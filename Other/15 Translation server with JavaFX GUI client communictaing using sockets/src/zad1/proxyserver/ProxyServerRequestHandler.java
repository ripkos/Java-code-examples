package zad1.proxyserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ProxyServerRequestHandler implements Runnable {
    private ProxyServer proxyServer;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    public ProxyServerRequestHandler(ProxyServer server, Socket socket) {
        this.proxyServer=server;
        this.socket=socket;
        try {
            in = new BufferedReader (new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            int code = Integer.parseInt( in.readLine() );
            boolean passed=false;
            String requestKey = null;
            String requestLang = null;
            String[] data = null;
            int requestPort = 0;
            switch (code) {
                // rejestracja nowego serwera
                case -1:
                    data = in.readLine().split(":");
                    String serverName = data[0];
                    int serverPort = Integer.parseInt(data[1]);
                    proxyServer.addServer(serverName, socket.getInetAddress().getHostAddress(), serverPort);
                    break;
                // dodatnie - obsluga zadania
                case 1:
                    data = in.readLine().split(";");
                    requestKey = data[0];
                    requestLang = data[1];
                    requestPort = Integer.parseInt(data[2]);
                    if(proxyServer.isServerExists(requestLang))
                    {
                        out.println(1);
                        passed=true;
                        
                    }
                    else {
                        out.println(-1);
                    }
                    break;
            }
            out.close();
            socket.close();
            if(passed) {
                proxyServer.proceedRequest(requestKey,requestLang,requestPort,socket.getInetAddress().getHostAddress());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
