package com.accenture.training.java.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordServiceTest {

    private WordService service;

    @BeforeEach
    void setUp() {
        this.service = new WordService();
    }

    @Test
    void getNextWord() {
        Assertions.assertNotNull(service.getNextWord());
    }

    @Test
    void getWord() {
        String word1 = service.getWord();
        String word2 = service.getWord();
        String word3 = service.getWord();

        Assertions.assertTrue(service.usedWords.contains(word1));
        Assertions.assertTrue(service.usedWords.contains(word2));
        Assertions.assertTrue(service.usedWords.contains(word3));

        Assertions.assertNotEquals(word1, word2);
        Assertions.assertNotEquals(word1, word3);
        Assertions.assertNotEquals(word2, word3);
    }
}