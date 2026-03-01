package view;

import controller.Controller;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Model;
import java.io.File;
import java.io.IOException;

public class DropBoxView implements FXComponent {
  private final Model model;
  private final Controller controller;

  public DropBoxView(Controller controller, Model model) {
    this.controller = controller;
    this.model = model;
  }

  @Override
  public Parent render() {
    BorderPane root = new BorderPane();
    root.getStyleClass().add("dropbox-root");

    Button infoButton = new Button("!");
    infoButton.getStyleClass().add("info-button");
    infoButton.setOnAction(e -> showInfo());

    Button backButton = new Button("Back to Home");
    backButton.getStyleClass().add("menu-secondary-button");
    backButton.setOnAction(e -> controller.backToMenu());

    Label title = new Label("Quizlet~Local Version");
    title.getStyleClass().add("title-style");

    HBox titleBox = new HBox(title);
    titleBox.getStyleClass().add("title-box");

    HBox rightActions = new HBox(backButton, infoButton);
    rightActions.getStyleClass().add("top-actions");

    BorderPane topBar = new BorderPane();
    topBar.getStyleClass().add("top-bar");
    topBar.setLeft(titleBox);
    topBar.setRight(rightActions);

    root.setTop(topBar);

    StackPane dropArea = new StackPane();
    dropArea.getStyleClass().add("drop-area");

    Label plus = new Label("+");
    plus.getStyleClass().add("plus-label");

    Label hint = new Label("Drag Excel file here");
    hint.getStyleClass().add("hint-label");

    VBox centerBox = new VBox(plus, hint);
    centerBox.getStyleClass().add("center-box");

    dropArea.getChildren().add(centerBox);
    root.setCenter(dropArea);

    dropArea.setOnDragOver(e -> {
      if (e.getDragboard().hasFiles()) {
        e.acceptTransferModes(TransferMode.COPY);
      }

      e.consume();
    });

    dropArea.setOnDragDropped(e -> {
      Dragboard db = e.getDragboard();
      boolean success = false;
      if (db.hasFiles()) {
        File file = db.getFiles().get(0);
        if (isExcel(file)) {
          try {
            controller.loadfile(file); // status will change
            success = true;
          } catch (IOException ex) {
            showError("Failed to load file");
          }
        } else {
          showError("Please upload a valid .xlsx file");
        }
      }
      e.setDropCompleted(success);
      e.consume();
    });

    return root;
  }

  private void showInfo() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Instructions");
    alert.setHeaderText("Excel Format Requirement");
    alert.setContentText("""
        • Exactly 3 columns
        • Column 1: German
        • Column 2: English
        • Column 3: Word Type
        • First row must be headers
        """);
    alert.showAndWait();
  }

  private void showError(String msg) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setContentText(msg);
    alert.showAndWait();
  }

  private boolean isExcel(File file) {
    return file.getName().toLowerCase().endsWith(".xlsx");
  }
}
