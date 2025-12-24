package model.Words;

public class WordPairImpl implements WordPair {
  private final String German;
  private final String English;
  private final String WordType;

  public WordPairImpl(String German, String English, String WordType) {
    this.German = German;
    this.English = English;
    this.WordType = WordType;
  }

  @Override
  public String getGerman() {
    return German;
  }

  @Override
  public String getEnglish() {
    return English;
  }

  @Override
  public String getType() {
    return WordType;
  }
  ;

  @Override
  public String toString() {
    return German + " -> " + WordType + " " + English;
  }
}
