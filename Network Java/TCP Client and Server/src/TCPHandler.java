import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPHandler implements Runnable {
    private Socket clientSocket;
    TCPHandler(Socket clientSocket){
        this.clientSocket=clientSocket;
    }
    @Override
    public void run() {
        try {
            BufferedReader inFromServer = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
            //wysyla s
            outToServer.println(20865);
            //wysyla 62849
            outToServer.println(62849);
            int s=0;
            String response[]=new String[2];
            //odczytuje dwie linie
            String line;
            while (!(line=inFromServer.readLine()).isEmpty()){
                System.out.println(line + " " + this.clientSocket.hashCode());
                if(s<2){
                    response[s]=line;
                }
                s++;
            }
            //Wysyla N+S
            outToServer.println(response[0]+response[1]);
           // clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
