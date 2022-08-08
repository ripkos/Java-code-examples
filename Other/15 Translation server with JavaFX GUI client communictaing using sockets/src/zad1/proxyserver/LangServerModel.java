package zad1.proxyserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class LangServerModel {
    String lang;
    String ip;
    int port;

    public LangServerModel(String lang, String ip, int port) {
        this.lang = lang;
        this.ip = ip;
        this.port = port;
    }

    public void ask(String key, int requesterPort, String requesterIp) {
        try {
            Socket clientSocket = new Socket(ip, port);
            PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
            //BufferedReader inFromServer = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));
            outToServer.println(key+";"+requesterPort+";"+requesterIp);
            outToServer.close();
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
