package zad1.proxyserver;


import zad1.CommonVariables;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProxyServer {
    boolean isRunning;
    private Map<String,LangServerModel> servers;
    ServerSocket serverSocket;
    ExecutorService threadPool;
    public ProxyServer() {
        try {
            serverSocket = new ServerSocket(CommonVariables.getVars().proxyServerPort);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        threadPool = Executors.newFixedThreadPool(3);
        isRunning=false;
        servers=new HashMap<>();
    }
    public void start() {
        isRunning=true;
        while (isRunning)
        {
            try {
                threadPool.submit(new ProxyServerRequestHandler(this,serverSocket.accept()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addServer(String serverName, String ip, int port) {
        System.out.println(serverName+" "+ip+":"+port);
        servers.put(serverName, new LangServerModel(serverName,ip,port));
    }

    public boolean isServerExists(String serverName) {
        return servers.containsKey(serverName);
    }

    public void proceedRequest(String key, String lang, int port, String hostAddress) {
        servers.get(lang).ask(key,port,hostAddress);
    }
}
