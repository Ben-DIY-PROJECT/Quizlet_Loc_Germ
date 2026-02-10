package testMain;

import model.ExcelHandle.ExcelLoader;
import model.ExcelHandle.ExcelLoaderImpl;
import model.Session.Session;
import model.Session.SessionImpl;
import model.Words.WordPair;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class StorageTest {
    public static void main(String[] args) {
        try {
            File excelFile = new File("ExampleSheet copy.xlsx");

            ExcelLoader loader = new ExcelLoaderImpl(excelFile);
            List<WordPair> words = loader.load();

            System.out.println("Loaded " + words.size() + " words from Excel.");

            // check for storage function
            Session session = new SessionImpl();

            session.upsertWordPairs(words);
            System.out.println("Upserted " + words.size() + " word pairs into database.");

            List<WordPair> loadedWords = session.loadWordPairs();
            System.out.println("Loaded " + loadedWords.size() + " word pairs from database.");

            if (!loadedWords.isEmpty()) {
                System.out.println("First record: " + loadedWords.get(0));
                System.out.println("DB path = " + "jdbc:sqlite:" + System.getProperty("user.dir")
                        + "/data/session.db");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
