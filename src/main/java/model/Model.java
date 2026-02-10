package model;

import model.Words.WordPair;
import java.io.File;
import java.io.IOException;

public interface Model extends Subject {

  STATUS getStatus();

  WordPair getCurrentWord();

  int getCurrentIndex();

  int getTotalNum();

  boolean isTranslationVisible();

  void loadFromFile(File file) throws IOException;

  void nextCard();

  void prevCard();

  void showTranslation();

  void hideTranslation();

  void reset();

  enum STATUS {
    NO_FILE, LOADING, READING
  }
}
