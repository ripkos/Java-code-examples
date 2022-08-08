import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPReceiver implements Runnable {
    DatagramSocket serverSocket;
    int port;

    public UDPReceiver(DatagramSocket serverSocket, int port){
        this.serverSocket=serverSocket;
        this.port=port;
    }

    @Override
    public void run() {
        while (true) {
            //Odbieram dowolny datagram
            DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);
            try {
                serverSocket.receive(receivePacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //String sentence = new String(receivePacket.getData());
            //System.out.println(sentence);
            InetAddress IPAddress = receivePacket.getAddress();
            int portFrom = receivePacket.getPort();
            server.checkAddPort(IPAddress, portFrom, this.port);
        }
    }
}
