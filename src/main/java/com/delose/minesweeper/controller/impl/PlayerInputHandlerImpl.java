package com.delose.minesweeper.controller.impl;

import com.delose.minesweeper.controller.PlayerInputHandler;
import com.delose.minesweeper.core.exception.GameInputException;

public class PlayerInputHandlerImpl implements PlayerInputHandler {

    private final int gridSize;

    /**
     * Constructs a PlayerInputHandler with the specified grid size.
     * 
     * @param gridSize the size of the grid (gridSize x gridSize)
     */
    public PlayerInputHandlerImpl(int gridSize) {
        this.gridSize = gridSize;
    }

    /**
     * Parses and validates the player input.
     * 
     * @param input the player input (e.g., "A1")
     * @return the parsed input in a standardized format (e.g., "A1")
     * @throws GameInputException if the input is invalid or out of bounds
     */
    public String parseInput(String input) {
        if (input == null || input.length() < 2 || input.length() > 3) {
            throw new GameInputException("Invalid input format. Please use a valid grid position (e.g., A1).");
        }

        // Normalize input to uppercase
        input = input.toUpperCase();

        // Extract row and column
        char row = input.charAt(0);
        int col;
        try {
            col = Integer.parseInt(input.substring(1));
        } catch (NumberFormatException e) {
            throw new GameInputException("Invalid input format. Please use a valid grid position (e.g., A1).");
        }

        // Validate row and column bounds
        if (row < 'A' || row >= 'A' + gridSize || col < 1 || col > gridSize) {
            throw new GameInputException("Input is out of bounds. Please select a valid grid position within the grid size.");
        }

        return input;
    }
}
