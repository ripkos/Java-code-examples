package TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("172.21.48.147", 10002);
            PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader inFromServer = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));
            outToServer.println("s20865");
            outToServer.println(900562);
            //outToServer.println();

            String line;
            while ((line = inFromServer.readLine()) != null) {
                System.out.println(line);
            }
            clientSocket.close();
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
