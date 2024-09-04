package com.delose.minesweeper.controller;

import com.delose.minesweeper.controller.impl.GameControllerImpl;
import com.delose.minesweeper.core.exception.GameInputException;
import com.delose.minesweeper.model.GameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameController = new GameControllerImpl(4, 2); // A 4x4 grid with 2 mines
    }

    @Nested
    @DisplayName("Game Initialization Tests")
    class GameInitializationTests {

        @Test
        @DisplayName("should initialize the game with IN_PROGRESS status and correct grid size")
        void testGameInitialization() {
            assertEquals(GameStatus.IN_PROGRESS, gameController.getGameStatus());
            assertEquals(4, gameController.getMinefieldSize());
        }
    }

    @Nested
    @DisplayName("Reveal Square Tests")
    class RevealSquareTests {

        @Test
        @DisplayName("should lose the game if a mine is revealed")
        void testRevealSquareWithMine() {
            // Given
            gameController.placeMineAt("A1");

            // When
            gameController.revealSquare("A1");

            // Then
            assertEquals(GameStatus.LOST, gameController.getGameStatus());
            assertTrue(gameController.isSquareRevealed("A1"));
        }

        @Test
        @DisplayName("should reveal square and adjacent squares with no mines")
        void testRevealSquareNoAdjacentMines() {
            // Given
            gameController.placeMineAt("A1"); // Place a mine at A1

            // When
            gameController.revealSquare("D4"); // D4 should have no adjacent mines

            // Then
            assertTrue(gameController.isSquareRevealed("D4"));
            assertTrue(gameController.isSquareRevealed("D3"));
            assertTrue(gameController.isSquareRevealed("C4"));
            assertTrue(gameController.isSquareRevealed("C3"));
            assertEquals(GameStatus.IN_PROGRESS, gameController.getGameStatus());
        }

        @Test
        @DisplayName("should not change game state if revealing an already revealed square")
        void testRevealSquareAlreadyRevealed() {
            // Given
            gameController.placeMineAt("A1");
            gameController.revealSquare("B2");

            // When
            gameController.revealSquare("B2"); // Reveal B2 again

            // Then
            assertEquals(GameStatus.IN_PROGRESS, gameController.getGameStatus());
            assertTrue(gameController.isSquareRevealed("B2"));
        }

        @Test
        @DisplayName("should win the game if all non-mine squares are revealed")
        void testRevealSquareWinningCondition() {
            // Given
            gameController = new GameControllerImpl(2, 1); // A 2x2 grid with 1 mine
            gameController.placeMineAt("A1");  // Place a mine at A1

            // When
            gameController.revealSquare("B1");
            gameController.revealSquare("B2");
            gameController.revealSquare("A2");

            // Then
            assertEquals(GameStatus.WON, gameController.getGameStatus());
        }
    }

    @Nested
    @DisplayName("isMineAt method Tests")
    class IsMineAtTests {

        @Test
        @DisplayName("should return true if there is a mine at the specified position")
        void testIsMineAt_True() {
            // Given
            gameController.placeMineAt("A1");

            // When
            boolean result = gameController.isMineAt("A1");

            // Then
            assertTrue(result);
        }

        @Test
        @DisplayName("should return false if there is no mine at the specified position")
        void testIsMineAt_False() {
            // When
            boolean result = gameController.isMineAt("B2");

            // Then
            assertFalse(result);
        }
    }

    @Nested
    @DisplayName("revealAllCells method Tests")
    class RevealAllCellsTests {

        @Test
        @DisplayName("should reveal all cells in the minefield")
        void testRevealAllCells() {
            // Given
            gameController.placeMineAt("A1");
            gameController.placeMineAt("B2");

            // When
            gameController.revealAllCells();

            // Then
            for (int row = 0; row < gameController.getMinefieldSize(); row++) {
                for (int col = 1; col <= gameController.getMinefieldSize(); col++) {
                    String position = "" + (char) ('A' + row) + col;
                    assertTrue(gameController.isSquareRevealed(position));
                }
            }
        }
    }

    @Nested
    @DisplayName("getAdjacentMinesCount method Tests")
    class GetAdjacentMinesCountTests {

        @Test
        @DisplayName("should return the correct number of adjacent mines")
        void testGetAdjacentMinesCount() {
            // Given
            gameController.placeMineAt("A1");
            gameController.placeMineAt("A2");

            // When
            int adjacentMines = gameController.getAdjacentMinesCount("B1");

            // Then
            assertEquals(2, adjacentMines);
        }

        @Test
        @DisplayName("should return zero if there are no adjacent mines")
        void testGetAdjacentMinesCount_NoMines() {
            // Given
            gameController.placeMineAt("A1");

            // When
            int adjacentMines = gameController.getAdjacentMinesCount("C3");

            // Then
            assertEquals(0, adjacentMines);
        }
    }

    @Nested
    @DisplayName("Multiple Reveal Tests")
    class MultipleRevealTests {

        @Test
        @DisplayName("should not change game state if revealing a square multiple times")
        void testMultipleReveals() {
            // Given
            gameController.placeMineAt("A1");

            // When
            gameController.revealSquare("B2");  // Reveal B2, which is adjacent to no mines
            gameController.revealSquare("B2");  // Reveal B2 again, should not affect the game state

            // Then
            assertEquals(GameStatus.IN_PROGRESS, gameController.getGameStatus());
        }
    }

    @Nested
    @DisplayName("Invalid Input Tests")
    class InvalidInputTests {

        @Test
        @DisplayName("should throw an exception for an invalid grid position")
        void testInvalidPosition() {
            // Given
            String invalidPosition = "Z9";

            // When & Then
            Exception exception = assertThrows(GameInputException.class, () -> {
                gameController.revealSquare(invalidPosition);
            });
            assertEquals("Invalid input. Please select a valid square (e.g., A1).", exception.getMessage());
        }
    }
}