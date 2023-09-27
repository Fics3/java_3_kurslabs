import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {

    private int port = -1 ;
    private InetAddress ipAddress;
    private DatagramSocket serverSocket;
    private String username = "Server";
    boolean mda = false;
    int min = 0;
    int max = 100;
    int current;

    int count = 0;

    public Server(int port, DatagramSocket datagramSocket){
        this.port = port;
        this.serverSocket = datagramSocket;

    }

    public void sendMessageBack(String message) throws IOException {
        message = username + ": " + message;
        byte[] sendData = new byte[1024];
        sendData = message.getBytes();

        byte[] receiveData = new byte[1024];
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
        serverSocket.send(sendPacket);
    }
    public void guessNumber(String answer) throws IOException {
        String sentence;
        if (count < 7) {
            switch (answer) {
                case "yes" -> {
                    sentence = "Yay number guessed, now server in chat mode chat";
                    sendMessageBack(sentence);
                    mda = !mda;
                    min = 0;
                    max = 100;
                    count = 0;
                }
                case "<" -> {
                    max = current - 1;
                    count++;
                }
                case ">" -> {
                    min = current + 1;
                    count++;
                }
            }
            if (mda) {
                current = (min + max) / 2;
                sentence = "Your number is " + current;
                sendMessageBack(sentence);
            }
        }
        else {
            mda = true;
            min = 0;
            max = 100;
            current = (min + max) / 2;
            count = 1;
            sentence = "Let's try again\n"+"Your number is " + current;
            sendMessageBack(sentence);
        }
    }

    public void sendMessage() throws IOException {   //lovi pivo!

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {

                String message = inFromUser.readLine();
                if (message.startsWith("@name")){
                    String[] tmp = message.split(" ");
                    message = username+" change nickname to: ";
                    if(tmp[1]!=null) {
                        username = tmp[1];
                        message = message + username;
                    }
                    else{
                        System.out.println("ERROR: @name -name-");
                    }
                }
                else if (message.startsWith("@quit")) {
                    System.exit(-1);
                }
                else if (message.startsWith("@mda")){
                    mda = !mda;
                    message = "game ended by the Server";
                }
                sendMessageBack(message);
            }
            catch (IllegalArgumentException e){
                System.out.println("!WAIT FOR ANY USER CONNECT!");
            }
        }
    }
    public void receiveMessage() throws IOException {   //da da slushauy

            while (true) {
                    byte[] receiveData = new byte[1024];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    serverSocket.receive(receivePacket);
                    port = receivePacket.getPort();
                    ipAddress = receivePacket.getAddress();

                    String sentence = new String(receivePacket.getData(),0,receivePacket.getLength());
                    String[] a = sentence.split(" ");
                    try {
                        if (a[1].equals("@mda")) {
                            mda = true;
                            sentence = "GAME START\nWrite > if your number bigger or < lower";
                            min = 0;
                            max = 100;
                            current = (min + max) / 2;
                            count = 0;
                            count++;
                            sendMessageBack(sentence);
                        }
                        if (mda) {
                            guessNumber(a[1]);
                        } else {
                            System.out.println(sentence);
                        }
                    }
                    catch (ArrayIndexOutOfBoundsException e){
                        sendMessageBack("your message is empty");
                    }
            }
    }

    static public void main(String[] args) throws IOException {

        if(args.length!=1){
            System.out.println("java Server -port-");
        }
        DatagramSocket serverSocket = new DatagramSocket(Integer.parseInt(args[0]));

        Server server = new Server(Integer.parseInt(args[0]),serverSocket);

        Thread thread = new Thread(() -> {
            try {
                server.sendMessage();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
        thread.start();

        server.receiveMessage();
    }

}
