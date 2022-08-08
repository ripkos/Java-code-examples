import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User {
    InetAddress ip;
    int port;
    int[] knockedPort;
    int currentPointer;
    boolean sequenceConfirmed=false;
    boolean TCPCreated=false;

    public User(InetAddress ip, int port, int seqlen){
        currentPointer=0;
        this.ip=ip;
        this.port=port;
        this.knockedPort=new int[seqlen];
    }
    synchronized void knock(int knockPort) {
        knockedPort[currentPointer]=knockPort;
        currentPointer+=1;
        boolean isCorrectSequence=true;
        boolean shouldReset=false;
        for (int i = 0; i < knockedPort.length && isCorrectSequence ; i++) {
            if(knockedPort[i]!=server.correctSequence[i]){
                isCorrectSequence=false;
                if(knockedPort[i]!=0){
                    shouldReset=true;
                }
            }
        }
        if (isCorrectSequence){
            sequenceConfirmed=true;
        }
        else{
            if(shouldReset){
                Arrays.fill(knockedPort, 0);
                knockedPort[0]=knockPort;
                currentPointer=1;
            }
        }

    }


}
