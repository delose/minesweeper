package com.delose.minesweeper.core.helper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class GameSetupHelperTest {

    private GameSetupHelper gameSetupHelper;

    @BeforeEach
    void setUp() {
        gameSetupHelper = new GameSetupHelper();
    }

    @Test
    void testInitializeGameComponents() {
        String userInput = "4\n3\n"; // Grid size = 4, Mines = 3
        InputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);

        GameComponents components = gameSetupHelper.initializeGameComponents();

        assertNotNull(components.getGameController());
        assertNotNull(components.getDisplayManager());
        assertNotNull(components.getInputHandler());
        assertEquals(4, components.getGameController().getMinefieldSize());
    }
}