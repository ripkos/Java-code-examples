package main;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpConnectionCounter {

    private static int contactedServers = 0;

    public static void main(String[] args) {
        System.out.println("Starting the client...");
        CountDownLatch latch = new CountDownLatch(2);
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        threadPool.submit(new HttpConnectionThread("www.pja.edu.pl", latch));
        threadPool.submit(new HttpConnectionThread("www.onet.pl", latch));
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Number of responsive servers: " + HttpConnectionCounter.getContactedServers());
        threadPool.shutdown();
    }

    public static synchronized void incrementContactedServers() {
        contactedServers++;
    }

    public static int getContactedServers() {
        return contactedServers;
    }
}
