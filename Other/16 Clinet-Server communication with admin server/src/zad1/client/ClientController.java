package zad1.client;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import zad1.client.fx.CheckBoxWithTopic;
import zad1.models.Message;
import zad1.models.Topic;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    public RequestHandler requestHandler;
    private ObservableList<CheckBoxWithTopic> topicsList;
    private ObservableList<Message> msgsList;
    private ObservableList<String> sysmsgList;
    @FXML
    private ListView<CheckBoxWithTopic> topics;

    @FXML
    private ListView<String> sysmsg;

    @FXML
    private ListView<Message> msgs;

    @FXML
    private TextArea serverText;


    public void reconnect() {
        //requestHandler.checkConnect();
        requestHandler.write("ping");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        topicsList = FXCollections.observableArrayList();
        sysmsgList = FXCollections.observableArrayList();
        msgsList = FXCollections.observableArrayList();
        topics.setItems(topicsList);
        sysmsg.setItems(sysmsgList);
        msgs.setItems(msgsList);
        requestHandler = new RequestHandler(topicsList, msgsList, sysmsgList, serverText);
        requestHandler.runThreads();
        //requestHandler.write("user_initial");
        //requestHandler.serverDisconnected();

    }
}
