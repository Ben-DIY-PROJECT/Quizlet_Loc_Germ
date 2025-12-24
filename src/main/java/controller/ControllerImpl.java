package controller;

import model.Model;

import java.io.File;
import java.io.IOException;

public class ControllerImpl implements Controller {
  private Model model;

  public ControllerImpl(Model model) {
    this.model = model;
  }

  @Override
  public void nextword() {
    this.model.nextCard();
  }

  @Override
  public void prevword() {
    this.model.prevCard();
  }

  @Override
  public void exit() {
    this.model.reset();
  }

  @Override
  public void showtrans() {
    this.model.showTranslation();
  }

  @Override
  public void hidetrans() {
    this.model.hideTranslation();
  }

  @Override
  public void loadfile(File file) throws IOException {
    this.model.loadFromFile(file);
  }
}
