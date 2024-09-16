package com.delose.minesweeper.view.impl;

import com.delose.minesweeper.controller.GameController;
import com.delose.minesweeper.core.util.config.MessageProvider;
import com.delose.minesweeper.model.GameStatus;
import com.delose.minesweeper.view.DisplayManager;

/**
 * Manages the display of the Minesweeper game.
 * Responsible for rendering the minefield and displaying game status messages.
 */
public class DisplayManagerImpl implements DisplayManager {

    private final GameController gameController;

    /**
     * Constructs a DisplayManager with a reference to the GameController.
     * 
     * @param gameController the controller managing the game's state
     */
    public DisplayManagerImpl(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * Renders the current state of the minefield to be displayed to the player.
     * 
     * @return a String representing the current minefield display
     */
    public String renderMinefield() {
        StringBuilder display = new StringBuilder();
        int gridSize = gameController.getMinefieldSize();
    
        // Build the column headers
        display.append(MessageProvider.getMessage("display.header"));
        for (int col = 1; col <= gridSize; col++) {
            display.append(String.format("%-3d", col));
        }
        display.append(MessageProvider.getMessage("display.newline"));
    
        // Build each row of the minefield
        for (char row = 'A'; row < 'A' + gridSize; row++) {
            display.append(row).append(MessageProvider.getMessage("display.col"));
            for (int col = 1; col <= gridSize; col++) {
                String position = "" + row + col;
                if (gameController.isSquareRevealed(position)) {
                    // Check if the revealed square contains a mine
                    if (gameController.isMineAt(position)) {
                        display.append(String.format("%-3s", MessageProvider.getMessage("display.mine")));  // Display * for mines
                    } else {
                        int adjacentMines = gameController.getAdjacentMinesCount(position);
                        display.append(String.format("%-3d", adjacentMines));
                    }
                } else {
                    display.append(String.format("%-3s", MessageProvider.getMessage("display.unrevealed")));
                }
            }
            display.append(MessageProvider.getMessage("display.newline"));
        }
    
        return display.toString();
    }

    /**
     * Displays the end game message based on the game status.
     * 
     * @return a String containing the appropriate end game message
     */
    public String displayEndGameMessage() {
        if (gameController.getGameStatus() == GameStatus.WON) {
            return MessageProvider.getMessage("game.congratulations");
        } else if (gameController.getGameStatus() == GameStatus.LOST) {
            return MessageProvider.getMessage("game.gameOver");
        }
        return "";
    }
}
