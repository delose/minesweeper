package com.delose.minesweeper.helper;

import com.delose.minesweeper.controller.GameController;
import com.delose.minesweeper.view.DisplayManager;
import com.delose.minesweeper.controller.PlayerInputHandler;

/**
 * A container class to hold the initialized game components.
 */
public class GameComponents {

    private final GameController gameController;
    private final DisplayManager displayManager;
    private final PlayerInputHandler inputHandler;

    public GameComponents(GameController gameController, DisplayManager displayManager, PlayerInputHandler inputHandler) {
        this.gameController = gameController;
        this.displayManager = displayManager;
        this.inputHandler = inputHandler;
    }

    public GameController getGameController() {
        return gameController;
    }

    public DisplayManager getDisplayManager() {
        return displayManager;
    }

    public PlayerInputHandler getInputHandler() {
        return inputHandler;
    }
}