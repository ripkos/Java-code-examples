package zad5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class UPD5Client {
    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("172.21.48.141", 20001);
            PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader inFromServer = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));
            outToServer.println(20865);
            outToServer.println("172.23.129.89:9988");
            //outToServer.println();

            String line;
            while ((line = inFromServer.readLine()) != null) {
                System.out.println(line);
            }


            //clientSocket.close();

        }
        catch (UnknownHostException u){
            u.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
