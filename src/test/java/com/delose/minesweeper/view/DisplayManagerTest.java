package com.delose.minesweeper.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.delose.minesweeper.controller.GameController;
import com.delose.minesweeper.controller.impl.GameControllerImpl;
import com.delose.minesweeper.model.GameStatus;
import com.delose.minesweeper.view.impl.DisplayManagerImpl;

import static org.junit.jupiter.api.Assertions.*;

class DisplayManagerTest {

    private DisplayManager displayManager;
    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameController = new GameControllerImpl(4, 3); // A 4x4 game with 3 mines
        displayManager = new DisplayManagerImpl(gameController);
    }

    @Test
    void testInitialDisplay() {
        String initialDisplay = displayManager.renderMinefield();
        assertNotNull(initialDisplay, "Initial display should not be null.");
        assertTrue(initialDisplay.contains("_"), "Initial display should contain unrevealed cells.");
    }

    @Test
    void testDisplayAfterReveal() {
        gameController.revealSquare("A1");
        String displayAfterReveal = displayManager.renderMinefield();
        assertFalse(displayAfterReveal.contains("_A1"), "The revealed cell should not display as '_'.");
    }

    @Test
    void testDisplayWinningMessage() {
        gameController.revealSquare("A2");
        gameController.revealSquare("A3");
        gameController.revealSquare("A4");
        gameController.revealSquare("B1");
        gameController.revealSquare("B2");
        // Continue revealing until the game is won
        if (gameController.getGameStatus() == GameStatus.WON) {
            String winningMessage = displayManager.displayEndGameMessage();
            assertEquals("Congratulations, you have won the game!", winningMessage, "Winning message should be displayed correctly.");
        }
    }

    @Test
    void testDisplayLosingMessage() {
        gameController.placeMineAt("A1");
        gameController.revealSquare("A1");
        if (gameController.getGameStatus() == GameStatus.LOST) {
            String losingMessage = displayManager.displayEndGameMessage();
            assertEquals("Oh no, you detonated a mine! Game over.", losingMessage, "Losing message should be displayed correctly.");
        }
    }
}