package zad1;

import java.util.ArrayList;
import java.util.List;

public class Letters {
    List<Thread> list = new ArrayList<>();

    public Letters(String letters) {
        for (int i = 0; i < letters.length(); i++) {
            char Char = letters.charAt(i);
            list.add(new Thread(()->
            {
                while(true)
                {
                    try {
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        return;
                    }
                    System.out.print(Char);

                }
            }
                    ,"Thread " + Char));
        }
    }

    public Iterable<? extends Thread> getThreads() {
        return list;
    }
}
