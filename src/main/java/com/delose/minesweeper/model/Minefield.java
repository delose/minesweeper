package com.delose.minesweeper.model;

import java.util.Random;

import com.delose.minesweeper.core.exception.GameInputException;
import com.delose.minesweeper.core.util.config.GameConfig;
import com.delose.minesweeper.core.util.config.MessageProvider;

/**
 * Represents a minefield for the Minesweeper game.
 * Handles the grid size, mine placement, and revealing squares.
 */
public class Minefield {

    private final int size;
    private final int numberOfMines;
    private final boolean[][] mineGrid;
    /**
     * Constructs a Minefield with the specified size and number of mines.
     * 
     * @param size the size of the minefield (size x size grid)
     * @param numberOfMines the number of mines to be placed in the minefield
     * @throws GameInputException if the number of mines exceeds the maximum allowed
     */
    public Minefield(int size, int numberOfMines) {
        GameConfig config = GameConfig.getInstance();
        if (numberOfMines > size * size * config.getMaxMineRatio()) {
            throw new GameInputException(MessageProvider.getMessage("minefield.tooManyMines"));
        }
        this.size = size;
        this.numberOfMines = numberOfMines;
        this.mineGrid = new boolean[size][size];
    }

    public int getSize() {
        return size;
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }

    /**
     * Places a mine at the specified location.
     * 
     * @param row the row index
     * @param col the column index
     */
    public void placeMine(int row, int col) {
        validateCoordinates(row, col);
        if (!mineGrid[row][col]) {
            mineGrid[row][col] = true;
        }
    }

    /**
     * Randomly places mines in the grid.
     */
    public void placeMines() {
        Random random = new Random();
        int minesPlaced = 0;
        while (minesPlaced < numberOfMines) {
            int row = random.nextInt(size);
            int col = random.nextInt(size);
            if (!mineGrid[row][col]) {
                mineGrid[row][col] = true;
                minesPlaced++;
            }
        }
    }

    /**
     * Checks if there is a mine at the specified location.
     * 
     * @param row the row index
     * @param col the column index
     * @return true if there is a mine at the specified location, false otherwise
     */
    public boolean isMineAt(int row, int col) {
        validateCoordinates(row, col);
        return mineGrid[row][col];
    }

    /**
     * Calculates the number of adjacent mines for a given square.
     * 
     * @param row the row index
     * @param col the column index
     * @return the number of adjacent mines
     */
    public int calculateAdjacentMines(int row, int col) {
        validateCoordinates(row, col);
        int mineCount = 0;
        for (int i = Math.max(0, row - 1); i <= Math.min(size - 1, row + 1); i++) {
            for (int j = Math.max(0, col - 1); j <= Math.min(size - 1, col + 1); j++) {
                if (mineGrid[i][j]) {
                    mineCount++;
                }
            }
        }
        return mineCount;
    }

    /**
     * Validates the row and column coordinates to ensure they are within bounds.
     * 
     * @param row the row index
     * @param col the column index
     */
    private void validateCoordinates(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new GameInputException(MessageProvider.getMessage("minefield.coordinatesOutOfBounds"));
        }
    }

    /**
     * Counts the number of mines placed in the minefield.
     * 
     * @return the number of mines
     */
    public int countMines() {
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (mineGrid[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }
}