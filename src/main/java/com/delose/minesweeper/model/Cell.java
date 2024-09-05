package com.delose.minesweeper.model;

import com.delose.minesweeper.core.exception.GameInputException;
import com.delose.minesweeper.core.util.config.MessageProvider;

/**
 * Represents a single cell in the minefield.
 * A cell may contain a mine, be revealed, and have a count of adjacent mines.
 */
public class Cell {

    private boolean hasMine;
    private boolean isRevealed;
    private int adjacentMinesCount;

    /**
     * Constructs a new Cell.
     * Initially, the cell does not contain a mine, is not revealed, and has 0 adjacent mines.
     */
    public Cell() {
        this.hasMine = false;
        this.isRevealed = false;
        this.adjacentMinesCount = 0;
    }

    /**
     * Checks if the cell contains a mine.
     * 
     * @return true if the cell contains a mine, false otherwise
     */
    public boolean hasMine() {
        return hasMine;
    }

    /**
     * Sets whether the cell contains a mine.
     * 
     * @param hasMine true if the cell should contain a mine, false otherwise
     */
    public void setMine(boolean hasMine) {
        this.hasMine = hasMine;
    }

    /**
     * Checks if the cell has been revealed.
     * 
     * @return true if the cell is revealed, false otherwise
     */
    public boolean isRevealed() {
        return isRevealed;
    }

    /**
     * Reveals the cell.
     * Once revealed, the cell cannot be hidden again.
     */
    public void reveal() {
        this.isRevealed = true;
    }

    /**
     * Uneveals the cell.
     * Once unrevealed, the cell will be hidden again.
     */
    public void unreveal() {
        this.isRevealed = false;
    }

    /**
     * Gets the count of adjacent mines.
     * 
     * @return the number of adjacent mines
     */
    public int getAdjacentMinesCount() {
        return adjacentMinesCount;
    }

    /**
     * Sets the count of adjacent mines.
     * 
     * @param adjacentMinesCount the number of adjacent mines
     * @throws GameInputException if adjacentMinesCount is negative
     */
    public void setAdjacentMinesCount(int adjacentMinesCount) {
        if (adjacentMinesCount < 0) {
            throw new GameInputException(MessageProvider.getMessage("cell.adjacentMinesCannotBeNegative"));
        }
        this.adjacentMinesCount = adjacentMinesCount;
    }
}