import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9001);
            ExecutorService threadPool = Executors.newFixedThreadPool(3);
            while (true) {
                threadPool.submit(new TCPHandler(serverSocket.accept()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
