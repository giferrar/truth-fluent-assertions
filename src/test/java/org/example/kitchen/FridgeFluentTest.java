package org.example.kitchen;

import org.example.kitchen.exception.FridgeFullException;
import org.example.kitchen.exception.NotEnoughFoodException;
import org.example.kitchen.model.Food;
import org.example.kitchen.utils.TestableFridge;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;
import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;
import static org.example.kitchen.Fridge.MAX_SPACE;
import static org.example.kitchen.utils.TestableFridge.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FridgeFluentTest {

    private final TestableFridge fridge = new TestableFridge();

    @Nested
    class PutTests {

    }

    @Nested
    class GetAllTests {

    }

    @Nested
    class GetSomeTests {

    }

    @Nested
    class ListFoodTests {

    }

    @Nested
    class GetEmptySpaceTests {

    }

    @Nested
    class EmptyTests {
        @Test
        public void given_fridge_with_an_apple_when_empty_then_fridge_is_empty() {
            // Given
            fridge.with(AN_APPLE);

            // When
            fridge.empty();

            // Then
            assertThat(fridge.getEmptySpace()).isEqualTo(MAX_SPACE);
            assertThat(fridge.listFood()).isEmpty();
        }

        @Test
        public void given_empty_fridge_when_empty_then_fridge_is_empty() {
            // Given
            fridge.with();

            // When
            fridge.empty();

            // Then
            assertThat(fridge.getEmptySpace()).isEqualTo(MAX_SPACE);
            assertThat(fridge.listFood()).isEmpty();
        }
    }

}
