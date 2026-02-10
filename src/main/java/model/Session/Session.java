package model.Session;

import model.Words.WordPair;

import java.util.List;

public interface Session {
    void upsertWordPairs(List<WordPair> wordPairs); // Insert or update word pairs

    List<WordPair> loadWordPairs(); // Load word pairs from SQLite

    void clearCache(); // Delete Cache
}
