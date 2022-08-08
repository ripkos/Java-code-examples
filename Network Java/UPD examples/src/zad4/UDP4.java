package zad4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDP4 {
    public static void main(String args[]) throws Exception {
        //BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName("172.21.48.197");
        //byte[] sendData = new byte[1024];
        byte[] sendData = "904915".getBytes();
        byte[] receiveData = new byte[512];
        //String sentence = inFromUser.readLine();
        //sendData = sentence.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 10005);
        clientSocket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
        System.out.println("Here 3");
            String modifiedSentence = new String(receivePacket.getData());
            System.out.println("FROM SERVER: " + modifiedSentence);


        clientSocket.close();
    }
}
