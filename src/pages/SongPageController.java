package pages;

import dataStructures.LyricNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @author Dmytro Sytnik (VanArman)
 * @version 22 February, 2018
 */
public class SongPageController {
    private GridPane songGridPane = new GridPane();

    public SongPageController(LyricNode song){
        StackPane songLayout = new StackPane();
        songLayout.getChildren().add(songGridPane);
        Scene songScene = new Scene(songLayout, 370, 330);
        Stage songStage = new Stage();
        songStage.setResizable(false);
        songStage.setTitle(song != null ? "Edit Song" : "Add Song");
        songStage.setScene(songScene);
        songStage.show();

        Button saveBtn = new Button("Save");
        saveBtn.setDefaultButton(true);
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setCancelButton(true);

        Label songNameLabel = new Label("Song name: ");
        Label lyricLabel = new Label("Song Lyric: ");

        TextField songField = new TextField();
        TextArea songLyric = new TextArea();

        songField.appendText(song != null ? song.getSongName() : "");
        songLyric.appendText(song != null ? song.getLyric() : "");

        songGridPane.setVgap(10);
        songGridPane.setHgap(10);
        GridPane.setHalignment(cancelBtn, HPos.LEFT);
        GridPane.setHalignment(saveBtn, HPos.RIGHT);
        songGridPane.setPadding(new Insets(10, 5, 10, 5));
        songGridPane.add(songNameLabel, 0, 0, 2,1);
        songGridPane.add(songField, 0, 1, 2, 1);
        songGridPane.add(lyricLabel, 0, 2, 2, 1);
        songGridPane.add(songLyric, 0, 3, 2, 1);
        songGridPane.add(cancelBtn, 0, 4, 1, 1);
        songGridPane.add(saveBtn, 1, 4, 1, 1);

        cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                songStage.close();
            }
        });
    }
}
