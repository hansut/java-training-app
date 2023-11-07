package com.accenture.training.java.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    private GameService gameService;

    @Mock
    private WordService wordService;

    @BeforeEach
    void setUp() {

        this.gameService = new GameService(this.wordService);
    }

    @Test
    void guessLetter_Correct() {
        String word = "SOMETHING";
        Mockito.doReturn(word).when(wordService).getWord();
        gameService.initialize();

        GuessResult result = gameService.guessLetter("E");
        Assertions.assertFalse(result.isWordGuessed());
        Assertions.assertEquals(1, result.getNumberOfHits());
        Assertions.assertEquals("***E*****", result.getMaskedWord());
    }

    @Test
    void guessLetter_Incorrect() {
        String word = "SOMETHING";
        Mockito.doReturn(word).when(wordService).getWord();
        gameService.initialize();
        /*
            TODO implement the rest of the test method
            - call the gameService.guessLetter() method with parameter "X"
            - write assertions on the returned GuessResult object, which will check values returned by GuessResult methods
            - it is expected that this test has no hit and returns masked word all letters masked
            - it is also expected that the flag isWordGuessed() is false
            - HINT: check guessLetter_Correct() test method in this class to find some inspiration
         */
        GuessResult result = gameService.guessLetter("X");
        Assertions.assertFalse(result.isWordGuessed());
        Assertions.assertEquals(0, result.getNumberOfHits());
        Assertions.assertEquals("*********", result.getMaskedWord());
    }

    @Test
    void guessWord_Correct() {
        String word = "SOMETHING";
        Mockito.doReturn(word).when(wordService).getWord();
        gameService.initialize();
        GuessResult result = gameService.guessWord(word);
        Assertions.assertTrue(result.isWordGuessed());
        Assertions.assertEquals(word, result.getMaskedWord());
    }

    @Test
    void guessWord_Incorrect() {
        String word = "SOMETHING";
        Mockito.doReturn(word).when(wordService).getWord();
        gameService.initialize();
        GuessResult result = gameService.guessWord("ELSE");
        Assertions.assertFalse(result.isWordGuessed());
        Assertions.assertNotEquals(word, result.getMaskedWord());
    }

    @Test
    void reset() {
        Mockito.doReturn("SOMETHING", "ELSE").when(wordService).getWord();
        gameService.initialize();
        String wordToGuess = gameService.getWordToGuess();
        gameService.reset();
        Assertions.assertNotEquals(wordToGuess, gameService.getWordToGuess());
    }

    @Test
    void setMaskedWord() {
        String word = "SOMETHING";
        gameService.setMaskedWord(word);
        Assertions.assertEquals(word.length(), gameService.getMaskedWord().length);
        for (int i = 0; i < gameService.getMaskedWord().length; i++) {
            Assertions.assertEquals(GameService.MASK_CHARACTER.charAt(0), gameService.getMaskedWord()[i]);
        }
    }
}