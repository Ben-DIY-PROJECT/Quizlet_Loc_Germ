package model.Session;

import model.Words.WordPair;
import model.Words.WordPairImpl;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SessionImpl implements Session {
    // same path as project file
    private static final String DB_PATH =
            "jdbc:sqlite:" + System.getProperty("user.dir") + "/data/session.db";

    public SessionImpl() {
        initializeDatabase();
    }

    // JBDC - Java Database Connectivity
    // stmt - statement type

    private void initializeDatabase() {
        // create if no file
        new File(System.getProperty("user.dir") + "/data").mkdirs();// in the file within this
                                                                    // project
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            String wordSql = "CREATE TABLE IF NOT EXISTS word_pairs ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "german TEXT NOT NULL, "
                    + "english TEXT NOT NULL, " + "word_type TEXT NOT NULL, "
                    + "UNIQUE(german, word_type)" + ");";
            stmt.execute(wordSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void upsertWordPairs(List<WordPair> wordPairs) {
        String sql = "INSERT INTO word_pairs (german, english, word_type) VALUES (?, ?, ?) "
                + "ON CONFLICT(german, word_type) DO UPDATE SET english = excluded.english";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (WordPair pair : wordPairs) {
                stmt.setString(1, pair.getGerman());
                stmt.setString(2, pair.getEnglish());
                stmt.setString(3, pair.getType());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<WordPair> loadWordPairs() {
        List<WordPair> data = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String sql = "SELECT german, english, word_type FROM word_pairs ORDER BY id";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                data.add(new WordPairImpl(rs.getString("german"), rs.getString("english"),
                        rs.getString("word_type")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public void clearCache() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM word_pairs");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {// helper function
        return DriverManager.getConnection(DB_PATH);
    }
}
