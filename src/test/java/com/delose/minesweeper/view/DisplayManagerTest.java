package com.delose.minesweeper.view;

import com.delose.minesweeper.controller.GameController;
import com.delose.minesweeper.model.GameStatus;
import com.delose.minesweeper.view.impl.DisplayManagerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DisplayManagerTest {

    private DisplayManager displayManager;
    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameController = mock(GameController.class);
        displayManager = new DisplayManagerImpl(gameController);
    }

    @Nested
    @DisplayName("renderMinefield method")
    class RenderMinefieldTests {

        @Test
        @DisplayName("should display the initial state of the minefield with all cells unrevealed")
        void testRenderMinefield_Initial() {
            // Given
            when(gameController.getMinefieldSize()).thenReturn(4);
            when(gameController.isSquareRevealed(anyString())).thenReturn(false);

            // When
            String minefieldDisplay = displayManager.renderMinefield();

            // Then
            assertTrue(minefieldDisplay.contains("_ _ _ _"));
            assertTrue(minefieldDisplay.contains("A"));
            assertTrue(minefieldDisplay.contains("B"));
            assertTrue(minefieldDisplay.contains("C"));
            assertTrue(minefieldDisplay.contains("D"));
        }

        @Test
        @DisplayName("should display revealed cells with adjacent mine counts")
        void testRenderMinefield_RevealedCells() {
            // Given
            when(gameController.getMinefieldSize()).thenReturn(4);
            when(gameController.isSquareRevealed("A1")).thenReturn(true);
            when(gameController.isSquareRevealed("B2")).thenReturn(true);
            when(gameController.getAdjacentMinesCount("A1")).thenReturn(1);
            when(gameController.getAdjacentMinesCount("B2")).thenReturn(2);

            // When
            String minefieldDisplay = displayManager.renderMinefield();

            // Then
            assertTrue(minefieldDisplay.contains("1"));  // Display the adjacent mine count
            assertTrue(minefieldDisplay.contains("2"));  // Display the adjacent mine count
            assertFalse(minefieldDisplay.contains("_A1"));
            assertFalse(minefieldDisplay.contains("_B2"));
        }

        @Test
        @DisplayName("should display a mine when a mine is revealed")
        void testRenderMinefield_RevealMine() {
            // Given
            when(gameController.getMinefieldSize()).thenReturn(4);
            when(gameController.isSquareRevealed("A1")).thenReturn(true);
            when(gameController.isMineAt("A1")).thenReturn(true);

            // When
            String minefieldDisplay = displayManager.renderMinefield();

            // Then
            assertTrue(minefieldDisplay.contains("*"));  // Display the mine
            assertFalse(minefieldDisplay.contains("_A1"));
        }

        @Test
        @DisplayName("should display unrevealed cells as underscores")
        void testRenderMinefield_UnrevealedCells() {
            // Given
            when(gameController.getMinefieldSize()).thenReturn(4);
            when(gameController.isSquareRevealed("A1")).thenReturn(false);

            // When
            String minefieldDisplay = displayManager.renderMinefield();

            // Then
            assertTrue(minefieldDisplay.contains("_ _ _ _"));
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