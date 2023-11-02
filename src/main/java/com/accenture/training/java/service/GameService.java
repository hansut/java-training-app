package com.accenture.training.java.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

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
    }

    @PostConstruct
    public void initialize() {
        reset();
    }

    /**
     * Implementation of logic when player tries to guess a letter.
     */
    public GuessResult guessLetter(String letter) {
        GuessResult result = new GuessResult();
        // convert letter to uppercase, so it can be easily matched with letters in the word
        char letterAsChar = letter.toUpperCase().charAt(0);

        // check if the letter is present in the word; if it is, update result and masked word
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

    /**
     * Implementation of logic when player tries to guess the word.
     */
    public GuessResult guessWord(String word) {
        GuessResult result = new GuessResult();

        // when guessing the word, it is needed to convert it to uppercase for correct matching
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

    /**
     * Reset the game to start from beginning.
     */
    public void reset() {
        this.wordToGuess = wordService.getWord();
        setMaskedWord(this.wordToGuess);
    }

    /**
     * Set masked word to the variable.
     */
    void setMaskedWord(String word) {
        this.maskedWord = word.replaceAll("[A-Z]", MASK_CHARACTER).toCharArray();
    }

    String getWordToGuess() {
        return wordToGuess;
    }

    char[] getMaskedWord() {
        return maskedWord;
    }
}
