package controller;

import java.io.File;
import java.io.IOException;

public interface Controller {
  void nextword();

  void prevword();

  void exit();

  void showtrans();

  void hidetrans();

  void loadfile(File file) throws IOException;

  void viewMemory();

  void clearMemory();

  void backToMenu();

  void uploadMoreWords();

  void startReading();
}
