package zad5;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UDP5Server {
    public static ExecutorService threadPool = Executors.newFixedThreadPool(3);
    public static int i=0;
    public static DatagramSocket serverSocket;

    static {
        try {
            serverSocket = new DatagramSocket(9988);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        while(true){
            //odbiera dowolny datagram
            DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);
            serverSocket.receive(receivePacket);
            String sentence = new String(receivePacket.getData());
            System.out.println(sentence);
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            //- wysyła bufor bajtów napisu będącego pojenczą linią składającą się z numeru s Twojego indeksu (bez litery s)
            DatagramPacket sendPacket = new DatagramPacket("20865".getBytes(), "20865".getBytes().length, IPAddress, port);
            serverSocket.send(sendPacket);
            //- wysyła bufor bajtów napisu będącego pojenczą linią składającą się z 133411
            sendPacket = new DatagramPacket("133411".getBytes(), "133411".getBytes().length, IPAddress, port);
            serverSocket.send(sendPacket);
        }
    }

}
