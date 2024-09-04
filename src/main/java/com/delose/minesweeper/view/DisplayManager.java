package com.delose.minesweeper.view;

public interface DisplayManager {
    /**
    * Renders the current state of the minefield to be displayed to the player.
    *
    * @return a String representing the current minefield display
    */
   String renderMinefield();

   /**
    * Displays the end game message based on the game status.
    *
    * @return a String containing the appropriate end game message
    */
   String displayEndGameMessage();
}