package com.delose.minesweeper.controller.impl;

import com.delose.minesweeper.controller.PlayerInputHandler;
import com.delose.minesweeper.core.exception.GameInputException;
import com.delose.minesweeper.core.util.config.MessageProvider;
import com.delose.minesweeper.core.util.validator.GameValidator;

public class PlayerInputHandlerImpl implements PlayerInputHandler {

    private final int gridSize;
    private final GameValidator gameValidator;

    /**
     * Constructs a PlayerInputHandler with the specified grid size.
     * 
     * @param gridSize the size of the grid (gridSize x gridSize)
     */
    public PlayerInputHandlerImpl(int gridSize, GameValidator gameValidator) {
        this.gridSize = gridSize;
        this.gameValidator = gameValidator;
    }

    /**
     * Parses and validates the player input.
     * 
     * @param input the player input (e.g., "A1")
     * @return the parsed input in a standardized format (e.g., "A1")
     * @throws GameInputException if the input is invalid or out of bounds
     */
    public String parseInput(String input) {
        this.gameValidator.validateInputSize(input);

        // Normalize input to uppercase
        input = input.toUpperCase();

        // Extract row and column
        char row = input.charAt(0);
        int col;
        try {
            col = Integer.parseInt(input.substring(1));
        } catch (NumberFormatException e) {
            throw new GameInputException(MessageProvider.getMessage("game.invalidInputGridPosition"));
        }

        // Validate row and column bounds
        if (row < 'A' || row >= 'A' + gridSize || col < 1 || col > gridSize) {
            throw new GameInputException(MessageProvider.getMessage("game.inputOutOfBounds"));
        }

        return input;
    }
}
