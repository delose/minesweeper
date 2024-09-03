package com.delose.minesweeper.helper;

import com.delose.minesweeper.controller.GameController;
import com.delose.minesweeper.view.DisplayManager;
import com.delose.minesweeper.controller.PlayerInputHandler;
import com.delose.minesweeper.util.logging.LoggerUtil;

import java.util.Scanner;

/**
 * Helper class to handle the setup and initialization of Minesweeper game components.
 */
public class GameSetupHelper {

    /**
     * Initializes game components by prompting the user for grid size and number of mines.
     *
     * @return a GameComponents object containing the initialized game components
     */
    public GameComponents initializeGameComponents() {
        Scanner scanner = new Scanner(System.in);

        int gridSize = promptGridSize(scanner);
        int numberOfMines = promptNumberOfMines(scanner, gridSize);

        GameController gameController = new GameController(gridSize, numberOfMines);
        DisplayManager displayManager = new DisplayManager(gameController);
        PlayerInputHandler inputHandler = new PlayerInputHandler(gridSize);

        return new GameComponents(gameController, displayManager, inputHandler);
    }

    private int promptGridSize(Scanner scanner) {
        LoggerUtil.info("Welcome to Minesweeper!");
        System.out.print("Enter the size of the grid (e.g., 4 for a 4x4 grid): ");
        return scanner.nextInt();
    }

    private int promptNumberOfMines(Scanner scanner, int gridSize) {
        int maxMines = (int) (gridSize * gridSize * 0.35);
        System.out.print("Enter the number of mines to place on the grid (maximum is " + maxMines + "): ");
        return scanner.nextInt();
    }
}