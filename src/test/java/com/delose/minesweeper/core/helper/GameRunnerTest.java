package com.delose.minesweeper.core.helper;

import com.delose.minesweeper.controller.GameController;
import com.delose.minesweeper.model.GameStatus;
import com.delose.minesweeper.core.exception.GameInputException;
import com.delose.minesweeper.view.DisplayManager;
import com.delose.minesweeper.controller.PlayerInputHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameRunnerTest {

    private GameRunner gameRunner;
    private GameController gameController;
    private DisplayManager displayManager;
    private PlayerInputHandler inputHandler;
    private GameComponents components;

    @BeforeEach
    void setUp() {
        gameController = mock(GameController.class);
        displayManager = mock(DisplayManager.class);
        inputHandler = mock(PlayerInputHandler.class);

        components = new GameComponents(gameController, displayManager, inputHandler);
    }

    @Nested
    @DisplayName("When running the game loop")
    class RunGameLoopTests {

        @Test
        @DisplayName("It should continue running until the game is won or lost")
        void testRunGameLoop_GameContinuesUntilEnd() {
            when(gameController.getGameStatus()).thenReturn(GameStatus.IN_PROGRESS).thenReturn(GameStatus.LOST);

            String userInput = "A1\n"; // Simulate user input
            InputStream in = new ByteArrayInputStream(userInput.getBytes());
            System.setIn(in);

            gameRunner = new GameRunner(components);
            gameRunner.runGameLoop();

            verify(gameController, times(1)).revealSquare(any());
            verify(gameController, times(1)).revealAllCells();
            verify(displayManager, times(2)).renderMinefield();
            verify(displayManager, times(1)).displayEndGameMessage();
        }
        
        @Test
        @DisplayName("It should handle invalid input and continue running")
        void testRunGameLoop_InvalidInputContinuesGame() {
            when(gameController.getGameStatus()).thenReturn(GameStatus.IN_PROGRESS).thenReturn(GameStatus.LOST);
            doThrow(new GameInputException("Invalid input")).when(inputHandler).parseInput(anyString());

            String userInput = "InvalidInput\nA1\n"; // Simulate invalid user input followed by valid input
            InputStream in = new ByteArrayInputStream(userInput.getBytes());
            System.setIn(in);

            gameRunner = new GameRunner(components);
            gameRunner.runGameLoop();

            verify(gameController, times(0)).revealSquare(anyString());
            verify(gameController, times(0)).revealAllCells();
            verify(displayManager, times(0)).displayEndGameMessage();
        }
    }

    @Nested
    @DisplayName("When prompting for replay")
    class PromptReplayTests {

        @Test
        @DisplayName("It should return true if the user does not type 'exit'")
        void testPromptReplay_UserWantsToReplay() {
            String userInput = "any key\n"; // Simulate user pressing any key
            InputStream in = new ByteArrayInputStream(userInput.getBytes());
            System.setIn(in);

            gameRunner = new GameRunner(components);
            boolean shouldReplay = gameRunner.promptReplay();

            assertTrue(shouldReplay, "Expected to replay the game");
        }

        @Test
        @DisplayName("It should return false if the user types 'exit'")
        void testPromptReplay_UserExitsGame() {
            String userInput = "exit\n"; // Simulate user typing 'exit'
            InputStream in = new ByteArrayInputStream(userInput.getBytes());
            System.setIn(in);

            gameRunner = new GameRunner(components);
            boolean shouldReplay = gameRunner.promptReplay();

            assertFalse(shouldReplay, "Expected to exit the game");
        }
    }
}