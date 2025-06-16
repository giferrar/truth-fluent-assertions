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
        @Test
        public void given_empty_fridge_when_list_food_then_list_is_empty() {
            // Given
            fridge.with();

            // When
            List<String> list = fridge.listFood();

            // Then
            assertThat(list).isEmpty();
        }

        @Test
        public void given_fridge_with_an_apple_when_list_food_then_list_contains_only_apple() {
            // Given
            fridge.with(AN_APPLE);

            // When
            List<String> list = fridge.listFood();

            // Then
            assertThat(list).containsExactly(AN_APPLE.name);
        }

        @Test
        public void given_fridge_with_apples_and_bananas_when_list_food_then_list_contains_apples_and_bananas() {
            // Given
            fridge.with(TEN_APPLES, TEN_BANANAS);

            // When
            List<String> list = fridge.listFood();

            // Then
            assertThat(list).containsExactly(TEN_APPLES.name, TEN_BANANAS.name);
        }
    }

    @Nested
    class GetEmptySpaceTests {
        @Test
        public void given_empty_fridge_when_get_empty_space_then_empty_space_is_100() {
            // Given
            fridge.with();

            // When
            int emptySpace = fridge.getEmptySpace();

            // Then
            assertThat(emptySpace).isEqualTo(MAX_SPACE);
        }

        @Test
        public void given_fridge_with_an_apple_when_get_empty_space_then_empty_space_is_99() {
            // Given
            fridge.with(AN_APPLE);

            // When
            int emptySpace = fridge.getEmptySpace();

            // Then
            assertThat(emptySpace).isEqualTo(MAX_SPACE - AN_APPLE.quantity);
        }

        @Test
        public void given_fridge_with_hundred_apples_when_get_empty_space_then_empty_space_is_0() {
            // Given
            fridge.with(HUNDRED_APPLES);

            // When
            int emptySpace = fridge.getEmptySpace();

            // Then
            assertThat(emptySpace).isEqualTo(MAX_SPACE - HUNDRED_APPLES.quantity);
        }
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
