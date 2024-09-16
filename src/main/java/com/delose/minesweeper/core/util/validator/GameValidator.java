package com.delose.minesweeper.core.util.validator;

import com.delose.minesweeper.core.exception.GameInputException;
import com.delose.minesweeper.core.util.config.MessageProvider;

public class GameValidator {

    public void validateInputSize(String input) {
        if (input == null || input.length() < 2 || input.length() > 3) {
            throw new GameInputException(MessageProvider.getMessage("game.invalidInputGridPosition"));
        }
    }

}
