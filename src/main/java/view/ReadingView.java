package view;

import controller.Controller;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Model;

public class ReadingView implements FXComponent {

  private final Model model;
  private final Controller controller;

  public ReadingView(Controller controller, Model model) {
    this.controller = controller;
    this.model = model;
  }

  @Override
  public Parent render() {

    BorderPane root = new BorderPane();
    root.getStyleClass().add("reading-root");

    Label indexLabel = new Label(model.getCurrentIndex() + " / " + model.getTotalNum());
    indexLabel.getStyleClass().add("reading-title");

    Button exitButton = new Button("X");
    exitButton.getStyleClass().add("exit-button");
    exitButton.setOnAction(e -> controller.exit());

    BorderPane topBar = new BorderPane();
    topBar.setCenter(indexLabel);
    topBar.setRight(exitButton);

    root.setTop(topBar);

    Button prevButton = new Button("<");
    prevButton.getStyleClass().add("nav-button");
    prevButton.setOnAction(e -> controller.prevword());

    Button nextButton = new Button(">");
    nextButton.getStyleClass().add("nav-button");
    nextButton.setOnAction(e -> controller.nextword());

    Label germanLabel = new Label(model.getCurrentWord().getGerman());
    germanLabel.getStyleClass().add("german-word");

    HBox centerBox = new HBox(prevButton, germanLabel, nextButton);
    centerBox.getStyleClass().add("reading-center");

    root.setCenter(centerBox);

    Label englishLabel =
        new Label(model.getCurrentWord().getType() + " " + model.getCurrentWord().getEnglish());
    englishLabel.getStyleClass().add("trans-word");
    englishLabel.setVisible(model.isTranslationVisible());

    Button eyeButton = new Button();
    eyeButton.getStyleClass().add("eye-button");

    if (model.isTranslationVisible()) {
      eyeButton.setText("Hide");
    } else {
      eyeButton.setText("Show");
    }

    eyeButton.setOnAction(e -> {
      if (model.isTranslationVisible()) {
        controller.hidetrans();
      } else {
        controller.showtrans();
      }
    });

    VBox bottomBox = new VBox(englishLabel, eyeButton);
    bottomBox.getStyleClass().add("reading-bottom");

    root.setBottom(bottomBox);

    exitButton.setFocusTraversable(false);
    prevButton.setFocusTraversable(false);
    nextButton.setFocusTraversable(false);
    eyeButton.setFocusTraversable(false);

    return root;
  }
}
