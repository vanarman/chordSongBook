
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("pages/mainPage.fxml"));
        primaryStage.setTitle("Guitar Chords Song Book");
        primaryStage.setMinHeight(640);
        primaryStage.setMinWidth(1000);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}
