package com.delose.minesweeper.core.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.delose.minesweeper.core.exception.GameInputException;
import com.delose.minesweeper.core.util.validator.GameValidator;

public class GameValidatorTest {
    
    private GameValidator gameValidator;

    @BeforeEach
    void setUp() {
        gameValidator = new GameValidator();
    }

    @Nested
    @DisplayName("Validate Input Size")
    class ValidateInputSizeTests {
        @Test
        @DisplayName("should not throw exception for valid input")
        void testValidateInputSize_ValidInput() {
            String validInput = "A1";
            assertDoesNotThrow(() -> gameValidator.validateInputSize(validInput));
        }

        @Test
        @DisplayName("should throw exception for input length less than 2")
        void testValidateInputSize_InvalidInput() {
            String invalidInput = "A";
            assertThrows(GameInputException.class, () -> gameValidator.validateInputSize(invalidInput));
        }

        @Test
        @DisplayName("should throw exception for null input")
        void testValidateInputSize_NullInput() {
            String nullInput = null;
            assertThrows(GameInputException.class, () -> gameValidator.validateInputSize(nullInput));
        }

        @Test
        @DisplayName("should throw exception for input length greater than 3")
        void testValidateInputSize_InvalidInputLength() {
            String invalidInput = "A123";
            assertThrows(GameInputException.class, () -> gameValidator.validateInputSize(invalidInput));
        }
        
    }

}
