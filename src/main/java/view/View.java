package view;

import controller.Controller;
import javafx.scene.Parent;
import javafx.stage.Stage;
import model.Model;
import model.Observer;

public class View implements FXComponent, Observer {
  private Model model;
  private Controller controller;
  private Stage stage;

  public View(Stage stage, Controller controller, Model model) {
    this.stage = stage;
    this.model = model;
    this.controller = controller;
  }

  @Override
  public Parent render() {
    switch (model.getStatus()) {
      case MENU:
        return new PostLoadMenuView(controller, model).render();
      case NO_FILE, LOADING, UPLOAD:
        return new DropBoxView(controller, model).render();
      case MEMORY:
        return new MemoryView(controller, model).render();
      case READING:
        return new ReadingView(controller, model).render();
      default:
        return new DropBoxView(controller, model).render();
    }
  }

  @Override
  public void update() {
    Parent root = render();
    stage.getScene().setRoot(root);
  }
}
