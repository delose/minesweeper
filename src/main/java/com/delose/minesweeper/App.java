package com.delose.minesweeper;

import com.delose.minesweeper.controller.GameController;
import com.delose.minesweeper.view.DisplayManager;
import com.delose.minesweeper.controller.PlayerInputHandler;
import com.delose.minesweeper.helper.GameComponents;
import com.delose.minesweeper.helper.GameSetupHelper;
import com.delose.minesweeper.model.GameStatus;
import com.delose.minesweeper.util.logging.LoggerUtil;

import java.util.Scanner;

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

        runGameLoop(components);

        if (promptReplay(new Scanner(System.in))) {
            main(args); // Restart the game
        } else {
            LoggerUtil.info("Thank you for playing Minesweeper!");
        }
    }

    /**
     * Runs the main game loop, handling the game state and user interactions.
     *
     * @param components the container object holding the game components
     */
    private static void runGameLoop(GameComponents components) {
        Scanner scanner = new Scanner(System.in);
        GameController gameController = components.getGameController();
        DisplayManager displayManager = components.getDisplayManager();
        PlayerInputHandler inputHandler = components.getInputHandler();

        while (gameController.getGameStatus() == GameStatus.IN_PROGRESS) {
            LoggerUtil.info(displayManager.renderMinefield());
            System.out.print("Select a square to reveal (e.g., A1): ");
            String input = scanner.nextLine();

            try {
                String position = inputHandler.parseInput(input);
                gameController.revealSquare(position);
            } catch (IllegalArgumentException e) {
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
     * @param scanner the scanner for user input
     * @return true if the user wants to replay, false otherwise
     */
    private static boolean promptReplay(Scanner scanner) {
        System.out.print("Press any key to play again, or type 'exit' to quit: ");
        String replayInput = scanner.nextLine();
        return !replayInput.equalsIgnoreCase("exit");
    }
}