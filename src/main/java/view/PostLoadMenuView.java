package view;

import controller.Controller;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Model;

public class PostLoadMenuView implements FXComponent {
  private final Controller controller;
  private final Model model;

  public PostLoadMenuView(Controller controller, Model model) {
    this.controller = controller;
    this.model = model;
  }

  @Override
  public Parent render() {
    BorderPane root = new BorderPane();
    root.getStyleClass().add("menu-root");

    Label title = new Label("Choose an Option");
    title.getStyleClass().add("menu-title");

    Button startButton = new Button("Start Studying");
    startButton.getStyleClass().add("menu-button");
    startButton.setOnAction(e -> {
      controller.startReading();
      if (model.getStatus() != Model.STATUS.READING) {
        showWarning("WordBase is empty, please upload words first.");
      }
    });

    Button memoryButton = new Button("View WordBase");
    memoryButton.getStyleClass().add("menu-button");
    memoryButton.setOnAction(e -> controller.viewMemory());

    Button uploadButton = new Button("Upload more Words");
    uploadButton.getStyleClass().add("menu-button");
    uploadButton.setOnAction(e -> controller.uploadMoreWords());

    VBox center = new VBox(title, startButton, memoryButton, uploadButton);
    center.getStyleClass().add("menu-center");

    root.setCenter(center);
    return root;
  }

  private void showWarning(String msg) {
    Alert alert = new Alert(Alert.AlertType.NONE);
    alert.setTitle("No Words Available");
    alert.setHeaderText(null);

    Label icon = new Label("⚠");
    icon.setStyle("-fx-font-size: 24;");

    Label text = new Label(msg);
    text.setWrapText(true);

    HBox row = new HBox(12, icon, text);
    row.setAlignment(Pos.CENTER_LEFT);

    alert.getDialogPane().setContent(row);
    alert.getButtonTypes().setAll(ButtonType.OK);
    alert.showAndWait();
  }
}
