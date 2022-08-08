package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectionCounter {
    private static int start_port=31167;
    private static int end_port=31688;
    private static String ip="172.21.48.177";
    private static int contactedServers = 0;
    private static List<Integer> list=  new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Starting the client...");
        CountDownLatch latch = new CountDownLatch(end_port-start_port);
        ExecutorService threadPool = Executors.newFixedThreadPool(16);
        for (int currport = start_port; currport < end_port+1; currport++) {
            threadPool.submit(new CSocket(ip,currport,latch));
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Number of responsive servers: " + getContactedServers()+"\nValues:\n");
        int sum=0;
        for(int obj:list){
            sum++;
            System.out.println(obj);
        }
        System.out.println("Sum " + sum + " all " + (sum+getContactedServers()));
        threadPool.shutdown();
    }
    public static synchronized void addInt(int a){
        list.add(a);
    }
    public static synchronized void incrementContactedServers() {
        contactedServers++;
    }

    public static int getContactedServers() {
        return contactedServers;
    }
}
