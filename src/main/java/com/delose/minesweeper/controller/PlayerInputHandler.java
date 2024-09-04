package com.delose.minesweeper.controller;
/**
 * The PlayerInputHandler interface defines the contract for handling 
 * and parsing player input within the Minesweeper game.
 * 
 * Implementations of this interface are responsible for validating 
 * and converting player input (e.g., grid positions) into a format 
 * that can be used by the game logic.
 */
public interface PlayerInputHandler {

    String parseInput(String input);
    
}