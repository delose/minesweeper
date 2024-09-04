package com.delose.minesweeper.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.delose.minesweeper.controller.impl.PlayerInputHandlerImpl;
import com.delose.minesweeper.core.exception.GameInputException;

import static org.junit.jupiter.api.Assertions.*;

class PlayerInputHandlerTest {

    private PlayerInputHandler inputHandler;

    @BeforeEach
    void setUp() {
        inputHandler = new PlayerInputHandlerImpl(4); // Assume a 4x4 grid
    }

    @Test
    void testValidInput() {
        String input = "A1"; // Valid input
        assertDoesNotThrow(() -> {
            inputHandler.parseInput(input);
        });
    }

    @Nested
    @DisplayName("Throw GameInputException Scenarios")
    class ThrowGameInputExceptionScenarios {

        @Test
        @DisplayName("Null input should throw GameInputException")
        void testParseInput_NullInput_ThrowsException() {
            // Given a null input
            String input = null;

            // When & Then: Expecting GameInputException to be thrown
            assertThrows(GameInputException.class, () -> inputHandler.parseInput(input), 
                "Invalid input format. Please use a valid grid position (e.g., A1).");
        }

        @Test
        @DisplayName("Input too short should throw GameInputException")
        void testParseInput_InputTooShort_ThrowsException() {
            // Given an input string that is too short
            String input = "A";

            // When & Then: Expecting GameInputException to be thrown
            assertThrows(GameInputException.class, () -> inputHandler.parseInput(input), 
                "Invalid input format. Please use a valid grid position (e.g., A1).");
        }

        @Test
        @DisplayName("Input too long should throw GameInputException")
        void testParseInput_InputTooLong_ThrowsException() {
            // Given an input string that is too long
            String input = "A123";

            // When & Then: Expecting GameInputException to be thrown
            assertThrows(GameInputException.class, () -> inputHandler.parseInput(input), 
                "Invalid input format. Please use a valid grid position (e.g., A1).");
        }

        @Test
        @DisplayName("Non-numeric column should throw GameInputException")
        void testParseInput_NonNumericColumn_ThrowsException() {
            // Given an input string with a non-numeric column
            String input = "A#";

            // When & Then: Expecting GameInputException to be thrown
            assertThrows(GameInputException.class, () -> inputHandler.parseInput(input), 
                "Invalid input format. Please use a valid grid position (e.g., A1).");
        }

        @Test
        @DisplayName("Input out of row bounds should throw GameInputException")
        void testParseInput_InputOutOfRowBounds_ThrowsException() {
            // Given an input string with an out-of-bounds row
            String input = "E1"; // E is outside the 4x4 grid

            // When & Then: Expecting GameInputException to be thrown
            assertThrows(GameInputException.class, () -> inputHandler.parseInput(input), 
                "Input is out of bounds. Please select a valid grid position within the grid size.");
        }

        @Test
        @DisplayName("Input out of column bounds should throw GameInputException")
        void testParseInput_InputOutOfColumnBounds_ThrowsException() {
            // Given an input string with an out-of-bounds column
            String input = "A5"; // 5 is outside the 4x4 grid

            // When & Then: Expecting GameInputException to be thrown
            assertThrows(GameInputException.class, () -> inputHandler.parseInput(input), 
                "Input is out of bounds. Please select a valid grid position within the grid size.");
        }
    }
}