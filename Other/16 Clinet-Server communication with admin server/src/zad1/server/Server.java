package zad1.server;

import zad1.models.ClientModel;
import zad1.models.Topic;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Pattern;

public class Server {
    public ServerSocketChannel serverSocketChannel;
    public Selector selector;
    boolean isRunning;
    Map<String,Topic> topics;
    Map<SocketChannel, ClientModel> clients;
    public Server(int port){
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(port));
            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            isRunning = true;
            topics = new HashMap<>();
            clients = new HashMap<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (isRunning) {
            try {
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Set keys = selector.selectedKeys();
            Iterator iter = keys.iterator();
            while (iter.hasNext()){
                SelectionKey key = (SelectionKey) iter.next();
                iter.remove();
                if(key.isAcceptable()){
                    try {
                        SocketChannel clientChannel = serverSocketChannel.accept();
                        clientChannel.configureBlocking(false);
                        clientChannel.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ);
                        ClientModel clientModel = new ClientModel();
                        clients.put(clientChannel, clientModel);
                        if(!topics.isEmpty()){
                            topics.forEach((topicName,value) -> {
                                clientModel.scheduleBroadcast("add_topic;;"+topicName);
                            });
                        }
                        System.out.println("Nowy klient!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    continue;
                }

                    if(key.isReadable()){
                        SocketChannel cc = (SocketChannel) key.channel();
                        //System.out.println("TestR");

                        try {
                            serviceRequest(cc);
                        } catch (IOException e) {            System.out.println("Klient rozlaczony2");
                            try {
                                cc.close();
                                cc.socket().close();
                                clients.remove(cc);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                        }


                        //
                        continue;
                    }

                    if(key.isWritable()){
                        SocketChannel cc = (SocketChannel) key.channel();
                        ClientModel currentClient = clients.get(cc);
                        //System.out.println("TestW");
                            currentClient.scheduled.forEach(s -> {
                                System.out.println("Wyslano wiadomosc");
                                System.out.println(s);
                                    writeToClient(cc,s);



                            });
                            currentClient.scheduled.clear();

                        continue;
                    }


            }

        }
    }
    private static Charset charset  = Charset.forName("ISO-8859-2");
    private static final int BSIZE = 1024;
    private ByteBuffer bbuf = ByteBuffer.allocate(BSIZE);
    private StringBuffer reqString = new StringBuffer();

    private static Pattern reqPatt = Pattern.compile(";;", Pattern.UNIX_LINES | Pattern.CASE_INSENSITIVE);
    private void serviceRequest(SocketChannel sc) throws IOException {
        //System.out.println("Here");
        if(!sc.isOpen()){
            clients.remove(sc);
            return;
        }
        reqString.setLength(0);
        bbuf.clear();
        readLoop:
        while(true){
            //System.out.println("Here");
            int n = sc.read(bbuf);
            if (n>0){
                bbuf.flip();
                CharBuffer cbuf = charset.decode(bbuf);
                while (cbuf.hasRemaining()){
                    char c =cbuf.get();
                    if(c=='\r' || c=='\n') break readLoop;
                    reqString.append(c);
                }
            }
        }
        String[] req = reqPatt.split(reqString,3);
        System.out.println(Arrays.toString(req));
        // comenda ;; parametr1 ;; parametr2
        switch (req[0]) {
            //admin
            case "add_topic":
                topics.put(req[1],new Topic(req[1]));
                clients.forEach((socketChannel, clientModel) -> {
                    clientModel.scheduleBroadcast("add_topic;;"+req[1]);
                });
                break;
            case "remove_topic":
                clients.forEach((socketChannel, clientModel) -> {
                    clientModel.unsub(topics.get(req[1]));
                    clientModel.scheduleBroadcast("remove_topic;;"+req[1]);
                });
                topics.remove(req[1]);
                break;
            case "add_msg":
                clients.forEach((socketChannel, clientModel) -> {
                    if(clientModel.isInterestedIn(topics.get(req[1])))
                    clientModel.scheduleBroadcast("add_msg;;"+req[1]+";;"+req[2]);
                });
                break;
            //user
            case "user_sub":
                System.out.println(topics.get(req[1]));
                clients.get(sc).sub(topics.get(req[1]));
                break;
            case "user_unsub":
                clients.get(sc).unsub(topics.get(req[1]));
                break;
            default:
                break;
        }
    }

    private StringBuffer remsg = new StringBuffer(); // Odpowiedź
    public void writeToClient(SocketChannel sc, String msg) {
        try {
            remsg.setLength(0);
            remsg.append(msg);
            remsg.append('\r');
            ByteBuffer buff = charset.encode(CharBuffer.wrap(remsg));
            sc.write(buff);
        }
        catch (IOException e) {
            System.out.println("Klient rozlaczony2");
            try {
                sc.close();
                sc.socket().close();
                clients.remove(sc);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
