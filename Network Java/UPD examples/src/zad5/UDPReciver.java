package zad5;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPReciver implements Runnable {
    DatagramSocket serverSocket;
    int unq;
    public UDPReciver(DatagramSocket serverSocket,int i){
        this.serverSocket=serverSocket;
        unq=i;
    }
    @Override
    public void run() {
        //odbiera dowolny datagram
/*
            DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);

            serverSocket.receive(receivePacket);
            String sentence = new String(receivePacket.getData());
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            //- wysyła bufor bajtów napisu będącego pojenczą linią składającą się z numeru s Twojego indeksu (bez litery s)
            DatagramPacket sendPacket = new DatagramPacket("20865".getBytes(), "20865".getBytes().length, IPAddress, port);
            serverSocket.send(sendPacket);
            //- wysyła bufor bajtów napisu będącego pojenczą linią składającą się z 133411
            sendPacket = new DatagramPacket("133411".getBytes(), "133411".getBytes().length, IPAddress, port);
            serverSocket.send(sendPacket);
            //- odbiera jeden datagram z pojedynczą linią składającą się z liczby całkowitej N
            DatagramPacket receivePacket2 = new DatagramPacket(new byte[512], 512);

            serverSocket.receive(receivePacket2);
            String str2 = new String(receivePacket2.getData());
            System.out.println(str2+" <-"+unq);
            DatagramPacket receivePacket3 = new DatagramPacket(new byte[512], 512);

            serverSocket.receive(receivePacket3);
            String str3 = new String(receivePacket3.getData());
            System.out.println(str2+" <-"+unq);


 */
    }
}
