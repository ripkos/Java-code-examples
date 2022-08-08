package zad1;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Communicator implements MessageListener  {
    MessageProducer sender;
    Session ses;
    Controller c;
    public Communicator(Controller contr) {
        try {
        this.c=contr;
        Context ctx = null;
        ctx = new InitialContext();

        ConnectionFactory fact = (ConnectionFactory) ctx.lookup("ConnectionFactory");
        String admDestName = "z6";
        Destination dest = (Destination) ctx.lookup(admDestName);
        Connection con = fact.createConnection();
        ses = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        con.start();
        sender = ses.createProducer(dest);
        MessageConsumer receiver = ses.createConsumer(dest);
        receiver.setMessageListener(this);
        } catch (NamingException | JMSException e) {
            e.printStackTrace();
        }
    // Posłanie wiadomości msg
    }
    public void sendMsg(String text)  {
        try {
            sender.send(ses.createTextMessage(text));
            //System.out.println("Sended");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onMessage(Message msg) {
        try {
            //System.out.println("Recieved");
            c.callBack(((TextMessage) msg).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
