package com.delose.minesweeper.core.helper;
import com.delose.minesweeper.core.exception.GameInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class GameSetupHelperTest {

    private GameSetupHelper gameSetupHelper;

    @BeforeEach
    void setUp() {
        gameSetupHelper = new GameSetupHelper();
    }

    @Nested
    @DisplayName("Game Component Initialization Tests")
    class GameComponentInitializationTests {

        @Test
        @DisplayName("Should initialize game components successfully with valid input")
        void testInitializeGameComponents_Success() {
            // Given: User input for grid size = 4 and mines = 3
            String userInput = "4\n3\n"; 
            InputStream in = new ByteArrayInputStream(userInput.getBytes());
            System.setIn(in);

            // When: Initialize game components
            GameComponents components = gameSetupHelper.initializeGameComponents();

            // Then: Verify the components are initialized correctly
            assertNotNull(components.getGameController());
            assertNotNull(components.getDisplayManager());
            assertNotNull(components.getInputHandler());
            assertEquals(4, components.getGameController().getMinefieldSize());
        }

        @Test
        @DisplayName("Should throw GameInputException for invalid grid size")
        void testInitializeGameComponents_InvalidGridSize_Failure() {
            // Given: User input for invalid grid size
            String userInput = "100\n3\n"; 
            InputStream in = new ByteArrayInputStream(userInput.getBytes());
            System.setIn(in);

            // When/Then: Expect GameInputException due to invalid grid size
            assertThrows(GameInputException.class, () -> gameSetupHelper.initializeGameComponents());
        }

        @Test
        @DisplayName("Should throw NoSuchElementException for invalid number of mines")
        void testInitializeGameComponents_InvalidMines_Failure() {
            // Given: User input for grid size = 4 and an invalid number of mines = 20
            String userInput = "4\n20\n"; 
            InputStream in = new ByteArrayInputStream(userInput.getBytes());
            System.setIn(in);

            // When/Then: Expect NoSuchElementException due to invalid number of mines
            assertThrows(NoSuchElementException.class, () -> gameSetupHelper.initializeGameComponents());
        }

        @Test
        @DisplayName("Should throw GameInputException for non-numeric grid size")
        void testInitializeGameComponents_NonNumericGridSize_Failure() {
            // Given: User input with non-numeric grid size
            String userInput = "abc\n3\n"; 
            InputStream in = new ByteArrayInputStream(userInput.getBytes());
            System.setIn(in);

            // When/Then: Expect GameInputException due to non-numeric grid size
            assertThrows(GameInputException.class, () -> gameSetupHelper.initializeGameComponents());
        }
    }
}