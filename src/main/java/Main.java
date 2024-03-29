import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

    Scene scene = new Scene(root, 590, 650);

    scene.getStylesheets().addAll(this.getClass().getResource("javaFX.css").toExternalForm());

    primaryStage.setTitle("Production");
    primaryStage.setScene(scene);
    primaryStage.show();


  }
}
