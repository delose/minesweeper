package com.delose.minesweeper.model;

import java.util.Random;

import com.delose.minesweeper.core.exception.GameInputException;

/**
 * Represents a minefield for the Minesweeper game.
 * Handles the grid size, mine placement, and revealing squares.
 */
public class Minefield {

    private static final double MAX_MINE_RATIO = 0.35;
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
        if (numberOfMines > size * size * MAX_MINE_RATIO) {
            throw new GameInputException("Too many mines for the given grid size.");
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
     * Reveals the square at the specified location.
     * 
     * @param row the row index
     * @param col the column index
     */
    public void revealSquare(int row, int col) {
        validateCoordinates(row, col);
        // Logic to reveal the square
        // If square is empty, calculate adjacent mines
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
            throw new GameInputException("Coordinates are out of bounds.");
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