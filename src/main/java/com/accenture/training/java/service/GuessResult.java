package com.accenture.training.java.service;

/**
 * POJO class that represents result of player's guess.
 */
public class GuessResult {

    private String maskedWord;

    private int numberOfHits = 0;

    private boolean wordGuessed = false;

    public String getMaskedWord() {

        return maskedWord;
    }

    public void setMaskedWord(char[] maskedWord) {

        this.maskedWord = String.valueOf(maskedWord);
    }

    public void setMaskedWord(String maskedWord) {

        this.maskedWord = maskedWord;
    }

    public int getNumberOfHits() {

        return numberOfHits;
    }

    public void incrementNumberOfHits() {
        this.numberOfHits++;
    }

    public boolean isWordGuessed() {

        return wordGuessed;
    }

    public void setWordGuessed(boolean wordGuessed) {

        this.wordGuessed = wordGuessed;
    }

    @Override
    public String toString() {

        return "GuessResult{" +
                "maskedWord='" + maskedWord + '\'' +
                ", numberOfHits=" + numberOfHits +
                ", wordGuessed=" + wordGuessed +
                '}';
    }
}
