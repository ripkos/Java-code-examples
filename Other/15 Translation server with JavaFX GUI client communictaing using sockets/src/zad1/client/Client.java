package zad1.client;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Client extends Application {
    static ClientHost host;
    public static void main(String[] args) {
        host=new ClientHost();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("JavaFX Slownik");
        //Input
        TextField word = new TextField("Wyraz");
        TextField lang = new TextField("Jezyk");
        TextField port = new TextField("Port");
        //Output
        TextField response = new TextField("Tlumaczenie");
        response.setEditable(false);
        //Button
        Button update_button = new Button("Update");
        update_button.setOnMousePressed(
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if(host.handle(Integer.parseInt(port.getText()))) {
                            try {
                                if(host.ask(word.getText(),lang.getText(),(Integer.parseInt(port.getText())))) {
                                    response.setText("Czekam na odpowiedz od serwera...");
                                    while(host.waiting) {
                                        Thread.sleep(1500);
                                    }
                                    response.setText(host.response);
                                    host.waiting=true;
                                }
                                else {
                                    response.setText("Blond: nie ma takiego jezyka");
                                }
                            } catch (IOException | InterruptedException e) {
                                e.printStackTrace();
                                response.setText("Blond: nie mozna polaczyc sie z serwerem");
                            }
                        }
                        else {
                            response.setText("Blond: nie wolno otwierac tego portu");
                        }
                    }
                }
        );

        VBox vBox = new VBox(word,lang,port,update_button,response);
        Scene scene = new Scene(vBox, 960, 600);

        primaryStage.setScene(scene);
        primaryStage.show();


    }
}
