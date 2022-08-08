package zad1.langserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LangRequestHandler implements Runnable {
    private Socket socket;
    private LangServer langServer;
    public LangRequestHandler(Socket socket, LangServer s) {
        this.socket=socket;
        this.langServer=s;
    }

    @Override
    public void run() {
        PrintWriter outToServer = null;
        try {
            //outToServer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader inFromServer = new BufferedReader (new InputStreamReader(socket.getInputStream()));
            //outToServer.println(key+";"+requesterPort);
            String data[] = inFromServer.readLine().split(";");
            inFromServer.close();
            socket.close();
            String key = data[0];
            int port = Integer.parseInt(data[1]);
            String ip = data[2];
            langServer.sendResponse(key,ip,port);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
