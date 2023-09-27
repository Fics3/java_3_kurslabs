import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Util {
    int port;

    public int receivePort() throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket();
        datagramSocket.setBroadcast(true);
        while (true) {
            byte[] receiveMessage = new byte[1024];
            DatagramPacket datagramPacket = new DatagramPacket(receiveMessage, receiveMessage.length);
            datagramSocket.receive(datagramPacket);
            String serverPort = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
            if (Integer.parseInt(serverPort) > 0) {
                port = Integer.parseInt(serverPort);
                System.out.println("Yay we got server port: " + port);
                datagramSocket.close();
                return port;
            }
        }
    }
}
