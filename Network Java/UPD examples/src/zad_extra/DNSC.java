package zad_extra;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class DNSC {

    public static void main(String args[]) throws Exception{
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName("TODO 1: wpisz tutaj adres IP serwera DNS a.root-servers.net.");
        byte[] receiveData = new byte[1024];

        byte[] requestPayload = {(byte)0x48, (byte)0x77, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x01, (byte)0x00,
                (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x03, (byte)0x77,
                (byte)0x77, (byte)0x77, (byte)0x04, (byte)0x32, (byte)0x31, (byte)0x39, (byte)0x31, (byte)0x03,
                (byte)0x6f, (byte)0x72, (byte)0x67, (byte)0x00, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x01};

        DatagramPacket sendPacket = new DatagramPacket(requestPayload, requestPayload.length, IPAddress,
                /*TODO 2: zastąp 0 prawidłowym numerem portu dla serwera DNS*/ 0);
		clientSocket.send(sendPacket);

		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);

		//UWAGA! Aby odpowiedzieć na pytania z formularza przed uruchomieniem aplikacji włącz nasłuchiwanie w Wireshark
		System.out.println("Response received - check it in Wireshark!");
		clientSocket.close();
	}
}
