package zad1.client;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import zad1.client.fx.CheckBoxWithTopic;
import zad1.models.Message;
import zad1.models.Topic;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.regex.Pattern;

public class RequestReader extends Thread {

    private StringBuffer result = new StringBuffer();
    private static ByteBuffer inBuf = ByteBuffer.allocateDirect(1024);
    private static Pattern reqPatt = Pattern.compile(";;", Pattern.UNIX_LINES | Pattern.CASE_INSENSITIVE);
    private RequestHandler handler;
    public RequestReader(RequestHandler h){
        this.handler=h;
    }

    @Override
    public void run() {
        while (true) {
            result.setLength(0);
            inBuf.clear();
            if(handler.checkConnect()){
                int readBytes = 0;
                try {
                    readBytes = handler.socketChannel.read(inBuf);
                } catch (IOException e) {
                    handler.checkConnect();
                }
                if (readBytes==-1){
                    try {
                        handler.socketChannel.close();
                        handler.socketChannel.socket().close();
                        handler.socketChannel=null;
                    } catch (IOException e) {
                        handler.serverDisconnected(false);
                        //e.printStackTrace();
                    }

                }
                else if(readBytes==0){
                    continue;
                }
                else {
                    inBuf.flip();
                    CharBuffer cbuf = RequestHandler.charset.decode(inBuf);
                    while (cbuf.hasRemaining()){
                        char c =cbuf.get();
                        if(c=='\r' || c=='\n') break;
                        result.append(c);
                    }
                    String[] req = reqPatt.split(result,3);
                    switch (req[0]){
                        case "add_topic":
                            Platform.runLater(()->{
                                handler.sysmsgList.add("[Nowy temat] - " + req[1]);
                                CheckBoxWithTopic cb = new CheckBoxWithTopic(new Topic(req[1]));
                                cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
                                    @Override
                                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                                        handler.sub(newValue, cb.topic);
                                    }
                                });
                                handler.topicList.add(cb);
                            });

                            break;
                        case "remove_topic":
                            Platform.runLater(()->{
                                handler.sysmsgList.add("[Usuniento temat] - " + req[1]);
                                handler.topicList.removeIf(a->a.topic.name.equals(req[1]));
                                handler.msgList.removeIf(a->a.topic.name.equals(req[1]));

                            });
                            break;
                        case "add_msg":
                            Platform.runLater(()->{
                                System.out.println("Here");
                                handler.msgList.add(new Message(handler.getTopicByName(req[1]),req[2]));
                            });
                            break;
                        default:
                            break;
                    }
                }

            }
        }
    }
}
