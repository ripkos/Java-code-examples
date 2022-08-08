import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class server {

    static List<User> users = new ArrayList<>();
    static int[] correctSequence;
    static DatagramSocket masterSocket;
    static ExecutorService TCPthreadPool;


    public static void main(String[] args) throws SocketException {
        List<Integer> countList = new ArrayList<>();
        correctSequence = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            correctSequence[i]=Integer.parseInt(args[i]);
            if (!countList.contains(Integer.parseInt(args[i]))) {
                countList.add(Integer.parseInt(args[i]));
            }
        }
        TCPthreadPool = Executors.newCachedThreadPool();
        ExecutorService UDPthreadPool = Executors.newCachedThreadPool();
        for (int port : countList){
            try {
                UDPthreadPool.submit(new UDPReceiver(new DatagramSocket(port), port));
            } catch (SocketException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        masterSocket=new DatagramSocket();


    }
    static synchronized void checkAddPort (InetAddress ipFrom, int portFrom, int knockedPort){
        System.out.println(ipFrom.getHostAddress()+":"+portFrom +" knocked port " + knockedPort);
        User current = null;
        for (User user : users){

            if (user.ip.getHostAddress().equals(ipFrom.getHostAddress()) && user.port==portFrom) {
            current=user;
            break;
            }
        }
        if (current==null){

            current=new User(ipFrom, portFrom, correctSequence.length);
            users.add(current);
        }
        current.knock(knockedPort);

        if (current.sequenceConfirmed){
            if (!current.TCPCreated){
                try {
                    ServerSocket serverSocket = new ServerSocket(0);
                    System.out.println(ipFrom.getHostAddress()+":"+portFrom +" authorization successful, opening port " + serverSocket.getLocalPort());
                    String port = String.valueOf(serverSocket.getLocalPort());
                    DatagramPacket data = new DatagramPacket(port.getBytes(),port.getBytes().length,ipFrom,portFrom);
                    masterSocket.send(data);
                    TCPthreadPool.submit(new TCPReceiver(serverSocket.accept()));

                } catch (IOException e) {
                    e.printStackTrace();
                }
                current.TCPCreated=true;
            }
        }
    }

}
