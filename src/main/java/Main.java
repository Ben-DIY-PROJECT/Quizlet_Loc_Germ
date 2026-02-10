import javafx.application.Application;
import view.AppLauncher;

import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException {

    // File excelFile =
    // new File("D:\\AS A TAR HEEL\\Self-Designed
    // Projet\\Quizlet_Loc_Germ\\ExampleSheet.xlsx");
    //
    // ExcelLoader loader = new ExcelLoaderImpl(excelFile);
    //
    // List<WordPair> words = loader.load();
    //
    // System.out.println(words.size());
    //
    // for (WordPair wp : words) {
    // System.out.println(wp);
    // }

    Application.launch(AppLauncher.class);
  }
}
