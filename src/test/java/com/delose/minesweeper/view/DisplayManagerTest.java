package com.delose.minesweeper.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.delose.minesweeper.controller.GameController;
import com.delose.minesweeper.model.GameStatus;
import com.delose.minesweeper.view.impl.DisplayManagerImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DisplayManagerTest {

    private DisplayManager displayManager;
    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameController = mock(GameController.class);
        displayManager = new DisplayManagerImpl(gameController);
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

    @Nested
    @DisplayName("displayEndGameMessage method")
    class DisplayEndGameMessageTests {

        @Test
        @DisplayName("should return victory message when game status is WON")
        void testDisplayEndGameMessage_Won() {
            // Given
            when(gameController.getGameStatus()).thenReturn(GameStatus.WON);

            // When
            String message = displayManager.displayEndGameMessage();

            // Then
            assertEquals("Congratulations, you have won the game!", message);
        }

        @Test
        @DisplayName("should return game over message when game status is LOST")
        void testDisplayEndGameMessage_Lost() {
            // Given
            when(gameController.getGameStatus()).thenReturn(GameStatus.LOST);

            // When
            String message = displayManager.displayEndGameMessage();

            // Then
            assertEquals("Oh no, you detonated a mine! Game over.", message);
        }

        @Test
        @DisplayName("should return an empty string when game status is neither WON nor LOST")
        void testDisplayEndGameMessage_InProgress() {
            // Given
            when(gameController.getGameStatus()).thenReturn(GameStatus.IN_PROGRESS);

            // When
            String message = displayManager.displayEndGameMessage();

            // Then
            assertEquals("", message);
        }
    }
}