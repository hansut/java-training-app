package com.accenture.training.java.controller;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.training.java.service.GameService;
import com.accenture.training.java.service.GuessResult;

/**
 * REST controller for the guessing game.
 */
@RestController
public class GameController {

    public static final int maxWordLength = 30;

    public static final String INSTRUCTIONS = """
            Welcome to the guessing game!<br/>
            Please use following REST endpoints:<br/>
            * /letter/<letter> to guess a letter in the word<br/>
            * /word/<word> to guess the word<br/>
            * /reset to reset the guessed word<br/>
            """;

    private final GameService service;

    @Autowired
    public GameController(GameService service) {
        this.service = service;
    }

    /**
     * Print instructions.
     */
    @GetMapping(value = "/")
    @ResponseStatus(HttpStatus.OK)
    public String printInstructions() {
        return INSTRUCTIONS;
    }


    /**
     * Try to guess a letter (without parameter).
     */
    @GetMapping(value = "/letter")
    @ResponseStatus(HttpStatus.OK)
    public String guessLetter() {
        return "You entered invalid letter, please try again.";
    }

    /**
     * Try to guess a letter.
     */
    @GetMapping(value = "/letter/{letter}")
    @ResponseStatus(HttpStatus.OK)
    public String guessLetter(@PathVariable(required = false) String letter) {
        /*
            TODO implement method:
            - call method for input validation with maxLength = 1
            - if validation fails, print return a String with error message
            - call service.guessLetter() method that implements the business logic
            - return result transformed with getOutcome() method, which expects also "letter" as a second parameter
            - HINT: check method guessWord(@PathVariable(required = false) String word) in this class to find some inspiration
         */
        if (!validateInput(letter, 1)) {
            return "You entered invalid letter, please try again.";
        }
        GuessResult result = service.guessLetter(letter);
        return getOutcome(result, letter);
    }

    /**
     * Try to guess the whole word (without parameter).
     */
    @GetMapping(value = "/word")
    @ResponseStatus(HttpStatus.OK)
    public String guessWord() {
        return "You entered invalid word, please try again.";
    }

    /**
     * Try to guess the whole word.
     */
    @GetMapping(value = "/word/{word}")
    @ResponseStatus(HttpStatus.OK)
    public String guessWord(@PathVariable(required = false) String word) {
        if (!validateInput(word, maxWordLength)) {
            return "You entered invalid word, please try again.";
        }
        GuessResult result = service.guessWord(word);
        return getOutcome(result, null);
    }

    /**
     * Reset the game to a new word.
     */
    @GetMapping(value = "/reset")
    @ResponseStatus(HttpStatus.OK)
    public String reset() {
        service.reset();
        return "The game has been reset, you can start guessing another word";
    }

    /**
     * Get formatted text to print as an outcome of a guess.
     */
    String getOutcome(GuessResult result, String letter) {
        String outcome = "";
        if (result.isWordGuessed()) {
            outcome += "Congratulations! You guessed the word!<br/>";
        } else if (letter == null) {
            outcome += "Wrong guess.<br/>";
        }
        if (letter != null) {
            outcome += "Letter " + letter.toUpperCase() + " is present " + result.getNumberOfHits() + " times.<br/>";
        }
        outcome += "The word is '" + result.getMaskedWord() + "' and has " + result.getMaskedWord().length() + " letters.<br/>";
        if (result.isWordGuessed()) {
            outcome += "If you want to repeat the game with another word, please call /reset endpoint.<br/>";
        }
        return outcome;
    }

    /**
     * Validate the text input from URL. Checks:
     * - if the text is not empty
     * - if it contains only character (upper or lowercase) up to allowed length.
     */
    boolean validateInput(String input, int maxLength) {
        if (!StringUtils.hasText(input)) {
            return false;
        }

        String regex = "[a-zA-Z]{1," + maxLength + "}";
        return Pattern.matches(regex, input);
    }
}
