package com.delose.minesweeper;

import com.delose.minesweeper.core.helper.GameComponents;
import com.delose.minesweeper.core.helper.GameRunner;
import com.delose.minesweeper.core.helper.GameSetupHelper;
import com.delose.minesweeper.core.util.config.MessageProvider;
import com.delose.minesweeper.core.util.logging.LoggerUtil;

/**
 * The main class for running the Minesweeper game.
 * Initializes game components, handles user input, and manages the game loop.
 */
public class App {

    /**
     * The entry point of the Minesweeper game.
     * Initializes the game, manages the main game loop, and handles user interactions.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        GameSetupHelper setupHelper = new GameSetupHelper();
        GameComponents components = setupHelper.initializeGameComponents();

        GameRunner gameRunner = new GameRunner(components);
        gameRunner.runGameLoop();

        if (gameRunner.promptReplay()) {
            main(args); // Restart the game
        } else {
            LoggerUtil.info(MessageProvider.getMessage("game.thankYouMessage"));
        }
    }
}