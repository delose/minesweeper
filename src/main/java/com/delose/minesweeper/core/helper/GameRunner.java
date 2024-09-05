package com.delose.minesweeper.core.helper;

import com.delose.minesweeper.controller.GameController;
import com.delose.minesweeper.model.GameStatus;
import com.delose.minesweeper.core.exception.GameInputException;
import com.delose.minesweeper.core.util.config.MessageProvider;
import com.delose.minesweeper.core.util.logging.LoggerUtil;
import com.delose.minesweeper.view.DisplayManager;
import com.delose.minesweeper.controller.PlayerInputHandler;

import java.util.Scanner;

/**
 * Helper class to manage the game loop and user interactions.
 */
public class GameRunner {

    private final GameComponents components;
    private final Scanner scanner;

    /**
     * Constructs a GameRunner with the specified game components.
     *
     * @param components the container object holding the game components
     */
    public GameRunner(GameComponents components) {
        this.components = components;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Starts and runs the main game loop.
     */
    public void runGameLoop() {
        GameController gameController = components.getGameController();
        DisplayManager displayManager = components.getDisplayManager();
        PlayerInputHandler inputHandler = components.getInputHandler();

        while (gameController.getGameStatus() == GameStatus.IN_PROGRESS) {
            LoggerUtil.info(displayManager.renderMinefield());
            System.out.print(MessageProvider.getMessage("game.selectedSquare"));
            String input = scanner.nextLine();

            try {
                String position = inputHandler.parseInput(input);
                gameController.revealSquare(position);
            } catch (GameInputException e) {
                LoggerUtil.error(e.getMessage());
                continue;
            }

            if (gameController.getGameStatus() != GameStatus.IN_PROGRESS) {
                gameController.revealAllCells();
                LoggerUtil.info(displayManager.renderMinefield());
                LoggerUtil.info(displayManager.displayEndGameMessage());
                break;
            }
        }
    }

    /**
     * Prompts the user to replay the game or exit.
     *
     * @return true if the user wants to replay, false otherwise
     */
    public boolean promptReplay() {
        System.out.print(MessageProvider.getMessage("game.playAgain"));
        String replayInput = scanner.nextLine();
        return !replayInput.equalsIgnoreCase(MessageProvider.getMessage("game.exit"));
    }
}