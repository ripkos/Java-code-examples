package zad1.langserver;

import zad1.CommonVariables;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LangServer {
    String serverName;
    HashMap<String,String> langMap;
    boolean isRunning=true;
    ServerSocket serverSocket;
    ExecutorService threadPool;
    public LangServer(String serverName){
        langMap = new HashMap<>();
        this.serverName = serverName;
        try {
            BufferedReader reader1 = new BufferedReader(new FileReader("D:\\Study\\SEMESTR 4\\TPO\\TPO3_PM_S20865\\src\\zad1\\langserver\\data\\"+serverName+".txt"));
            String line = reader1.readLine();
            while (line!=null){
                String[] data = line.split(";");
                langMap.put(data[0],data[1]);
                line = reader1.readLine();
            }
            reader1.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        try {
            serverSocket = new ServerSocket(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        threadPool = Executors.newFixedThreadPool(3);
    }
    public void ConnectToMainServer () {
        try {
            Socket clientSocket = new Socket(CommonVariables.getVars().localhost, CommonVariables.getVars().proxyServerPort);
            PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
            //BufferedReader inFromServer = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));
            outToServer.println(-1);
            outToServer.println(this.serverName+":"+serverSocket.getLocalPort());
            outToServer.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void StartProcessing () {
        while (isRunning){
            try {
                threadPool.submit(new LangRequestHandler(serverSocket.accept(),this));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendResponse(String key, String ip, int port) {
        try {
            Socket responseSocket = new Socket(ip,port);
            PrintWriter outToServer = new PrintWriter(responseSocket.getOutputStream(), true);
            outToServer.println(langMap.getOrDefault(key, "Blond: Nie znalieziono tlumaczenia dla takiego slowa"));
            outToServer.close();
            responseSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
