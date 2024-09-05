package com.delose.minesweeper.controller.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.delose.minesweeper.controller.GameController;
import com.delose.minesweeper.core.exception.GameInputException;
import com.delose.minesweeper.core.util.config.MessageProvider;
import com.delose.minesweeper.model.Cell;
import com.delose.minesweeper.model.GameStatus;
import com.delose.minesweeper.model.Minefield;

/**
 * Manages the overall flow of the Minesweeper game.
 * Handles the game initialization, user inputs, and game state transitions.
 */
public class GameControllerImpl implements GameController {

    private Minefield minefield;
    private GameStatus gameStatus;
    private Map<String, Cell> cellMap;

    public GameControllerImpl(int gridSize, int numberOfMines) {
        this.minefield = new Minefield(gridSize, numberOfMines);
        this.gameStatus = GameStatus.IN_PROGRESS;
        this.cellMap = new HashMap<>();
        initializeCells(gridSize);
    }

    public void placeMinesRandmly() {
        minefield.placeMines();
    }

    private void initializeCells(int gridSize) {
        for (char row = 'A'; row < 'A' + gridSize; row++) {
            for (int col = 1; col <= gridSize; col++) {
                String position = "" + row + col;
                cellMap.put(position, new Cell());
            }
        }
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public int getMinefieldSize() {
        return minefield.getSize();
    }

    public int getAdjacentMinesCount(String position) {
        validatePosition(position);
        int[] indices = convertPositionToIndices(position);
        return minefield.calculateAdjacentMines(indices[0], indices[1]);
    }

    public void revealSquare(String position) {
        validatePosition(position);
        int[] indices = convertPositionToIndices(position);
        Cell cell = cellMap.get(position);
    
        if (cell.isRevealed()) {
            return;  // If the cell is already revealed, do nothing
        }
    
        if (minefield.isMineAt(indices[0], indices[1])) {
            cell.reveal();
            gameStatus = GameStatus.LOST;
            return;
        }
    
        cell.reveal();  // Reveal the current cell
        revealAdjacentSquares(indices[0], indices[1]);
    
        if (checkWinCondition()) {
            gameStatus = GameStatus.WON;
        } else {
            gameStatus = GameStatus.IN_PROGRESS;
        }
    }

    private void revealAdjacentSquares(int row, int col) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{row, col});
    
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int currentRow = current[0];
            int currentCol = current[1];
            String position = convertIndicesToPosition(currentRow, currentCol);
            Cell cell = cellMap.get(position);

            cell.reveal();
            int adjacentMines = minefield.calculateAdjacentMines(currentRow, currentCol);
    
            // If there are no adjacent mines, continue revealing neighboring cells
            if (adjacentMines == 0) {
                for (int i = Math.max(0, currentRow - 1); i <= Math.min(minefield.getSize() - 1, currentRow + 1); i++) {
                    for (int j = Math.max(0, currentCol - 1); j <= Math.min(minefield.getSize() - 1, currentCol + 1); j++) {
                        // Avoid adding the current cell back into the queue
                        if (i != currentRow || j != currentCol) {
                            String adjacentPosition = convertIndicesToPosition(i, j);
                            Cell adjacentCell = cellMap.get(adjacentPosition);
    
                            // Only add cells to the queue that haven't been revealed yet
                            if (!adjacentCell.isRevealed()) {
                                queue.add(new int[]{i, j});
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean isMineAt(String position) {
        int[] indices = convertPositionToIndices(position);
        return minefield.isMineAt(indices[0], indices[1]);
    }

    private void validatePosition(String position) {
        if (!cellMap.containsKey(position)) {
            throw new GameInputException(MessageProvider.getMessage("game.invalidInputSquare"));
        }
    }

    private int[] convertPositionToIndices(String position) {
        int row = position.charAt(0) - 'A';
        int col = Integer.parseInt(position.substring(1)) - 1;
        return new int[]{row, col};
    }

    private String convertIndicesToPosition(int row, int col) {
        return (char) ('A' + row) + Integer.toString(col + 1);
    }
    
    private boolean checkWinCondition() {
        int unrevealedCells = 0;
    
        // Iterate over the mineGrid to count unrevealed cells
        for (int i = 0; i < minefield.getSize(); i++) {
            for (int j = 0; j < minefield.getSize(); j++) {
                if (!minefield.isMineAt(i, j) && !cellMap.get(convertIndicesToPosition(i, j)).isRevealed()) {
                    // If there's any non-mine cell that isn't revealed, the game isn't won yet
                    return false;
                }
                if (!cellMap.get(convertIndicesToPosition(i, j)).isRevealed()) {
                    unrevealedCells++;
                }
            }
        }
    
        // If the number of unrevealed cells is equal to the number of mines, the game is won
        return unrevealedCells == minefield.getNumberOfMines();
    }

    public boolean isSquareRevealed(String position) {
        validatePosition(position);
        return cellMap.get(position).isRevealed();
    }

    public void placeMineAt(String position) {
        validatePosition(position);
        int[] indices = convertPositionToIndices(position);
        minefield.placeMine(indices[0], indices[1]);
    }
    public void revealAllCells() {
        for (Cell cell : cellMap.values()) {
            cell.reveal();  // Reveal all cells, whether they contain a mine or not
        }
    }

}
