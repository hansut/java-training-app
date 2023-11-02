package com.accenture.training.java.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameControllerTest {

    private GameController controller;

    @BeforeEach
    void init() {
        controller = new GameController(null);
    }
}
