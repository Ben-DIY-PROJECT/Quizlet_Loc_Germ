package model;

import model.Words.WordPair;
import java.io.File;
import java.io.IOException;
import java.util.List;

public interface Model extends Subject {

  STATUS getStatus();

  WordPair getCurrentWord();

  int getCurrentIndex();

  int getTotalNum();

  List<WordPair> getMemoryWords();

  boolean isTranslationVisible();

  void loadFromFile(File file) throws IOException;

  void nextCard();

  void prevCard();

  void showTranslation();

  void hideTranslation();

  void showMemory();

  void clearMemory();

  void enterMenu();

  void enterUpload();

  void startReading();

  void reset();

  enum STATUS {
    NO_FILE,
    LOADING,
    MENU,
    UPLOAD,
    MEMORY,
    READING
  }
}
