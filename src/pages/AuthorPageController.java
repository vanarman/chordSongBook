package pages;

import DB.DBExtractionModification;
import dataStructures.AuthorNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @author Dmytro Sytnik (VanArman)
 * @version 22 February, 2018
 */
public class AuthorPageController {
        private GridPane authorGridPane = new GridPane();
        private AuthorNode newAuthorNode;

    public AuthorPageController(AuthorNode authorNode) {
        StackPane authorLayout = new StackPane();
        authorLayout.getChildren().add(authorGridPane);
        Scene authorScene = new Scene(authorLayout, 370, 70);
        Stage authorStage = new Stage();
        authorStage.setResizable(false);
        authorStage.setTitle(authorNode != null ? "Edit Author" : "Add Author");
        authorStage.setScene(authorScene);

        Label authorLabel = new Label("Author/Group Name: ");
        TextField authorNameField = new TextField();
        authorNameField.appendText(authorNode != null ? authorNode.getAuthorName() : "");
        authorNameField.setPrefWidth(200);
        Button saveBtn = new Button("Save");
        saveBtn.setDefaultButton(true);
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setCancelButton(true);

        authorGridPane.setVgap(10);
        authorGridPane.setHgap(10);
        authorGridPane.add(authorLabel, 0, 0);
        authorGridPane.add(authorNameField, 1, 0);
        authorGridPane.add(cancelBtn, 0, 1);
        authorGridPane.add(saveBtn, 1, 1);
        GridPane.setHalignment(saveBtn, HPos.CENTER);
        GridPane.setHalignment(cancelBtn, HPos.CENTER);
        authorGridPane.setPadding(new Insets(10, 5, 10, 5));

        ColumnConstraints col0 = new ColumnConstraints(150);
        ColumnConstraints col1 = new ColumnConstraints(200);
        authorGridPane.getColumnConstraints().addAll(col0, col1);

        cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                authorStage.close();
            }
        });

        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(authorNode == null) {
                    int newId = new DBExtractionModification().updateAuthor(authorNameField.getText(), -1);
                    newAuthorNode = new AuthorNode(newId, authorNameField.getText());
                } else {
                    new DBExtractionModification().updateAuthor(authorNameField.getText(), authorNode.getId());
                    authorNode.setAuthorName(authorNameField.getText());
                }

                authorStage.close();
            }
        });

        newAuthorNode = authorNode;

        authorStage.showAndWait();
    }

    public AuthorNode getAuthorNode() {
        return newAuthorNode;
    }
}
