import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class TCPClient {

    Socket socket;
    TCPClient(Socket socket){
        this.socket = socket;
    }

    public void sendMessage() throws IOException {
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        while (true) {
            String fromUser = stdIn.readLine();
            if (fromUser != null) {
                out.println(fromUser);
            }
        }
    }

    public void receiveMessage(BufferedReader in) throws IOException {
        String fromServer;
        while ((fromServer = in.readLine()) != null) {
            if(fromServer.equals("@quit")) {
                System.exit(-1);
            }
            else {
                System.out.println(fromServer);
            }
        }
    }


    public static void main(String[] args) throws IOException {
        String hostName = args[0];
        Util util = new Util();
        int portNumber = util.receivePort();

        try {
            Socket kkSocket = new Socket(hostName,portNumber);
            TCPClient tcpClient = new TCPClient(kkSocket);

            BufferedReader in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
            Thread thread = new Thread(() -> {
                try {
                    tcpClient.sendMessage();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });thread.start();
            tcpClient.receiveMessage(in);
            } catch (IOException ex) {
            throw new RuntimeException(ex);
            }
        }
    }
