import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;



public class TCPServer {
    ServerSocket serverSocket;
    ArrayList <ClientThread> clients ;
    int port;

    TCPServer(int port) throws IOException {
        this.port = port;
        serverSocket = new ServerSocket(port);
        clients = new ArrayList<>();
    }

    public static void main(String[] args) throws IOException {
        try {
            int portNumber = Integer.parseInt(args[0]);
            TCPServer tcpServer = new TCPServer(portNumber);
            Thread broadcast = new Broadcast(portNumber);
            broadcast.start();
            while (true) {
                try {
                    Socket clientSocket = tcpServer.serverSocket.accept();
                    ClientThread cliThread = new ClientThread(clientSocket, tcpServer);
                    tcpServer.clients.add(cliThread);
                    cliThread.start();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        } catch (BindException e) {
            System.out.println("ERROR: java TCPServer -port-");
        }
    }
}



