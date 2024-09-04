package com.delose.minesweeper.model;

import com.delose.minesweeper.core.exception.GameInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MinefieldTest {

    private Minefield minefield;

    @BeforeEach
    void setUp() {
        minefield = new Minefield(4, 2); // A 4x4 grid with 2 mines
    }

    @Nested
    @DisplayName("Minefield Initialization")
    class MinefieldInitializationTests {

        @Test
        @DisplayName("should initialize with correct size and number of mines")
        void testMinefieldInitialization() {
            assertEquals(4, minefield.getSize());
            assertEquals(2, minefield.getNumberOfMines());
            assertEquals(0, minefield.countMines()); // No mines placed initially
        }

        @Test
        @DisplayName("should throw exception when initializing with more than maximum allowed mines")
        void testMinefieldInitialization_MoreThanMaxMines() {
            int gridSize = 4;
            int maxMines = (int) (gridSize * gridSize * 0.35); // Using the same formula from Minefield class

            Exception exception = assertThrows(GameInputException.class, () -> {
                new Minefield(gridSize, maxMines + 1); // Attempting to initialize with more than allowed mines
            });
            assertEquals("Too many mines for the given grid size.", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Mine Placement")
    class MinePlacementTests {

        @Test
        @DisplayName("should place a mine at the specified location")
        void testPlaceMine() {
            minefield.placeMine(0, 0);  // Place a mine at (0, 0)
            assertTrue(minefield.isMineAt(0, 0));
            assertEquals(1, minefield.countMines());
        }

        @Test
        @DisplayName("should throw exception when placing a mine out of bounds")
        void testPlaceMineOutOfBounds() {
            Exception exception = assertThrows(GameInputException.class, () -> {
                minefield.placeMine(5, 5);  // Out of bounds
            });
            assertEquals("Coordinates are out of bounds.", exception.getMessage());
        }

        @Test
        @DisplayName("should place mines randomly in the grid")
        void testRandomMinePlacement() {
            minefield.placeMines();
            assertEquals(2, minefield.countMines());
        }

        @Test
        @DisplayName("should correctly count the number of mines placed")
        void testCountMines() {
            assertEquals(0, minefield.countMines());  // Initially, no mines

            minefield.placeMine(0, 0);
            minefield.placeMine(1, 1);
            assertEquals(2, minefield.countMines());

            minefield.placeMine(1, 1);  // Place mine at the same location
            assertEquals(2, minefield.countMines());  // Count should remain the same
        }
    }

    @Nested
    @DisplayName("Mine Location Checks")
    class MineLocationTests {

        @Test
        @DisplayName("should return true if there is a mine at the specified location")
        void testIsMineAt() {
            minefield.placeMine(1, 1);  // Place a mine at (1, 1)
            assertTrue(minefield.isMineAt(1, 1));
            assertFalse(minefield.isMineAt(0, 0));  // No mine at (0, 0)
        }

        @Test
        @DisplayName("should throw exception when checking for a mine out of bounds")
        void testIsMineAtOutOfBounds() {
            Exception exception = assertThrows(GameInputException.class, () -> {
                minefield.isMineAt(4, 4);  // Out of bounds
            });
            assertEquals("Coordinates are out of bounds.", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Adjacent Mines Calculation")
    class AdjacentMinesCalculationTests {

        @Test
        @DisplayName("should correctly calculate the number of adjacent mines")
        void testCalculateAdjacentMines() {
            minefield.placeMine(0, 0);  // Place a mine at (0, 0)
            minefield.placeMine(1, 1);  // Place another mine at (1, 1)

            // Test adjacent mines calculation
            assertEquals(2, minefield.calculateAdjacentMines(0, 1));  // (0, 1) is adjacent to 2 mines
            assertEquals(1, minefield.calculateAdjacentMines(2, 2));  // (2, 2) is adjacent to 1 mine
            assertEquals(0, minefield.calculateAdjacentMines(3, 3));  // (3, 3) is not adjacent to any mines
        }

        @Test
        @DisplayName("should throw exception when calculating adjacent mines out of bounds")
        void testCalculateAdjacentMinesOutOfBounds() {
            Exception exception = assertThrows(GameInputException.class, () -> {
                minefield.calculateAdjacentMines(4, 4);  // Out of bounds
            });
            assertEquals("Coordinates are out of bounds.", exception.getMessage());
        }
    }
}