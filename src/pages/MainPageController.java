package pages;

import DB.DBExtractionModification;
import dataStructures.AuthorNode;
import dataStructures.LyricNode;
import dataStructures.SongNode;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Main page layout and controller
 */
public class MainPageController implements Initializable {
    @FXML private GridPane mainGridPane;
    private ListView<AuthorNode> authorList;
    private ListView<SongNode> songList;
    private LyricNode lyricNode;
    private Label authorSongLabel = new Label();
    private TextArea songLyric;
    private Button saveLyricBtn;
    private Button editLyricEnableBtn;

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
        //System.out.println(getClass().getResource("../style/img/add.png").getPath());
        // Generate author column with favorite toggle, author list and add, edit, delete buttons
        getAuthorList();
        Button addAuthorBtn = new Button();
        Button removeAuthorBtn = new Button();
        Button editAuthorBtn = new Button();

        // Assign images for corresponding buttons
        addAuthorBtn.setGraphic(new ImageView(addImg));
        editAuthorBtn.setGraphic(new ImageView(editImg));
        removeAuthorBtn.setGraphic(new ImageView(delImg));

        editAuthorBtn.setDisable(true);
        removeAuthorBtn.setDisable(true);

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
        getSongList(authorList.getSelectionModel().getSelectedIndex());
        Button addSongBtn = new Button();
        Button removeSongBtn = new Button();
        Button editSongBtn = new Button();

        // Assign images for corresponding buttons
        addSongBtn.setGraphic(new ImageView(addImg));
        editSongBtn.setGraphic(new ImageView(editImg));
        removeSongBtn.setGraphic(new ImageView(delImg));

        // Horizontal pane for control buttons (add, edit, delete)
        HBox songHButtonBox = new HBox();
        songHButtonBox.setAlignment(Pos.CENTER);
        songHButtonBox.setSpacing(5);
        songHButtonBox.getChildren().setAll(addSongBtn, removeSongBtn, editSongBtn);

        // Song column
        GridPane songColumn = new GridPane();
        songColumn.add(songList, 0, 0);
        songColumn.add(songHButtonBox, 0, 1);

        RowConstraints songRowConstr1 = new RowConstraints(40);
        RowConstraints songRowConstr2 = new RowConstraints(400, 10000, 15000);
        songColumn.getRowConstraints().addAll(songRowConstr2, songRowConstr1);

        // Generate lyric column with author-song label, song lyric, edit lyric and save lyric
        getSongLyric(songList.getSelectionModel().getSelectedItem().getSid());
        songLyric.setEditable(false);
        editLyricEnableBtn = new Button("Enable Editing");
        editLyricEnableBtn.setDisable(false);
        saveLyricBtn = new Button("Save Lyric");
        saveLyricBtn.setDisable(true);
        authorSongLabel.setFont(new Font("Arial", 30));

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
        GridPane.setHalignment(authorSongLabel, HPos.CENTER);

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

        /**
         * Edit lyric button click event handler
         */
        editLyricEnableBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveLyricBtn.setDisable(!saveLyricBtn.isDisable());
                songLyric.setEditable(!saveLyricBtn.isDisable());
            }
        });

        /**
         * Save lyric button click event handler
         */
        saveLyricBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveLyricBtn.setDisable(!saveLyricBtn.isDisable());
                songLyric.setEditable(!saveLyricBtn.isDisable());
                lyricNode.setLyric(songLyric.getText());
            }
        });

        /**
         * Add author button click event handler
         * Request Author page
         */
        addAuthorBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AuthorNode aNode = new AuthorPageController(null).getAuthorNode();
                if (aNode != null) {
                    authorList.getItems().add(aNode);
                    authorList.refresh();
                }
            }
        });

        /**
         * Edit author button click event handler
         * Requests Author page with AuthorNode
         */
        editAuthorBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AuthorNode aNode = new AuthorPageController(authorList.getSelectionModel().getSelectedItem()).getAuthorNode();
                authorList.refresh();
                //authorSongLabel.setText(authorList.getSelectionModel().getSelectedItem().getAuthorName()+ " - " +songList.getSelectionModel().getSelectedItem().getSongName());
            }
        });

        /**
         * Remove author button event handler of selected author
         */
        removeAuthorBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Remove Author");
                alert.setHeaderText("Do you really want to delete "+ authorList.getSelectionModel().getSelectedItem().getAuthorName());
                alert.setContentText("All songs would be transferred to (Unknown)");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    new DBExtractionModification().removeAuthor(authorList.getSelectionModel().getSelectedItem().getId());
                    authorList.getItems().remove(authorList.getSelectionModel().getSelectedIndex());
                    authorList.refresh();
                } else {
                    alert.close();
                }
            }
        });

        /**
         * Add song button click event handler
         * Request Song page
         */
        addSongBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SongNode sNode = new SongPageController(null, lyricNode, authorList.getSelectionModel().getSelectedItem()).getSongNode();
                if(sNode != null) {
                    songList.getItems().add(sNode);
                    songList.refresh();
                }
            }
        });

        /**
         * Edit song button click event handler
         * Request Song page with LyricNode
         */
        editSongBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new SongPageController(songList.getSelectionModel().getSelectedItem(), lyricNode, authorList.getSelectionModel().getSelectedItem());
                songList.refresh();
            }
        });

        /**
         * Remove song button event handler of selected song
         */
        removeSongBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new DBExtractionModification().removeSong(songList.getSelectionModel().getSelectedItem().getSid());
                songList.getItems().remove(songList.getSelectionModel().getSelectedIndex());
                songList.refresh();
            }
        });

        /**
         * Update song list and lyric (first song by default) when author selected
         * Disable edit and delete buttons when (unknown) author selected
         */
        authorList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AuthorNode>() {
            @Override
            public void changed(ObservableValue<? extends AuthorNode> observable, AuthorNode oldValue, AuthorNode newValue) {
                editAuthorBtn.setDisable(newValue.getId() == 0);
                removeAuthorBtn.setDisable(newValue.getId() == 0);
                if(newValue != null) {
                    getSongList(newValue.getId());
                }
            }
        });

        /**
         * Update song lyric when select song from list
         * Update title label with author and song name
         */
        songList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(newValue != null) {
                    if(songList != null) {
                        getSongLyric(songList.getSelectionModel().getSelectedItem().getSid());
                        authorSongLabel.setText(authorList.getSelectionModel().getSelectedItem().getAuthorName() + " - " + songList.getSelectionModel().getSelectedItem().getSongName());
                        editLyricEnableBtn.setDisable(false);
                } else {
                        songList = new ListView<SongNode>();
                        editLyricEnableBtn.setDisable(true);
                    }
                }
            }
        });
    }

    /**
     * Get list of authors from db and modifies authorList
     */
    private void getAuthorList() {
        if (authorList == null) {
            authorList = new ListView<AuthorNode>();
            authorList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        }
        ArrayList<AuthorNode> authorArrList = null;
        authorArrList = new DBExtractionModification().getAuthorsList(-1);

        ObservableList<AuthorNode> authors = FXCollections.observableArrayList (authorArrList);
        authorList.setItems(authors);
        authorList.getSelectionModel().selectFirst();
        authorList.refresh();
    }

    /**
     * Get list of song regarding to selected author and modifies songList
     *
     * @param selectedAuthor int author id
     */
    private void getSongList(int selectedAuthor) {
        if (songList == null) {
            songList = new ListView<SongNode>();
            songList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        }
        ArrayList<SongNode> songArrList = null;
        songArrList = new DBExtractionModification().getSongData(selectedAuthor);

        if (!songArrList.isEmpty()){
            ObservableList<SongNode> songs = FXCollections.observableArrayList(songArrList);
            songList.setItems(songs);
            songList.getSelectionModel().selectFirst();
            songList.refresh();
            authorSongLabel.setText(authorList.getSelectionModel().getSelectedItem().getAuthorName() + " - " + songList.getSelectionModel().getSelectedItem().getSongName());
        } else {
            songList.getItems().clear();
            songList.refresh();
            authorSongLabel.setText(authorList.getSelectionModel().getSelectedItem().getAuthorName());
        }
    }

    /**
     * Get lyric of the specified song id and modifies songLyric text area
     *
     * @param songId int id of the song
     */
    private void getSongLyric(int songId){
        if(songId >= 0) {
            if (songLyric == null) {
                songLyric = new TextArea();
            }

            ArrayList<LyricNode> songsArrList = new DBExtractionModification().getLyricData(songId);
            if (!songsArrList.isEmpty()) {
                lyricNode = songsArrList.get(0);
                songLyric.setText(lyricNode.getLyric());
            } else {
                songLyric.setText(null);
            }
        } else {
            songLyric.clear();
        }

        songLyric.setEditable(false);

    }
}
