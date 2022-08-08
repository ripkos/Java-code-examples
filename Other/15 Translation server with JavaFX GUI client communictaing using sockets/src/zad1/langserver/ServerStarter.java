package zad1.langserver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ServerStarter {
    public static void main(String[] args) {
    LangServer server = new LangServer(args[0]);
    server.ConnectToMainServer();
    server.StartProcessing();
    }

}
