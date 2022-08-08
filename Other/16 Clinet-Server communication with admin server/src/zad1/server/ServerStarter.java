package zad1.server;

import zad1.models.ClientModel;
import zad1.models.Topic;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

public class ServerStarter {
    public static void main(String[] args) {

            Server server = new Server(9009);
            server.run();


    }

}
