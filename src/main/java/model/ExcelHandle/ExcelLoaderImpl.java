package model.ExcelHandle;

import model.Words.WordPair;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Words.WordPairImpl;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelLoaderImpl implements ExcelLoader {
  private final File file;

  public ExcelLoaderImpl(File file) {
    this.file = file;
  }

  @Override
  public List<WordPair> load() throws IOException {
    List<WordPair> wordlist = new ArrayList<>();

    try (FileInputStream fis = new FileInputStream(file); ) {
      Workbook workbook = new XSSFWorkbook(fis);

      Sheet sheet = workbook.getSheetAt(0); // get the first sheet

      for (Row row : sheet) { // uses the sheet iterator, will only traverse the written rows
        if (row.getRowNum() == 0) continue; // skip the first row

        Cell germanCell = row.getCell(0); // 0th ool
        Cell englishCell = row.getCell(1); // 1st col
        Cell wordtypeCell = row.getCell(2); // 2nd col

        if (germanCell == null || englishCell == null || wordtypeCell == null) continue;

        wordlist.add(
            new WordPairImpl(readCell(germanCell), readCell(englishCell), readCell(wordtypeCell)));
      }

    } catch (IOException e) {
      throw new IOException("Something is Wrong with the Input File： " + file.getAbsolutePath());
    }

    return wordlist;
  }

  private String readCell(Cell cell) {
    cell.setCellType(CellType.STRING);
    return cell.getStringCellValue().trim();
  }
}
