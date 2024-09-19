package com.delose.minesweeper.core.helper;

import com.delose.minesweeper.controller.GameController;
import com.delose.minesweeper.view.DisplayManager;
import com.delose.minesweeper.view.impl.DisplayManagerImpl;
import com.delose.minesweeper.controller.PlayerInputHandler;
import com.delose.minesweeper.controller.impl.GameControllerImpl;
import com.delose.minesweeper.controller.impl.PlayerInputHandlerImpl;
import com.delose.minesweeper.core.exception.GameInputException;
import com.delose.minesweeper.core.util.config.GameConfig;
import com.delose.minesweeper.core.util.config.MessageProvider;
import com.delose.minesweeper.core.util.logging.LoggerUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
            } catch (NoSuchElementException | GameInputException e) {
                LoggerUtil.error(e.getMessage());
                scanner.nextLine(); // Clear the invalid input
            }
        }
    
        GameController gameController = new GameControllerImpl(gridSize, numberOfMines);
        gameController.placeMinesRandmly();
        DisplayManager displayManager = new DisplayManagerImpl(gameController);
        PlayerInputHandler inputHandler = new PlayerInputHandlerImpl(gridSize);
    
        return new GameComponents(gameController, displayManager, inputHandler);
    }

    private int promptGridSize(Scanner scanner) {
        GameConfig config = GameConfig.getInstance();
        
        LoggerUtil.info(MessageProvider.getMessage("game.welcomeMessage"));
        System.out.print(MessageProvider.getMessage("game.enterSizeGrid") + config.getMaxGridSize() + "): ");
        
        if (!scanner.hasNextInt()) {
            throw new GameInputException(MessageProvider.getMessage("game.invalidIntegerGridSize"));
        }

        int gridSize = scanner.nextInt();

        if (gridSize < config.getMinGridSize() || gridSize > config.getMaxGridSize()) {
            throw new GameInputException(MessageProvider.getMessage("game.invalidGridSizeBetween") + config.getMinGridSize() + " and " + config.getMaxGridSize() + ".");
        }

        return gridSize;
    }

    private int promptNumberOfMines(Scanner scanner, int gridSize) {
        GameConfig config = GameConfig.getInstance();
        int maxMines = (int) (gridSize * gridSize * config.getMaxMineRatio());
    
        System.out.print(MessageProvider.getMessage("game.enterNumberOfMines") + maxMines + "): ");
        
        if (!scanner.hasNextBigDecimal()) {
            throw new GameInputException(MessageProvider.getMessage("game.invalidInputNumberOfMines"));
        }
    
        // Read BigDecimal input from the scanner
        BigDecimal input = scanner.nextBigDecimal();
        
        // Round input: if 1.4 round to 1, 1.5 and above round to 2
        int numberOfMines = input.setScale(0, RoundingMode.HALF_UP).intValue();
    
        // Validate number of mines
        if (numberOfMines < config.getMinGridSize() || numberOfMines > maxMines) {
            throw new GameInputException(MessageProvider.getMessage("game.invalidNumberOfMines") + config.getMinGridSize() + " and " + maxMines + ".");
        }
    
        return numberOfMines;
    }
}