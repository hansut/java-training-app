package com.accenture.training.java.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class that encapsulates the logic of the guessing game.
 */
@Service
public class GameService {

    public static final String MASK_CHARACTER = "*";

    private final WordService wordService;

    private String wordToGuess;

    private char[] maskedWord;

    @Autowired
    public GameService(WordService wordService) {
        this.wordService = wordService;
        reset();
    }

    public GuessResult guessLetter(String letter) {
        GuessResult result = new GuessResult();
        char letterAsChar = letter.toUpperCase().charAt(0);

        for (int i = 0; i < wordToGuess.length(); i++) {
            if (wordToGuess.charAt(i) == letterAsChar) {
                result.incrementNumberOfHits();
                maskedWord[i] = letterAsChar;
            }
        }

        result.setMaskedWord(maskedWord);
        result.setWordGuessed(!result.getMaskedWord().contains(MASK_CHARACTER));
        return result;
    }

    public GuessResult guessWord(String word) {
        GuessResult result = new GuessResult();

        if (wordToGuess.equals(word.toUpperCase())) {
            result.setWordGuessed(true);
            result.setMaskedWord(wordToGuess);
            this.maskedWord = wordToGuess.toCharArray();
        } else {
            result.setWordGuessed(false);
            result.setMaskedWord(maskedWord);
        }

        return result;
    }

    public void reset() {
        this.wordToGuess = wordService.getWord();
        setMaskedWord(this.wordToGuess);
    }

    void setMaskedWord(String word) {

        this.maskedWord = word.replaceAll("[A-Z]", MASK_CHARACTER).toCharArray();
    }
}
