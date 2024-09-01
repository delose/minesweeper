package com.delose.minesweeper.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerInputHandlerTest {

    private PlayerInputHandler inputHandler;

    @BeforeEach
    void setUp() {
        inputHandler = new PlayerInputHandler(4); // Assume a 4x4 grid
    }

    @Test
    void testInvalidInputFormat() {
        String input = "Z9"; // Out of bounds, so it's valid format but invalid position
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            inputHandler.parseInput(input);
        });
        assertEquals("Input is out of bounds. Please select a valid grid position within the grid size.", exception.getMessage());
    }

    @Test
    void testInputFormatTooShort() {
        String input = "A"; // Too short
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            inputHandler.parseInput(input);
        });
        assertEquals("Invalid input format. Please use a valid grid position (e.g., A1).", exception.getMessage());
    }

    @Test
    void testInputFormatTooLong() {
        String input = "A123"; // Too long
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            inputHandler.parseInput(input);
        });
        assertEquals("Invalid input format. Please use a valid grid position (e.g., A1).", exception.getMessage());
    }

    @Test
    void testValidInput() {
        String input = "A1"; // Valid input
        assertDoesNotThrow(() -> {
            inputHandler.parseInput(input);
        });
    }
}