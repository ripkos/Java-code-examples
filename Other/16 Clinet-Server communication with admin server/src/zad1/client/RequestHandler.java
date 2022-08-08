package zad1.client;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import jdk.nashorn.internal.ir.RuntimeNode;
import zad1.client.fx.CheckBoxWithTopic;
import zad1.models.Message;
import zad1.models.Topic;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.regex.Pattern;

public class RequestHandler {

    public static Charset charset  = Charset.forName("ISO-8859-2");
    SocketChannel socketChannel;
    String serverIP;
    int port;
    public ObservableList<CheckBoxWithTopic> topicList;
    public ObservableList<Message> msgList;
    public ObservableList<String> sysmsgList;
    private TextArea serverText;
    public RequestHandler(ObservableList<CheckBoxWithTopic> topics, ObservableList<Message> msgList, ObservableList<String> sysmsgList, TextArea serverText) {


            this.serverText=serverText;
            this.sysmsgList = sysmsgList;
            this.msgList = msgList;
            this.topicList = topics;
            this.serverIP = "127.0.0.1";
            this.port = 9009;
            checkConnect();
            //write("user_initial");
            //

    }

    public boolean wasLastFailure = false;
    public void serverDisconnected (boolean isConnected) {
        if (isConnected) {
            wasLastFailure=false;
            Platform.runLater(()-> {
                sysmsgList.add("[System] Jestes polaczony do servera!");
                serverText.setText("Jestes polaczony do serwera");
            });

        }
        else {
            try {
                socketChannel.close();
                socketChannel.socket().close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (!wasLastFailure) {
                wasLastFailure=true;
                Platform.runLater(() -> {
                    sysmsgList.add("[Error] Nie udalo sie polaczyc!");
                    serverText.setText("Blond polaczenia z serwerem");
                    topicList.clear();
                    msgList.clear();
                });
            }

        }
    }




    public Topic getTopicByName(String name) {
        final Topic[] tmp = {null};
        topicList.forEach(a-> {
            if (a.topic.name.equals(name)) tmp[0] =a.topic;
        });
        return tmp[0];
    }
    private StringBuffer remsg = new StringBuffer(); // Odpowiedź
    boolean checkConnect(){
        if (socketChannel == null || !socketChannel.isConnected() || !socketChannel.isOpen()){
            try {
                socketChannel = SocketChannel.open();
                socketChannel.configureBlocking(false);
                socketChannel.connect(new InetSocketAddress(serverIP,port));

                while (!socketChannel.finishConnect()) {
                    //
                }
                socketChannel.socket().setSoTimeout(2);
                serverDisconnected(true);
                return true;
            } catch (Exception e) {
                serverDisconnected(false);

                //e.printStackTrace();
                return false;

            }
        }

        return true;
    }
    public synchronized void write(String text) {
        remsg.setLength(0);
        remsg.append(text);
        remsg.append('\r');
        ByteBuffer buff = charset.encode(CharBuffer.wrap(remsg));
        try {
            socketChannel.write(buff);
        } catch (IOException e) {
            serverDisconnected(false);
        }
    }
    public boolean sub(Boolean newValue, Topic topic) {
            if(checkConnect()){

                String action = newValue ? "user_sub" : "user_unsub";
                String command = action+";;"+topic.name;
                write(command);
                if(!newValue){
                    msgList.removeIf(message -> message.topic.name.equals(topic.name));
                }
                return true;
            }
            else {
                return false;
            }


    }
    private RequestReader reader;
    private RequestStatus status;
    public void runThreads() {
        reader = new RequestReader(this);
        status = new RequestStatus(this);
        reader.start();
        status.start();
    }
}
