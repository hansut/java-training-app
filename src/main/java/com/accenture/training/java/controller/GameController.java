package com.accenture.training.java.controller;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.training.java.service.GameService;
import com.accenture.training.java.service.GuessResult;

/**
 * REST controller for the guessing game.
 */
@RestController
public class GameController {

    public static final int MAX_WORD_LENGTH = 30;

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
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String printInstructions() {
        return INSTRUCTIONS;
    }


    /**
     * Try to guess a letter (without parameter).
     */
    @RequestMapping(value = "/letter", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String guessLetter() {
        return "You entered invalid letter, please try again.";
    }

    /**
     * Try to guess a letter.
     */
    @RequestMapping(value = "/letter/{letter}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String guessLetter(@PathVariable(required = false) String letter) {
        if (!validateInput(letter, 1)) {
            return "You entered invalid letter, please try again.";
        }
        GuessResult result = service.guessLetter(letter);
        return getOutcome(result, letter);
    }

    /**
     * Try to guess the whole word (without parameter).
     */
    @RequestMapping(value = "/word", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String guessWord() {
        return "You entered invalid word, please try again.";
    }

    /**
     * Try to guess the whole word.
     */
    @RequestMapping(value = "/word/{word}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String guessWord(@PathVariable(required = false) String word) {
        if (!validateInput(word, MAX_WORD_LENGTH)) {
            return "You entered invalid word, please try again.";
        }
        GuessResult result = service.guessWord(word);
        return getOutcome(result, null);
    }

    /**
     * Reset the game to a new word.
     */
    @RequestMapping(value = "/reset", method = RequestMethod.GET)
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
