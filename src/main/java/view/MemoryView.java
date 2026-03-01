package view;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Model;
import model.Words.WordPair;

import java.util.List;
import java.util.stream.Collectors;

public class MemoryView implements FXComponent {
  private final Controller controller;
  private final Model model;

  public MemoryView(Controller controller, Model model) {
    this.controller = controller;
    this.model = model;
  }

  @Override
  public Parent render() {
    BorderPane root = new BorderPane();
    root.getStyleClass().add("memory-root");

    Label title = new Label("Database Words");
    title.getStyleClass().add("memory-title");

    List<String> lines = model.getMemoryWords().stream().map(this::formatLine).collect(Collectors.toList());
    ListView<String> listView = new ListView<>(FXCollections.observableArrayList(lines));
    listView.getStyleClass().add("memory-list");

    Label countLabel = new Label("Total: " + model.getMemoryWords().size());
    countLabel.getStyleClass().add("memory-count");

    Button clearButton = new Button("Clear Memory");
    clearButton.getStyleClass().add("memory-clear-button");
    clearButton.setOnAction(e -> controller.clearMemory());

    Button backButton = new Button("Back");
    backButton.getStyleClass().add("memory-back-button");
    backButton.setOnAction(e -> controller.backToMenu());

    HBox actions = new HBox(backButton, clearButton);
    actions.getStyleClass().add("memory-actions");

    VBox content = new VBox(title, countLabel, listView, actions);
    content.getStyleClass().add("memory-content");

    root.setCenter(content);
    return root;
  }

  private String formatLine(WordPair pair) {
    return pair.getGerman() + " -> " + pair.getType() + " " + pair.getEnglish();
  }
}
