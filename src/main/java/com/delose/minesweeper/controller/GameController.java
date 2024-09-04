package com.delose.minesweeper.controller;

import com.delose.minesweeper.model.GameStatus;

/**
 * Manages the overall flow of the Minesweeper game.
 * Handles the game initialization, user inputs, and game state transitions.
 */
public interface GameController {

    GameStatus getGameStatus();

    int getMinefieldSize();

    int getAdjacentMinesCount(String position);

    void revealSquare(String position);

    boolean isMineAt(String position);

    boolean isSquareRevealed(String position);

    // only used for testing
    void placeMineAt(String position);

    void revealAllCells();

    void placeMinesRandmly();
}