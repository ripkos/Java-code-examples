package zad1.adm;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import zad1.client.fx.CheckBoxWithTopic;
import zad1.models.Message;
import zad1.models.Topic;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdmController implements Initializable {
    private RequestHandler requestHandler;
    private ObservableList<CheckBoxWithTopic> topicList;

    @FXML
    private ListView<CheckBoxWithTopic> topics;

    @FXML
    private Button removeTopicBtn;

    @FXML
    private Button addTopicBtn;

    @FXML
    private TextField topicName;

    @FXML
    private ChoiceBox<CheckBoxWithTopic> choiceTopic;

    @FXML
    private TextArea msgTextArea;

    @FXML
    private Button sendBtn;

    public void sendMsg() {
        try {
            requestHandler.send_msg(msgTextArea.getText(), choiceTopic.getValue().topic);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        topicList = FXCollections.observableArrayList();
        choiceTopic.setItems(topicList);
        topics.setItems(topicList);
        requestHandler = new RequestHandler(topicList);
        requestHandler.start();
    }
    public void addTopic(){
        try {
            //System.out.println("here");
            requestHandler.add_topic(new Topic(topicName.getText()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void removeAllTopics(){
        topics.getItems().forEach(topicBox -> {
            if(topicBox.selectedProperty().get())
            try {
                requestHandler.remove_topic(topicBox.topic);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }




}
