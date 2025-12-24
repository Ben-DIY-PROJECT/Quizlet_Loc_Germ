package view;

import controller.Controller;
import controller.ControllerImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Model;
import model.ModelImpl;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {

    stage.setTitle("Quizlet_Loc");
    Model model = new ModelImpl();
    Controller controller = new ControllerImpl(model);
    View view = new View(stage, controller, model);

    model.addObserver(view);

    Scene scene = new Scene(view.render(), 600, 350);
    scene.getStylesheets().add("Quizlet_Loc.css");

    stage.setScene(scene);

    stage.show();
  }
}
