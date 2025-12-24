package model.ExcelHandle;

import model.Words.WordPair;

import java.io.IOException;
import java.util.List;

public interface ExcelLoader {
  List<WordPair> load() throws IOException;
}
