/**
 *
 *  @author Poskrypko Maksym S20865
 *
 */

package zad1;


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Main extends Application {
  public static Service static_s;
  public static void main(String[] args) {
    Service s = new Service("Poland");
    static_s=s;
    String weatherJson = s.getWeather("Warsaw");
    Double rate1 = s.getRateFor("USD");
    Double rate2 = s.getNBPRate();
    // ...
    // część uruchamiająca GUI
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("JavaFX WebView");

    WebView webView = new WebView();

    webView.getEngine().load("https://en.wikipedia.org/w/index.php?search="+static_s.city);
    //Output
    TextArea weather = new TextArea(static_s.weatherString);
    weather.setEditable(false);
    TextField finalRatio = new TextField(static_s.finalRatio);
    finalRatio.setEditable(false);
    TextField npbRatio = new TextField(static_s.npbRatio);
    npbRatio.setEditable(false);

    //Input
    TextField city = new TextField(static_s.city);

    TextField country = new TextField(static_s.country);

    TextField currency_string = new TextField(static_s.targetCurrency);
    Button update_button = new Button("Update");
    update_button.setOnMousePressed(
            new EventHandler<MouseEvent>() {
              @Override
              public void handle(MouseEvent event) {
                static_s = new Service(country.getText());
                weather.setText(static_s.getWeather(city.getText()));
                finalRatio.setText(static_s.getRateFor(currency_string.getText()).toString());
                static_s.getNBPRate();
                npbRatio.setText(static_s.npbRatio);
                webView.getEngine().load("https://en.wikipedia.org/w/index.php?search="+static_s.city);
              }
            }
    );

   /*
    ChangeListener<Object> listenerCurrency = new ChangeListener<Object>() {

      @Override
      public void changed(ObservableValue observable, Object oldValue, Object newValue) {
          finalRatio.setText(static_s.getRateFor(currency_string.getText()).toString());
      }
    };
    currency_string.textProperty().addListener(listenerCurrency);
    */
    VBox vBox = new VBox(city,country,currency_string,update_button, //input
            weather,finalRatio,npbRatio,webView);
    Scene scene = new Scene(vBox, 960, 600);

    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
