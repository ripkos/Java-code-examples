import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class dns1 {
    public static void main(String[] args) throws Exception
    {
        DatagramSocket clientSocket = new DatagramSocket();
        //"TODO 1: wpisz tutaj adres IP serwera DNS a.root-servers.net."
        InetAddress IPAddress = InetAddress.getByName("198.41.0.4");
        byte[] receiveData = new byte[1024];

        byte[] requestPayload = {0x48, 0x77, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x03, 0x77,
                0x77, 0x77, 0x04, 0x32, 0x31, 0x39, 0x31, 0x03, 0x6f, 0x72, 0x67, 0x00, 0x00, 0x01, 0x00, 0x01};

        DatagramPacket sendPacket = new DatagramPacket(requestPayload, requestPayload.length, IPAddress,
                /*TODO 2: zastąp 0 prawidłowym numerem portu dla serwera DNS*/53);
        clientSocket.send(sendPacket);

        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);

        //UWAGA! Aby odpowiedzieć na pytania z formularza przed uruchomieniem aplikacji włącz nasłuchiwanie w Wireshark
        System.out.println("Response received - check it in Wireshark!");
        clientSocket.close();
    }
}
