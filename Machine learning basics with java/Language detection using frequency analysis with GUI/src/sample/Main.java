package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.view.faze1.Weights;
import sample.view.faze2.Activated;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //
        List<Perceptron> perceptrons = getLayer();
        // Wizualizacja
        Parent root = FXMLLoader.load(getClass().getResource("fx1.fxml"));
        primaryStage.setTitle("mpp3");
        Scene scene = new Scene(root, 1200, 1200);
        primaryStage.setScene(scene);
        ChoiceBox<Perceptron> cb = (ChoiceBox<Perceptron>) scene.lookup("#choice1");
        cb.getItems().addAll(perceptrons);

        //Faza 2
        TextArea textArea = (TextArea) scene.lookup("#textArea");
        Button button = (Button) scene.lookup("#button");
        TextField decision = (TextField) scene.lookup("#decision");
        TableView<Activated> resultT = (TableView<Activated>) scene.lookup("#resultT");
        button.setOnAction((a) -> {
            String text = textArea.getText();
            text=Futil.cleanTextContent(text);
            double[] data = Futil.getProportions(Futil.countChars(text));
            resultT.getItems().clear();
            List<String> tmp = new ArrayList<>();
            for (Perceptron p : perceptrons) {
                int activated = p.activate(data);
                resultT.getItems().add(new Activated(p.lang,activated));
                if(activated==1) {
                    tmp.add(p.lang);
                }
            }
            String finalDeicision="Error";
            if(tmp.size()==0) {
                finalDeicision = perceptrons.get(0).lang;
                for (Perceptron p : perceptrons) {
                    if(!p.lang.equals(finalDeicision)) {
                        double rnd = Math.random();
                        if(rnd>0.5) {
                            finalDeicision=p.lang;
                        }
                    }
                }
            }
            if(tmp.size()>1) {
                finalDeicision = tmp.get(0);
                for (String s : tmp) {
                    if(!s.equals(finalDeicision)) {
                        double rnd = Math.random();
                        if(rnd>0.5) {
                            finalDeicision=s;
                        }
                    }
                }
            }
            if(tmp.size()==1){
                finalDeicision=tmp.get(0);
            }
            decision.setText(finalDeicision);
        });

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static List<Perceptron> getLayer() throws IOException {
        List<Perceptron> perceptrons;
        List<DataModel> trainingData = Futil.processDirs("trening");
        perceptrons = new ArrayList<>();
        for (DataModel d : trainingData) {
            perceptrons.add(new Perceptron(d.name));
        }
        int times = 3000000;

        for (Perceptron p : perceptrons) {
            boolean finished = false;
            for (int j = 0; j < times && !finished; j++) {
                boolean success = true;
                for ( DataModel d : trainingData) {
                    for (double[] prop : d.proportions) {
                        if(!p.train(prop,d.name)) {
                            success=false;
                        }
                    }
                }
                if(success) {
                    finished=true;
                }
            }
        }
        List<DataModel> testData = Futil.processDirs("test");
        for (Perceptron p: perceptrons) {
            for (DataModel d : testData) {
                int correct= d.name.equals(p.lang) ? 1 : 0;
                for (double[] prop : d.proportions) {
                    int decision = p.activate(prop);
                    if(decision==correct) {
                        if(decision==1) {
                            p.TasT++;
                        }
                        else {
                            p.FasF++;
                        }
                    }
                    else {
                        if(correct==1) {
                            p.TasF++;
                        }
                        else {
                            p.FasT++;
                        }
                    }
                }
            }
        }
        return perceptrons;
    }
}
