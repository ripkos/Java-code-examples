package zad1.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientResponseHandler extends Thread {
    boolean isRunning;
    ServerSocket serverSocket;
    private ClientHost host;
    public ClientResponseHandler(int port, ClientHost host) throws IOException {
        isRunning=true;
        serverSocket = new ServerSocket(port);
        this.host=host;
    }
    @Override
    public void run() {
        while (isRunning) {
            try {
                Socket responseSocket = serverSocket.accept();
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(responseSocket.getInputStream()));
                String response = inFromServer.readLine();
                inFromServer.close();
                serverSocket.close();
                host.setResponse(response);
                host.waiting=false;
                isRunning=false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
