import java.io.IOException;
import java.net.*;

public class Broadcast extends Thread{

    DatagramSocket datagramSocket;
    String broadcastMessage;
    int serverPort;

    Broadcast( int port) throws SocketException {
        datagramSocket = new DatagramSocket();
        this.serverPort = port;

    }

    public void run(){

        try {
            int port = 0;
            datagramSocket.setBroadcast(true);
            broadcastMessage = String.valueOf(serverPort);
            byte[] buffer = broadcastMessage.getBytes();
            while (true) {
                if(port==65535){
                    port = 10000;
                }
                else{
                    port++;
                }
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("255.255.255.255"), port);
                datagramSocket.send(datagramPacket);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
