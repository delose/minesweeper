package com.delose.minesweeper.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.delose.minesweeper.core.exception.GameInputException;

import static org.junit.jupiter.api.Assertions.*;

class MinefieldTest {

    private Minefield minefield;

    @BeforeEach
    void setUp() {
        minefield = new Minefield(4, 2); // A 4x4 grid with 2 mines
    }

    @Test
    void testMinefieldInitialization() {
        assertEquals(4, minefield.getSize());
        assertEquals(2, minefield.getNumberOfMines());
        assertEquals(0, minefield.countMines()); // No mines placed initially
    }

    @Test
    void testPlaceMine() {
        minefield.placeMine(0, 0);  // Place a mine at (0, 0)
        assertTrue(minefield.isMineAt(0, 0));
        assertEquals(1, minefield.countMines());
    }

    @Test
    void testPlaceMineOutOfBounds() {
        Exception exception = assertThrows(GameInputException.class, () -> {
            minefield.placeMine(5, 5);  // Out of bounds
        });
        assertEquals("Coordinates are out of bounds.", exception.getMessage());
    }

    @Test
    void testRandomMinePlacement() {
        minefield.placeMines();
        assertEquals(2, minefield.countMines());
    }

    @Test
    void testIsMineAt() {
        minefield.placeMine(1, 1);  // Place a mine at (1, 1)
        assertTrue(minefield.isMineAt(1, 1));
        assertFalse(minefield.isMineAt(0, 0));  // No mine at (0, 0)
    }

    @Test
    void testIsMineAtOutOfBounds() {
        Exception exception = assertThrows(GameInputException.class, () -> {
            minefield.isMineAt(4, 4);  // Out of bounds
        });
        assertEquals("Coordinates are out of bounds.", exception.getMessage());
    }

    @Test
    void testCalculateAdjacentMines() {
        minefield.placeMine(0, 0);  // Place a mine at (0, 0)
        minefield.placeMine(1, 1);  // Place another mine at (1, 1)

        // Test adjacent mines calculation
        assertEquals(2, minefield.calculateAdjacentMines(0, 1));  // (0, 1) is adjacent to 2 mines
        assertEquals(1, minefield.calculateAdjacentMines(2, 2));  // (2, 2) is adjacent to 1 mine
        assertEquals(0, minefield.calculateAdjacentMines(3, 3));  // (3, 3) is not adjacent to any mines
    }

    @Test
    void testCalculateAdjacentMinesOutOfBounds() {
        Exception exception = assertThrows(GameInputException.class, () -> {
            minefield.calculateAdjacentMines(4, 4);  // Out of bounds
        });
        assertEquals("Coordinates are out of bounds.", exception.getMessage());
    }

    @Test
    void testCountMines() {
        assertEquals(0, minefield.countMines());  // Initially, no mines

        minefield.placeMine(0, 0);
        minefield.placeMine(1, 1);
        assertEquals(2, minefield.countMines());

        minefield.placeMine(1, 1);  // Place mine at the same location
        assertEquals(2, minefield.countMines());  // Count should remain the same
    }
}