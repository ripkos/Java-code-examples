import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class client {
    public static void main(String[] args) {
        int timeOut = 3000;
        int TCPport=-1;
        int X = -1;
        int Y = (int)((Math.random()*10)%10+1);
        InetAddress serverIP=null;
        // UDP Port knocking
        try {
            serverIP = InetAddress.getByName(args[0]);
            DatagramSocket clientSocket =new DatagramSocket();
            for (int i = 1; i < args.length ; i++) {
                DatagramPacket sendPacket = new DatagramPacket("0".getBytes(), "0".getBytes().length, serverIP, Integer.parseInt(args[i]));
                Thread.sleep(150);
                System.out.println("Knocking " + args[i]);
                clientSocket.send(sendPacket);
            }
            System.out.println("All ports knocked");
            // Timeout
            clientSocket.setSoTimeout(timeOut);
            boolean received = false;
            DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);
            while(!received){
                try {
                clientSocket.receive(receivePacket);
                received=true; }
                catch (SocketTimeoutException e) {
                    // timeout exception.
                    System.out.println("Timeout reached!!! " + e);
                    clientSocket.close();
                    System.exit(-2);
                }

            }

            TCPport=Integer.parseInt(new String(receivePacket.getData()).replaceAll("[^\\d.]", ""));
            System.out.println("Received port: " + TCPport);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        // TCP komunikacja
        try{
            Socket clientSocket = new Socket(args[0], TCPport);
            System.out.println("Connection established");
            PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader inFromServer = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));
            boolean processing=true;
            while (processing) {
                String line;
                //Odbiera X
                while (!(line = inFromServer.readLine()).isEmpty()) {
                    System.out.println("Received X : " + line);
                    X = Integer.parseInt(line);
                    break;
                }
                //Wysyla Y
                if (X > 0) {
                    System.out.println("Sending Y : " + Y);
                    outToServer.println(Y);
                }
                else{
                    //Odbiera (X+Y)*-1
                    processing=false;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }
}
