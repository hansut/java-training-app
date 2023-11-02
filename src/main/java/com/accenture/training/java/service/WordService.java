package com.accenture.training.java.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service class that handles operations on the list of words for guessing game.
 */
@Service
public class WordService {

    private static final Logger LOG = LoggerFactory.getLogger(WordService.class);

    private static final List<String> WORDS = List.of("ACCENTURE", "FUTURE", "TRAINING", "PERSONALITY", "PROFESSIONAL",
            "ACCELERATION", "PROJECT", "WORK");

    private final Set<String> usedWords = new HashSet<>();

    /**
     * Returns random word from the list of words.
     */
    String getNextWord() {

        int wordIndex = ThreadLocalRandom.current().nextInt(WORDS.size());
        return WORDS.get(wordIndex);
    }

    /**
     * Returns word for guessing game.
     * Guarantees that the word has not been used before.
     * If all words has been used before, returns random one and resets the cache of used words.
     */
    public String getWord() {
        if (usedWords.size() == WORDS.size()) {
            LOG.info("All words have been used, resetting the cache of used words");
            usedWords.clear();
        }

        // Get next word from the list. If it has been used, find another one (not used).
        String word = getNextWord();
        while (usedWords.contains(word)) {
            word = getNextWord();
        }

        // Add word to the list of used words and return it
        usedWords.add(word);
        return word;
    }
}
