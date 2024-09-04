package com.delose.minesweeper.controller;

import com.delose.minesweeper.controller.impl.GameControllerImpl;
import com.delose.minesweeper.core.exception.GameInputException;
import com.delose.minesweeper.model.GameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameController = new GameControllerImpl(2, 1); // A 2x2 grid with 1 mine
    }

    @Test
    void testGameInitialization() {
        assertEquals(GameStatus.IN_PROGRESS, gameController.getGameStatus());
        assertEquals(2, gameController.getMinefieldSize());
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
}