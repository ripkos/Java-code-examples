package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class CSocket implements Runnable {
    private String ip;
    private int port;
    private Socket socket;
    private CountDownLatch latch;
    private static int code=935924;

    @Override
    public void run() {
        try {
            socket = new Socket(ip, port);
            ConnectionCounter.incrementContactedServers();

            PrintWriter outToServer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            outToServer.println(code);
            outToServer.println();

            String line;
            while (!(line = inFromServer.readLine()).isEmpty()) {
                ConnectionCounter.addInt(Integer.parseInt(line));
            }
            socket.close();
            System.out.println("Socket  closed!\n");
        } catch (IOException e) {
        } finally {
            latch.countDown();
        }
    }

    public CSocket(String ip, int port, CountDownLatch latch ) {
        this.ip=ip;
        this.port=port;
        this.latch=latch;
    }
}
