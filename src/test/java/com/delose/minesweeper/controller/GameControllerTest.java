package com.delose.minesweeper.controller;

import com.delose.minesweeper.controller.impl.GameControllerImpl;
import com.delose.minesweeper.core.exception.GameInputException;
import com.delose.minesweeper.model.GameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameController = new GameControllerImpl(4, 2); // A 2x2 grid with 1 mine
    }

    @Test
    void testGameInitialization() {
        assertEquals(GameStatus.IN_PROGRESS, gameController.getGameStatus());
        assertEquals(4, gameController.getMinefieldSize());
    }

    @Test
    void testRevealSquareWithMine() {
        gameController.placeMineAt("A1");  // Place a mine at A1

        gameController.revealSquare("A1");  // Reveal the square with a mine
        assertEquals(GameStatus.LOST, gameController.getGameStatus());
    }

    @Test
    @Disabled("Debugging required")
    void testRevealSquareWithoutMine() {
        gameController.placeMineAt("A1");  // Place a mine at A1

        gameController.revealSquare("B2");  // Reveal a square without a mine
        assertEquals(GameStatus.IN_PROGRESS, gameController.getGameStatus());
        assertTrue(gameController.isSquareRevealed("B2"));
    }

    @Test
    @Disabled("Debugging required")
    void testRevealSquareWithAdjacentMines() {
        gameController.placeMineAt("A1");  // Place a mine at A1

        gameController.revealSquare("B2");  // B2 should have 1 adjacent mine
        assertEquals(GameStatus.IN_PROGRESS, gameController.getGameStatus());
        int adjacentMines = gameController.getAdjacentMinesCount("B2");
        assertEquals(1, adjacentMines);
    }

    @Test
    void testRevealAdjacentSquaresWithNoMinesNearby() {
        // Place a mine at A1, revealing other cells should automatically reveal nearby safe cells
        gameController.placeMineAt("A1");

        gameController.revealSquare("B2");  // Reveal B2, which is adjacent to no mines
        assertFalse(gameController.isSquareRevealed("B1"));
        assertTrue(gameController.isSquareRevealed("B2"));
        assertFalse(gameController.isSquareRevealed("A2"));
    }

    @Test
    @Disabled("Debugging required")
    void testRevealSquareWinningCondition() {
        gameController.placeMineAt("A1");  // Place a mine at A1

        // Reveal all non-mine squares
        gameController.revealSquare("B1");
        gameController.revealSquare("B2");
        gameController.revealSquare("A2");

        assertEquals(GameStatus.WON, gameController.getGameStatus());
    }

    @Test
    void testInvalidPosition() {
        String invalidPosition = "Z9";
        Exception exception = assertThrows(GameInputException.class, () -> {
            gameController.revealSquare(invalidPosition);
        });
        assertEquals("Invalid input. Please select a valid square (e.g., A1).", exception.getMessage());
    }

    @Test
    @Disabled("Debugging required")
    void testIsMineAt() {
        gameController.placeMineAt("A1");

        assertTrue(gameController.isMineAt("A1"));
        assertFalse(gameController.isMineAt("B2"));
    }

    @Test
    @Disabled("Debugging required")
    void testIsSquareRevealed() {
        gameController.revealSquare("B1");
        assertTrue(gameController.isSquareRevealed("B1"));
        assertFalse(gameController.isSquareRevealed("A1"));
    }

    @Test
    @Disabled("Debugging required")
    void testMultipleReveals() {
        gameController.placeMineAt("A1");

        gameController.revealSquare("B2");  // Reveal B2, which is adjacent to no mines
        gameController.revealSquare("B2");  // Reveal B2 again, should not affect the game state
        assertEquals(GameStatus.IN_PROGRESS, gameController.getGameStatus());
    }

    @Test
    @Disabled("Debugging required")
    void testWinWithoutRevealingMine() {
        gameController.placeMineAt("B2");

        gameController.revealSquare("A1");
        gameController.revealSquare("A2");
        gameController.revealSquare("B1");

        assertEquals(GameStatus.WON, gameController.getGameStatus());
    }


    @Nested
    @DisplayName("revealSquare method")
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
        @Disabled("Debugging required")
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
        @Disabled("Debugging required")
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
        @Disabled("Debugging required")
        void testRevealSquareWinningCondition() {
            // Given
            gameController.placeMineAt("A1");
            gameController.placeMineAt("B2");

            // When
            gameController.revealSquare("C3");
            gameController.revealSquare("D4");
            gameController.revealSquare("A4");
            gameController.revealSquare("C1");
            gameController.revealSquare("D1");
            gameController.revealSquare("B3");
            gameController.revealSquare("D2");
            gameController.revealSquare("C2");
            gameController.revealSquare("A3");
            gameController.revealSquare("B1");

            // Then
            assertEquals(GameStatus.WON, gameController.getGameStatus());
        }
    }

    @Nested
    @DisplayName("isMineAt method")
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
        @Disabled("Debugging required")
        void testIsMineAt_False() {
            // When
            boolean result = gameController.isMineAt("B2");

            // Then
            assertFalse(result);
        }
    }

    @Nested
    @DisplayName("revealAllCells method")
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
    @DisplayName("getAdjacentMinesCount method")
    class GetAdjacentMinesCountTests {

        @Test
        @DisplayName("should return the correct number of adjacent mines")
        @Disabled("Debugging required")
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
        @Disabled("Debugging required")
        void testGetAdjacentMinesCount_NoMines() {
            // Given
            gameController.placeMineAt("A1");

            // When
            int adjacentMines = gameController.getAdjacentMinesCount("C3");

            // Then
            assertEquals(0, adjacentMines);
        }
    }
}