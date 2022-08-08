package zad1;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    public TextArea chatText;
    @FXML
    public TextField userName;
    @FXML
    public TextField msg;
    private StringBuilder textBox;
    private Communicator communicator;
    public void callBack(String msg){
        textBox.append(msg);
        Platform.runLater(()->{
            chatText.setText(textBox.toString());
        });
    }
    @FXML
    void sendMsg() {
        String m = userName.getText()+":"+msg.getText()+"\n";
        communicator.sendMsg(m);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textBox = new StringBuilder();
        communicator = new Communicator(this);
    }
}
