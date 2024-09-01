package com.delose.minesweeper.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    private Cell cell;

    @BeforeEach
    void setUp() {
        cell = new Cell();
    }

    @Test
    void testInitialCellState() {
        assertFalse(cell.isRevealed(), "Cell should not be revealed initially.");
        assertFalse(cell.hasMine(), "Cell should not contain a mine initially.");
        assertEquals(0, cell.getAdjacentMinesCount(), "Adjacent mines count should be 0 initially.");
    }

    @Test
    void testSetMine() {
        cell.setMine(true);
        assertTrue(cell.hasMine(), "Cell should contain a mine after setting.");
    }

    @Test
    void testRevealCell() {
        cell.reveal();
        assertTrue(cell.isRevealed(), "Cell should be revealed after calling reveal().");
    }

    @Test
    void testSetAdjacentMinesCount() {
        cell.setAdjacentMinesCount(3);
        assertEquals(3, cell.getAdjacentMinesCount(), "Cell should have 3 adjacent mines after setting.");
    }

    @Test
    void testInvalidAdjacentMinesCount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cell.setAdjacentMinesCount(-1);
        });
        assertEquals("Adjacent mines count cannot be negative.", exception.getMessage());
    }
}