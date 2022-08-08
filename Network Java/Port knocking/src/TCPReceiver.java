import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPReceiver implements Runnable{
    private Socket clientSocket;
    public TCPReceiver(Socket clientSocket){
        this.clientSocket=clientSocket;
    }

    int X=(int)(Math.random()*10%10+1);

    @Override
    public void run() {
        try {
            BufferedReader inFromServer = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
            boolean processing = true;
            while(processing) {
                //wysyla X
                outToServer.println(X);
                //System.out.println("Sending "+X+" to " + clientSocket.getInetAddress().getHostAddress()+":"+clientSocket.getPort());
                int Y = -1;
                //odbiera Y
                String line;
                while (!(line = inFromServer.readLine()).isEmpty()) {
                    //System.out.println(line + " " + this.clientSocket.hashCode());
                    Y = Integer.parseInt(line);
                    break;
                }
                //System.out.println("Recieveing "+Y+" from " + clientSocket.getInetAddress().getHostAddress()+":"+clientSocket.getPort());

                //wysyla (X+Y)*-1
                outToServer.println((X + Y) * -1);
                processing=false;
                System.out.println("Communication with " + clientSocket.getInetAddress().getHostAddress()+":"+clientSocket.getPort() + " ended successfully");
                // clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
