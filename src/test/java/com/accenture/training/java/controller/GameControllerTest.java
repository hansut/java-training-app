package com.accenture.training.java.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameControllerTest {

    private GameController controller;

    @BeforeEach
    void setUp() {
        controller = new GameController(null);
    }

    @Test
    void validateInput() {

        Assertions.assertTrue(controller.validateInput("z", 1));
        Assertions.assertTrue(controller.validateInput("Z", 1));
        Assertions.assertFalse(controller.validateInput("zzzz", 1));
        Assertions.assertFalse(controller.validateInput(null, 1));
        Assertions.assertFalse(controller.validateInput("", 1));

        Assertions.assertTrue(controller.validateInput("someword", 30));
        Assertions.assertTrue(controller.validateInput("SOMEWORD", 30));
        Assertions.assertFalse(controller.validateInput("SOME WORD", 30));
        Assertions.assertFalse(controller.validateInput("1234", 30));
        Assertions.assertFalse(controller.validateInput(null, 1));
        Assertions.assertFalse(controller.validateInput("", 1));
    }
}
