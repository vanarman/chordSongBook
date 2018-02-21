package pages;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Main page layout and controller
 */
public class MainPageController implements Initializable {
    @FXML private GridPane mainGridPane;

    /**
     * Initializes enter point
     *
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources) {
        // Default images for add, edit and delete buttons
        Image addImg = new Image(getClass().getResourceAsStream("../style/img/add.png"));
        Image delImg = new Image(getClass().getResourceAsStream("../style/img/delete.png"));
        Image editImg = new Image(getClass().getResourceAsStream("../style/img/edit.png"));

        // Generate author column with favorite toggle, author list and add, edit, delete buttons
        ListView authorList = new ListView(null);
        Button addAuthorBtn = new Button();
        Button removeAuthorBtn = new Button();
        Button editAuthorBtn = new Button();

        // Assign images for corresponding buttons
        addAuthorBtn.setGraphic(new ImageView(addImg));
        editAuthorBtn.setGraphic(new ImageView(editImg));
        removeAuthorBtn.setGraphic(new ImageView(delImg));

        // Horizontal pane for control buttons (add, edit, delete)
        HBox authorHButtonBox = new HBox();
        authorHButtonBox.setAlignment(Pos.CENTER);
        authorHButtonBox.setSpacing(10);
        authorHButtonBox.getChildren().setAll(addAuthorBtn, removeAuthorBtn, editAuthorBtn);

        // Group of buttons All or Favorite toggling
        ToggleGroup favoriteGroup = new ToggleGroup();
        ToggleButton allSongsBtn = new ToggleButton("All");
        ToggleButton favoriteSongsBtn = new ToggleButton("Favorites");
        allSongsBtn.setToggleGroup(favoriteGroup);
        allSongsBtn.setSelected(true);
        favoriteSongsBtn.setToggleGroup(favoriteGroup);

        // Horizontal group of toggling buttons
        HBox toggleFavoriteGroup = new HBox();
        toggleFavoriteGroup.setAlignment(Pos.CENTER);
        toggleFavoriteGroup.setSpacing(5);
        toggleFavoriteGroup.getChildren().addAll(allSongsBtn, favoriteSongsBtn);

        // Author column
        GridPane authorColumn = new GridPane();
        authorColumn.add(toggleFavoriteGroup, 0,0);
        authorColumn.add(authorList, 0, 1);
        authorColumn.add(authorHButtonBox, 0, 2);

        RowConstraints authorRowConstr1 = new RowConstraints(40);
        RowConstraints authorRowConstr2 = new RowConstraints(400, 10000, 15000);
        authorColumn.getRowConstraints().addAll(authorRowConstr1, authorRowConstr2, authorRowConstr1);

        // Generate songs column with author list and add, edit, delete buttons
        ListView songList = new ListView(null);
        Button addSong = new Button();
        Button removeSong = new Button();
        Button editSong = new Button();

        // Assign images for corresponding buttons
        addSong.setGraphic(new ImageView(addImg));
        editSong.setGraphic(new ImageView(editImg));
        removeSong.setGraphic(new ImageView(delImg));

        // Horizontal pane for control buttons (add, edit, delete)
        HBox songHButtonBox = new HBox();
        songHButtonBox.setAlignment(Pos.CENTER);
        songHButtonBox.setSpacing(5);
        songHButtonBox.getChildren().setAll(addSong, removeSong, editSong);

        // Song column
        GridPane songColumn = new GridPane();
        songColumn.add(songList, 0, 0);
        songColumn.add(songHButtonBox, 0, 1);

        RowConstraints songRowConstr1 = new RowConstraints(40);
        RowConstraints songRowConstr2 = new RowConstraints(400, 10000, 15000);
        songColumn.getRowConstraints().addAll(songRowConstr2, songRowConstr1);

        // Generate lyric column with author-song label, song lyric, edit lyric and save lyric
        TextArea songLyric = new TextArea();
        Button editLyricEnableBtn = new Button("Enable Editing");
        Button saveLyricBtn = new Button("Save Lyric");
        Label authorSongLabel = new Label("Author - Song");

        // Horizontal pane for edit save lyric buttons
        HBox lyricButtonsHBox = new HBox();
        lyricButtonsHBox.setSpacing(50);
        lyricButtonsHBox.setAlignment(Pos.CENTER);
        lyricButtonsHBox.getChildren().addAll(editLyricEnableBtn, saveLyricBtn);

        // Lyric column
        GridPane lyricColumn = new GridPane();
        lyricColumn.add(authorSongLabel, 0, 0);
        lyricColumn.add(songLyric, 0, 1);
        lyricColumn.add(lyricButtonsHBox, 0, 2);
        lyricColumn.setHalignment(authorSongLabel, HPos.CENTER);

        RowConstraints lyricRowConstr1 = new RowConstraints(40);
        RowConstraints lyricRowConstr2 = new RowConstraints(200, 10000, 15000);
        ColumnConstraints lyricColumn0 = new ColumnConstraints(200, 10000, 15000);
        lyricColumn.getColumnConstraints().addAll(lyricColumn0);
        lyricColumn.getRowConstraints().addAll(lyricRowConstr1, lyricRowConstr2, lyricRowConstr1);


        Button closeProgram = new Button("Exit");

        // Main pane configuration
        mainGridPane.setVgap(10);
        mainGridPane.setHgap(10);

        mainGridPane.setPadding(new Insets(10, 5, 10, 5));

        mainGridPane.add(authorColumn, 0, 0);
        mainGridPane.add(lyricColumn, 1,0);
        mainGridPane.add(songColumn, 2,0);

        ColumnConstraints column1 = new ColumnConstraints(250);
        ColumnConstraints column2 = new ColumnConstraints(400, 600, 50000);
        RowConstraints row1 = new RowConstraints(400, 600, 50000);

        mainGridPane.getColumnConstraints().addAll(column1, column2, column1);
        mainGridPane.getRowConstraints().addAll(row1);

        column1.setHgrow(Priority.ALWAYS);
        column2.setHgrow(Priority.ALWAYS);
        row1.setVgrow(Priority.ALWAYS);

        GridPane.setHalignment(authorColumn, HPos.LEFT);
        GridPane.setHalignment(lyricColumn, HPos.CENTER);
        GridPane.setHalignment(songColumn, HPos.RIGHT);
    }
}
