package zad1.models;

public class Message {
    public Topic topic;
    public String text;

    public Topic getTopic() {
        return topic;
    }


    public String getText() {
        return text;
    }

    public Message(Topic topic, String text) {
        this.topic = topic;
        this.text = text;
    }
    @Override
    public String toString(){
        return "["+this.topic.name+"] "+ text;
    }
}
