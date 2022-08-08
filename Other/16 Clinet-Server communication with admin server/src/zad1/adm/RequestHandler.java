package zad1.adm;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import zad1.client.fx.CheckBoxWithTopic;
import zad1.models.Topic;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.regex.Pattern;

public class RequestHandler extends Thread {
    private static Charset charset  = Charset.forName("ISO-8859-2");

    SocketChannel socketChannel;
    String serverIP;
    int port;
    private ObservableList<CheckBoxWithTopic> topicList;
    public RequestHandler(ObservableList<CheckBoxWithTopic> list) {
        topicList=list;
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);

            this.serverIP = "127.0.0.1";
            this.port = 9009;
            checkConnect();
            //

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private StringBuffer remsg = new StringBuffer(); // Odpowiedź
    boolean checkConnect(){
        if (!socketChannel.isConnected()){
            try {
                socketChannel.connect(new InetSocketAddress(serverIP,port));
                while (!socketChannel.finishConnect()) {
                    //
                }
                return true;
            } catch (Exception e) {
                //e.printStackTrace();
                return false;
            }
        }
        return true;
    }
    private void write(String text) throws IOException {
        if(checkConnect()){
            remsg.setLength(0);
            remsg.append(text);
            remsg.append('\r');
            ByteBuffer buff = charset.encode(CharBuffer.wrap(remsg));
            socketChannel.write(buff);
            //System.out.println("here");
        }

    }


    private StringBuffer result = new StringBuffer();
    private static ByteBuffer inBuf = ByteBuffer.allocateDirect(1024);
    private static Pattern reqPatt = Pattern.compile(";;", Pattern.UNIX_LINES | Pattern.CASE_INSENSITIVE);
    @Override
    public void run() {

        try {
            while (true){
                result.setLength(0);
                inBuf.clear();
                if(socketChannel.isOpen() && socketChannel.isConnected()){
                    int readBytes = socketChannel.read(inBuf);
                    if(readBytes==0){
                        continue;
                    }
                    else {
                        inBuf.flip();
                        CharBuffer cbuf = charset.decode(inBuf);
                        while (cbuf.hasRemaining()){
                            char c =cbuf.get();
                            if(c=='\r' || c=='\n') break;
                            result.append(c);
                        }
                        String[] req = reqPatt.split(result,3);
                        switch (req[0]){
                            case "add_topic":
                                Platform.runLater(()->{
                                    topicList.add(new CheckBoxWithTopic(new Topic(req[1])));
                                });

                                break;
                            case "remove_topic":
                                Platform.runLater(()->{
                                    topicList.removeIf(a->a.topic.name.equals(req[1]));
                                });
                                break;
                            default:
                                break;
                        }
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void remove_topic(Topic topic) throws IOException {
        write("remove_topic;;"+topic.name);
    }

    public void add_topic(Topic topic) throws  IOException {
        write("add_topic;;"+topic.name);
    }

    public void send_msg(String text, Topic topic) throws IOException {
        write("add_msg;;"+topic.name+";;"+text);
    }
}
