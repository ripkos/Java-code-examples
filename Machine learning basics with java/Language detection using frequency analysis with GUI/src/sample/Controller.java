package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.view.faze1.Eval;
import sample.view.faze1.Matrix;
import sample.view.faze1.Weights;
import sample.view.faze2.Activated;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    //Wagi
    @FXML
    private TableView<Weights> wagi;
    @FXML
    private TableColumn<Weights,String> litera;
    @FXML
    private TableColumn<Weights,Double> waga;

    //Choice
    @FXML
    private ChoiceBox<Perceptron> choice1;

    //Matrix
    @FXML
    private TableView<Matrix> matrix;
    @FXML
    private TableColumn<Matrix,String> m1;
    @FXML
    private TableColumn<Matrix,Integer> m2;
    @FXML
    private TableColumn<Matrix,Integer> m3;
    //Eval
    @FXML
    private TableView<Eval> eval;
    @FXML
    private TableColumn<Eval, Double> e1;
    @FXML
    private TableColumn<Eval, Double> e2;

    //Macierz Decyzji
    @FXML
    private TableView<Activated> resultT;
    @FXML
    private TableColumn<Activated, Integer> r1;
    @FXML
    private TableColumn<Activated, Integer> r2;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Dec
        r1.setCellValueFactory(new PropertyValueFactory<>("name"));
        r2.setCellValueFactory(new PropertyValueFactory<>("val"));
        resultT.setItems(FXCollections.observableArrayList());


        //Wagi
        litera.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        waga.setCellValueFactory(new PropertyValueFactory<>("waga"));
        wagi.setItems(FXCollections.observableArrayList());

        //Matrix
        m1.setCellValueFactory(new PropertyValueFactory<>("rowCase"));
        m2.setCellValueFactory(new PropertyValueFactory<>("positiveNum"));
        m3.setCellValueFactory(new PropertyValueFactory<>("negativeNum"));
        matrix.setItems(FXCollections.observableArrayList());

        //Eval
        e1.setCellValueFactory(new PropertyValueFactory<>("name"));
        e2.setCellValueFactory(new PropertyValueFactory<>("value"));
        eval.setItems(FXCollections.observableArrayList());
        //ChoiceBox
        choice1.setItems(FXCollections.observableArrayList());
        choice1.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Perceptron p = choice1.getItems().get((Integer) newValue);
                wagi.getItems().clear();
                wagi.getItems().addAll(p.getWeights());
                matrix.getItems().clear();
                matrix.getItems().addAll(p.getMatrix());
                eval.getItems().clear();
                eval.getItems().addAll(p.getEval());

            }
        });
    }
}
