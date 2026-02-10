package model;

import model.ExcelHandle.ExcelLoader;
import model.ExcelHandle.ExcelLoaderImpl;
import model.Words.WordPair;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ModelImpl implements Model {

  private List<WordPair> words;
  private int currentIndex;
  private boolean translationVisible;
  private STATUS status;

  private final List<Observer> observers = new ArrayList<>();

  public ModelImpl() {
    this.status = STATUS.NO_FILE;
    this.words = null;
    this.currentIndex = 0;
    this.translationVisible = false;
  }

  @Override
  public STATUS getStatus() {
    return status;
  }

  @Override
  public WordPair getCurrentWord() {
    if (status != STATUS.READING) {
      throw new IllegalStateException("No word available when not in READING state");
    }
    return words.get(currentIndex);
  }

  @Override
  public int getCurrentIndex() {
    if (status != STATUS.READING)
      return 0;
    return currentIndex + 1;
  }

  @Override
  public int getTotalNum() {
    if (status != STATUS.READING)
      return 0;
    return words.size();
  }

  @Override
  public boolean isTranslationVisible() {
    return translationVisible;
  }

  @Override
  public void loadFromFile(File file) throws IOException {
    this.status = STATUS.LOADING;
    notifyObservers();

    ExcelLoader loader = new ExcelLoaderImpl(file);
    List<WordPair> loaded = loader.load();

    if (loaded == null || loaded.isEmpty()) {
      throw new NoSuchElementException("Excel file contains no valid word pairs");
    }

    this.words = loaded;
    this.currentIndex = 0;
    this.translationVisible = false;
    this.status = STATUS.READING;

    notifyObservers();
  }

  @Override
  public void nextCard() {
    if (status != STATUS.READING)
      return;

    if (currentIndex < words.size() - 1) {
      currentIndex++;
      translationVisible = false;
      notifyObservers();
    }
  }

  @Override
  public void prevCard() {
    if (status != STATUS.READING)
      return;

    if (currentIndex > 0) {
      currentIndex--;
      translationVisible = false;
      notifyObservers();
    }
  }

  @Override
  public void showTranslation() {
    if (status != STATUS.READING)
      return;

    translationVisible = true;
    notifyObservers();
  }

  @Override
  public void hideTranslation() {
    if (status != STATUS.READING)
      return;

    translationVisible = false;
    notifyObservers();
  }

  @Override
  public void reset() { // when presses the exit button
    this.words = null;
    this.currentIndex = 0;
    this.translationVisible = false;
    this.status = STATUS.NO_FILE;
    notifyObservers();
  }

  @Override
  public void addObserver(Observer o) {
    observers.add(o);
  }

  private void notifyObservers() {
    for (Observer o : observers) {
      o.update();
    }
  }
}
