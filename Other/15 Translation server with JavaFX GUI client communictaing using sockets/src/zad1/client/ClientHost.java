package zad1.client;

import javafx.scene.control.TextField;
import zad1.CommonVariables;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHost {
    ClientResponseHandler responseHandler;
    String response="";
    boolean waiting=true;



    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean handle(int responsePort) {
        try {
            responseHandler = new ClientResponseHandler(responsePort,this);
            responseHandler.start();
        } catch (IOException e) {
            //e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean ask(String word, String lang, int port) throws IOException {
            Socket clientSocket = new Socket(CommonVariables.getVars().localhost, CommonVariables.getVars().proxyServerPort);
            PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outToServer.println(1);
            outToServer.println(word+";"+lang+";"+port);
            String responseLine = inFromServer.readLine();
            int code = Integer.parseInt(responseLine);
            outToServer.close();
            inFromServer.close();
            clientSocket.close();
        return code > 0;


    }
}
