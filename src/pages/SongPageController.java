package pages;

import DB.DBExtractionModification;
import dataStructures.AuthorNode;
import dataStructures.LyricNode;
import dataStructures.SongNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * @author Dmytro Sytnik (VanArman)
 * @version 22 February, 2018
 */
public class SongPageController {
    private GridPane songGridPane = new GridPane();
    private SongNode newSongNode;

    public SongPageController(SongNode songNode, LyricNode lyricNode, int authorId){
        StackPane songLayout = new StackPane();
        songLayout.getChildren().add(songGridPane);
        Scene songScene = new Scene(songLayout, 350, 170);
        Stage songStage = new Stage();
        songStage.setResizable(false);
        songStage.setTitle(songNode != null ? "Edit Song" : "Add Song");
        songStage.setScene(songScene);

        ArrayList<AuthorNode> authorArrList = null;
        authorArrList = new DBExtractionModification().getAuthorsList(-1);
        ObservableList<AuthorNode> authors = FXCollections.observableArrayList (authorArrList);

        ComboBox<AuthorNode> authorDropDown = new ComboBox<AuthorNode>(authors);
        authorDropDown.getSelectionModel().select(authorId);
        authorDropDown.setPrefWidth(350);

        Button saveBtn = new Button("Save");
        saveBtn.setDefaultButton(true);
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setCancelButton(true);

        Label songNameLabel = new Label("Song name: ");
        Label lyricLabel = new Label("Song Lyric: ");

        TextField songField = new TextField();
        songField.setPrefWidth(350);
        TextArea songLyric = new TextArea();

        songField.appendText(songNode != null ? songNode.getSongName() : "");
        songLyric.appendText(songNode != null ? lyricNode.getLyric() : "");

        songGridPane.setVgap(10);
        songGridPane.setHgap(10);
        GridPane.setHalignment(cancelBtn, HPos.LEFT);
        GridPane.setHalignment(saveBtn, HPos.RIGHT);
        songGridPane.setPadding(new Insets(10, 5, 10, 5));
        songGridPane.add(songNameLabel, 0, 0, 2,1);
        songGridPane.add(songField, 0, 1, 2, 1);
        songGridPane.add(authorDropDown, 0, 2, 2, 1);
        if(songNode == null) {
            songScene.getWindow().setHeight(350);
            songGridPane.add(lyricLabel, 0, 3, 2, 1);
            songGridPane.add(songLyric, 0, 4, 2, 1);
        }
        songGridPane.add(cancelBtn, 0, 5, 1, 1);
        songGridPane.add(saveBtn, 1, 5, 1, 1);

        cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                songStage.close();
            }
        });

        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(songNode == null) {
                    int newId = new DBExtractionModification().addNewSong(songField.getText(), songLyric.getText(), authorDropDown.getSelectionModel().getSelectedItem().getId());
                    newSongNode = new SongNode(newId, songField.getText(), authorDropDown.getSelectionModel().getSelectedItem().getId());
                } else {
                    new DBExtractionModification().updateSong(songField.getText(), songNode.getSid());
                    songNode.setSongName(songField.getText());
                }

                songStage.close();
            }
        });
        newSongNode = songNode;

        songStage.showAndWait();
    }

    public SongNode getSongNode() {
        return newSongNode;
    }
}
