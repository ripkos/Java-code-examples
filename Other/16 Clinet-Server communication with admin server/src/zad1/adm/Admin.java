package zad1.adm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Admin extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("admin.fxml"));
        primaryStage.setTitle("TPO z4 Client");
        Scene scene = new Scene(root, 1200, 1200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
