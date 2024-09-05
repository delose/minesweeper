package com.delose.minesweeper.controller;

import com.delose.minesweeper.controller.impl.GameControllerImpl;
import com.delose.minesweeper.core.exception.GameInputException;
import com.delose.minesweeper.core.util.config.MessageProvider;
import com.delose.minesweeper.model.GameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    private GameController gameController;

    @Nested
    @DisplayName("Game Initialization Tests")
    class GameInitializationTests {

        @BeforeEach
        void setUp() {
            gameController = new GameControllerImpl(4, 2); // A 4x4 grid with 2 mines
        }

        @Test
        @DisplayName("should initialize the game with IN_PROGRESS status and correct grid size (4x4 grid)")
        void testGameInitialization_4x4() {
            assertEquals(GameStatus.IN_PROGRESS, gameController.getGameStatus());
            assertEquals(4, gameController.getMinefieldSize());
        }

        @Test
        @DisplayName("should initialize the game with IN_PROGRESS status and correct grid size (5x5 grid)")
        void testGameInitialization_5x5() {
            gameController = new GameControllerImpl(5, 3); // A 5x5 grid with 3 mines
            assertEquals(GameStatus.IN_PROGRESS, gameController.getGameStatus());
            assertEquals(5, gameController.getMinefieldSize());
        }

        @Test
        @DisplayName("should initialize the game with IN_PROGRESS status and correct grid size (3x3 grid)")
        void testGameInitialization_3x3() {
            gameController = new GameControllerImpl(3, 1); // A 3x3 grid with 1 mine
            assertEquals(GameStatus.IN_PROGRESS, gameController.getGameStatus());
            assertEquals(3, gameController.getMinefieldSize());
        }
    }

    @Nested
    @DisplayName("Reveal Square Tests")
    class RevealSquareTests {

        @BeforeEach
        void setUp() {
            gameController = new GameControllerImpl(4, 2); // A 4x4 grid with 2 mines
        }

        @Test
        @DisplayName("should lose the game if a mine is revealed (4x4 grid)")
        void testRevealSquareWithMine_4x4() {
            gameController.placeMineAt("A1");
            gameController.revealSquare("A1");
            assertEquals(GameStatus.LOST, gameController.getGameStatus());
            assertTrue(gameController.isSquareRevealed("A1"));
        }

        @Test
        @DisplayName("should reveal square and adjacent squares with no mines (4x4 grid)")
        void testRevealSquareNoAdjacentMines_4x4() {
            gameController.placeMineAt("A1");
            gameController.revealSquare("D4");
            assertTrue(gameController.isSquareRevealed("D4"));
            assertTrue(gameController.isSquareRevealed("D3"));
            assertTrue(gameController.isSquareRevealed("C4"));
            assertTrue(gameController.isSquareRevealed("C3"));
            assertEquals(GameStatus.IN_PROGRESS, gameController.getGameStatus());
        }

        @Test
        @DisplayName("should not change game state if revealing an already revealed square (4x4 grid)")
        void testRevealSquareAlreadyRevealed_4x4() {
            gameController.placeMineAt("A1");
            gameController.revealSquare("B2");
            gameController.revealSquare("B2");
            assertEquals(GameStatus.IN_PROGRESS, gameController.getGameStatus());
            assertTrue(gameController.isSquareRevealed("B2"));
        }

        @Test
        @DisplayName("should win the game if all non-mine squares are revealed (2x2 grid)")
        void testRevealSquareWinningCondition_2x2() {
            gameController = new GameControllerImpl(2, 1);
            gameController.placeMineAt("A1");
            gameController.revealSquare("B1");
            gameController.revealSquare("B2");
            gameController.revealSquare("A2");
            assertEquals(GameStatus.WON, gameController.getGameStatus());
        }

        @Test
        @DisplayName("should lose the game if a mine is revealed (3x3 grid)")
        void testRevealSquareWithMine_3x3() {
            gameController = new GameControllerImpl(3, 1);
            gameController.placeMineAt("B2");
            gameController.revealSquare("B2");
            assertEquals(GameStatus.LOST, gameController.getGameStatus());
            assertTrue(gameController.isSquareRevealed("B2"));
        }

        @Test
        @DisplayName("should reveal square and adjacent squares with no mines (5x5 grid)")
        void testRevealSquareNoAdjacentMines_5x5() {
            gameController = new GameControllerImpl(5, 3);
            gameController.placeMineAt("A1");
            gameController.revealSquare("E5");
            assertTrue(gameController.isSquareRevealed("E5"));
            assertTrue(gameController.isSquareRevealed("E4"));
            assertTrue(gameController.isSquareRevealed("D5"));
            assertTrue(gameController.isSquareRevealed("D4"));
            assertEquals(GameStatus.IN_PROGRESS, gameController.getGameStatus());
        }
    }

    @Nested
    @DisplayName("isMineAt method Tests")
    class IsMineAtTests {

        @BeforeEach
        void setUp() {
            gameController = new GameControllerImpl(4, 2); // A 4x4 grid with 2 mines
        }

        @Test
        @DisplayName("should return true if there is a mine at the specified position (4x4 grid)")
        void testIsMineAt_True_4x4() {
            gameController.placeMineAt("A1");
            assertTrue(gameController.isMineAt("A1"));
        }

        @Test
        @DisplayName("should return false if there is no mine at the specified position (4x4 grid)")
        void testIsMineAt_False_4x4() {
            assertFalse(gameController.isMineAt("B2"));
        }

        @Test
        @DisplayName("should return true if there is a mine at the specified position (3x3 grid)")
        void testIsMineAt_True_3x3() {
            gameController = new GameControllerImpl(3, 1);
            gameController.placeMineAt("B2");
            assertTrue(gameController.isMineAt("B2"));
        }

        @Test
        @DisplayName("should return false if there is no mine at the specified position (5x5 grid)")
        void testIsMineAt_False_5x5() {
            gameController = new GameControllerImpl(5, 3);
            assertFalse(gameController.isMineAt("C3"));
        }
    }

    @Nested
    @DisplayName("revealAllCells method Tests")
    class RevealAllCellsTests {

        @BeforeEach
        void setUp() {
            gameController = new GameControllerImpl(4, 2); // A 4x4 grid with 2 mines
        }

        @Test
        @DisplayName("should reveal all cells in the minefield (4x4 grid)")
        void testRevealAllCells_4x4() {
            gameController.placeMineAt("A1");
            gameController.placeMineAt("B2");
            gameController.revealAllCells();
            for (int row = 0; row < gameController.getMinefieldSize(); row++) {
                for (int col = 1; col <= gameController.getMinefieldSize(); col++) {
                    String position = "" + (char) ('A' + row) + col;
                    assertTrue(gameController.isSquareRevealed(position));
                }
            }
        }

        @Test
        @DisplayName("should reveal all cells in the minefield (5x5 grid)")
        void testRevealAllCells_5x5() {
            gameController = new GameControllerImpl(5, 3);
            gameController.placeMineAt("A1");
            gameController.placeMineAt("B2");
            gameController.placeMineAt("C3");
            gameController.revealAllCells();
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

        @BeforeEach
        void setUp() {
            gameController = new GameControllerImpl(4, 2); // A 4x4 grid with 2 mines
        }

        @Test
        @DisplayName("should return the correct number of adjacent mines (5x5 grid)")
        void testGetAdjacentMinesCount_5x5() {
            gameController = new GameControllerImpl(5, 3);
            gameController.placeMineAt("A1");
            gameController.placeMineAt("A2");
            gameController.placeMineAt("B1");
            int adjacentMines = gameController.getAdjacentMinesCount("B2");
            assertEquals(3, adjacentMines);
        }

        @Test
        @DisplayName("should return zero if there are no adjacent mines (4x4 grid)")
        void testGetAdjacentMinesCount_NoMines_4x4() {
            gameController.placeMineAt("A1");
            int adjacentMines = gameController.getAdjacentMinesCount("C3");
            assertEquals(0, adjacentMines);
        }

        @Test
        @DisplayName("should return zero if there are no adjacent mines (3x3 grid)")
        void testGetAdjacentMinesCount_NoMines_3x3() {
            gameController = new GameControllerImpl(3, 1);
            gameController.placeMineAt("A1");
            int adjacentMines = gameController.getAdjacentMinesCount("C3");
            assertEquals(0, adjacentMines);
        }
    }

    @Nested
    @DisplayName("Multiple Reveal Tests")
    class MultipleRevealTests {

        @BeforeEach
        void setUp() {
            gameController = new GameControllerImpl(4, 2); // A 4x4 grid with 2 mines
        }

        @Test
        @DisplayName("should not change game state if revealing a square multiple times (4x4 grid)")
        void testMultipleReveals_4x4() {
            gameController.placeMineAt("A1");
            gameController.revealSquare("B2");  // Reveal B2, which is adjacent to no mines
            gameController.revealSquare("B2");  // Reveal B2 again, should not affect the game state
            assertEquals(GameStatus.IN_PROGRESS, gameController.getGameStatus());
        }

        @Test
        @DisplayName("should not change game state if revealing a square multiple times (3x3 grid)")
        void testMultipleReveals_3x3() {
            gameController = new GameControllerImpl(3, 1);
            gameController.placeMineAt("A1");
            gameController.revealSquare("B2");
            gameController.revealSquare("B2");
            assertEquals(GameStatus.IN_PROGRESS, gameController.getGameStatus());
        }

        @Test
        @DisplayName("should not change game state if revealing a square multiple times (5x5 grid)")
        void testMultipleReveals_5x5() {
            gameController = new GameControllerImpl(5, 3);
            gameController.placeMineAt("A1");
            gameController.revealSquare("C3");
            gameController.revealSquare("C3");
            assertEquals(GameStatus.IN_PROGRESS, gameController.getGameStatus());
        }
    }

    @Nested
    @DisplayName("Invalid Input Tests")
    class InvalidInputTests {

        @BeforeEach
        void setUp() {
            gameController = new GameControllerImpl(4, 2); // A 4x4 grid with 2 mines
        }

        @Test
        @DisplayName("should throw an exception for an invalid grid position (4x4 grid)")
        void testInvalidPosition_4x4() {
            String invalidPosition = "Z9";
            Exception exception = assertThrows(GameInputException.class, () -> {
                gameController.revealSquare(invalidPosition);
            });
            assertEquals(MessageProvider.getMessage("game.invalidInputSquare"), exception.getMessage());
        }

        @Test
        @DisplayName("should throw an exception for an invalid grid position (5x5 grid)")
        void testInvalidPosition_5x5() {
            gameController = new GameControllerImpl(5, 3);
            String invalidPosition = "Z9";
            Exception exception = assertThrows(GameInputException.class, () -> {
                gameController.revealSquare(invalidPosition);
            });
            assertEquals(MessageProvider.getMessage("game.invalidInputSquare"), exception.getMessage());
        }

        @Test
        @DisplayName("should throw an exception for an invalid grid position (3x3 grid)")
        void testInvalidPosition_3x3() {
            gameController = new GameControllerImpl(3, 1);
            String invalidPosition = "Z9";
            Exception exception = assertThrows(GameInputException.class, () -> {
                gameController.revealSquare(invalidPosition);
            });
            assertEquals(MessageProvider.getMessage("game.invalidInputSquare"), exception.getMessage());
        }
    }
}