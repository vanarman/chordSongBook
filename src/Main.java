import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        final MenuBar mainBar = new MainMenuBar().getMenu();
        final Group rootGroup = new Group();
        final Scene scene = new Scene(rootGroup);

        Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        primaryStage.setTitle("Guitar Chords Song Book");
        primaryStage.setMinHeight(640);
        primaryStage.setMinWidth(1000);

        rootGroup.getChildren().addAll(mainBar, root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
