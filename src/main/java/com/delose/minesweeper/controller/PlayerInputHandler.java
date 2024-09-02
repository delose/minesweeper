package com.delose.minesweeper.controller;

/**
 * Handles and validates player input for the Minesweeper game.
 * Converts user input into a format that the GameController can process.
 */
public class PlayerInputHandler {

    private final int gridSize;

    /**
     * Constructs a PlayerInputHandler with the specified grid size.
     * 
     * @param gridSize the size of the grid (gridSize x gridSize)
     */
    public PlayerInputHandler(int gridSize) {
        this.gridSize = gridSize;
    }

    /**
     * Parses and validates the player input.
     * 
     * @param input the player input (e.g., "A1")
     * @return the parsed input in a standardized format (e.g., "A1")
     * @throws IllegalArgumentException if the input is invalid or out of bounds
     */
    public String parseInput(String input) {
        if (input == null || input.length() < 2 || input.length() > 3) {
            throw new IllegalArgumentException("Invalid input format. Please use a valid grid position (e.g., A1).");
        }

        // Normalize input to uppercase
        input = input.toUpperCase(java.util.Locale.ENGLISH);

        // Extract row and column
        char row = input.charAt(0);
        int col;
        try {
            col = Integer.parseInt(input.substring(1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input format. Please use a valid grid position (e.g., A1).", e);
        }

        // Validate row and column bounds
        if (row < 'A' || row >= 'A' + gridSize || col < 1 || col > gridSize) {
            throw new IllegalArgumentException("Input is out of bounds. Please select a valid grid position within the grid size.");
        }

        return input;
    }}