package zad1.models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ClientModel {
    List<Topic> topics;
    public List<String> scheduled;
    public ClientModel() {
        topics = new LinkedList<>();
        scheduled=new LinkedList<>();
    }

    public void sub(Topic topic) {
        topics.add(topic);
    }

    public void unsub(Topic topic){
        topics.remove(topic);
    }

    public void scheduleBroadcast(String s) {
        scheduled.add(s);
    }

    public boolean isInterestedIn(Topic topic) {
        System.out.println(topic);
        System.out.println(topics);
        return topics.contains(topic);
    }
}
