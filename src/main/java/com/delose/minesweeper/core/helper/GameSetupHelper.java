package com.delose.minesweeper.core.helper;

import com.delose.minesweeper.controller.GameController;
import com.delose.minesweeper.view.DisplayManager;
import com.delose.minesweeper.view.impl.DisplayManagerImpl;
import com.delose.minesweeper.controller.PlayerInputHandler;
import com.delose.minesweeper.controller.impl.GameControllerImpl;
import com.delose.minesweeper.controller.impl.PlayerInputHandlerImpl;
import com.delose.minesweeper.core.exception.GameInputException;
import com.delose.minesweeper.core.util.config.GameConfig;
import com.delose.minesweeper.core.util.logging.LoggerUtil;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
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
    public GameComponents initializeGameComponents() throws GameInputException, NoSuchElementException {
        Scanner scanner = new Scanner(System.in);
    
        int gridSize;
        int numberOfMines;
        
        // Loop until valid grid size and number of mines are provided
        while (true) {
            try {
                gridSize = promptGridSize(scanner);
                numberOfMines = promptNumberOfMines(scanner, gridSize);
                break;  // Break the loop when valid inputs are provided
            } catch (NoSuchElementException e) {
                LoggerUtil.error(e.getMessage());
                scanner.nextLine(); // Clear the invalid input
                throw new GameInputException("Invalid input. Please enter valid integers.");
            } catch (GameInputException e) {
                LoggerUtil.error(e.getMessage());
                scanner.nextLine(); // Clear the invalid input
            }
        }
    
        GameController gameController = new GameControllerImpl(gridSize, numberOfMines);
        DisplayManager displayManager = new DisplayManagerImpl(gameController);
        PlayerInputHandler inputHandler = new PlayerInputHandlerImpl(gridSize);
    
        return new GameComponents(gameController, displayManager, inputHandler);
    }

    private int promptGridSize(Scanner scanner) {
        GameConfig config = GameConfig.getInstance();
        
        LoggerUtil.info("Welcome to Minesweeper!");
        System.out.print("Enter the size of the grid (e.g., 4 for a 4x4 grid, maximum is " + config.getMaxGridSize() + "): ");
        
        if (!scanner.hasNextInt()) {
            throw new InputMismatchException("Invalid input. Please enter an integer value for the grid size.");
        }

        int gridSize = scanner.nextInt();

        if (gridSize < config.getMinGridSize() || gridSize > config.getMaxGridSize()) {
            throw new GameInputException("Invalid grid size. Please enter a size between " + config.getMinGridSize() + " and " + config.getMaxGridSize() + ".");
        }

        return gridSize;
    }

    private int promptNumberOfMines(Scanner scanner, int gridSize) {
        GameConfig config = GameConfig.getInstance();
        int maxMines = (int) (gridSize * gridSize * config.getMaxMineRatio());
        
        System.out.print("Enter the number of mines to place on the grid (maximum is " + maxMines + "): ");
        
        if (!scanner.hasNextInt()) {
            throw new InputMismatchException("Invalid input. Please enter an integer value for the number of mines.");
        }
    
        int numberOfMines = scanner.nextInt();
    
        if (numberOfMines < config.getMinGridSize() || numberOfMines > maxMines) {
            throw new GameInputException("Invalid number of mines. Please enter a number between " + config.getMinGridSize() + " and " + maxMines + ".");
        }
    
        return numberOfMines;
    }
}