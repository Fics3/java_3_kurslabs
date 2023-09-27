import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

class ClientThread extends Thread{

    Socket socket;
    TCPServer tcpServer;
    PrintWriter out;
    String name = "user";
    ClientThread(Socket s,TCPServer tcpServer) throws IOException {
        socket = s;
        this.tcpServer = tcpServer;
        out = new PrintWriter(socket.getOutputStream(), true);
        name = name+socket.getPort();
    }
    public void sendMessageToAll(String message){
        if(message!=null) {
            for (ClientThread clientThread : tcpServer.clients) {
                if (clientThread.socket.getPort() != socket.getPort()) {
                    clientThread.out.println(name + ": " + message);
                }
            }
        }
        else {
            out.println("Your message is empty!");
        }
    }
    public void sendMessageToUser(String message){
        try {
            String nameReceiver = message.split(" ")[1];
            for (ClientThread clientThread : tcpServer.clients) {
                if (Objects.equals(nameReceiver, clientThread.name) && !Objects.equals(nameReceiver, name)) {
                    clientThread.out.println(name + " whispers to you" + ": " + message.split(nameReceiver + " ")[1]);
                    return;
                }
            }
            out.println("No such a user with this name!");
        }
        catch (ArrayIndexOutOfBoundsException e){
            out.println("ERROR: @senduser -name- -message-");
        }

    }
    public void changeUsername(String message){
        try {
            String[] tmp = message.split(" ");
            for (ClientThread clientThread: tcpServer.clients) {
                if(clientThread.name.equals(tmp[1]) && clientThread != this){
                    out.println(tmp[1]+" this name already in used");
                    return;
                }
            }
            message = name;
            name = tmp[1];
            message = message + " changed name to " + name;
            sendMessageToAll(message);
        }
        catch (ArrayIndexOutOfBoundsException e){
            out.println("ERROR: @name -yourname-");
        }
    }
    public void run() {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            String clientCommand;
            out.println("connected");
            while (socket.isConnected()) {
                clientCommand = in.readLine();
                if(clientCommand.startsWith("@")) {
                    if (clientCommand.startsWith("@name")) {
                        changeUsername(clientCommand);
                    } else if (clientCommand.startsWith("@senduser")){
                        sendMessageToUser(clientCommand);
                    }
                    else if(clientCommand.startsWith("@quit")){
                        out.println("@quit");
                    }
                    else{
                        out.println("Command "+ clientCommand + " doesn't exist");
                    }
                }
                else {
                    sendMessageToAll(clientCommand);
                }

            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        catch (NullPointerException e){
            tcpServer.clients.remove(this);
            sendMessageToAll(name+" disconnected!");
        }
    }

}
