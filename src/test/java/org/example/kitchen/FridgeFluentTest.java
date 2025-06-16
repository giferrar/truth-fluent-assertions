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
        @Test
        public void given_empty_fridge_when_get_all_apples_then_empty_optional_is_returned() {
            // Given
            fridge.with();

            // When
            Optional<Food> optionalApples = fridge.getAll(AN_APPLE.name);

            // Then
            assertThat(optionalApples).isEmpty();
        }

        @Test
        public void given_fridge_with_one_apple_when_get_all_apples_then_one_apple_is_returned_and_fridge_is_empty() {
            // Given
            fridge.with(AN_APPLE);

            // When
            Optional<Food> optionalApples = fridge.getAll(AN_APPLE.name);

            // Then
            assertThat(optionalApples).isPresent();
            assertThat(optionalApples).hasValue(AN_APPLE);
            assertThat(fridge.getEmptySpace()).isEqualTo(MAX_SPACE);
        }

        @Test
        public void given_fridge_with_apples_and_bananas_when_get_all_apples_then_apples_are_returned_and_fridge_contains_bananas() {
            // Given
            fridge.with(TEN_APPLES, TEN_BANANAS);

            // When
            Optional<Food> optionalApples = fridge.getAll(TEN_APPLES.name);

            // Then
            assertThat(optionalApples).isPresent();
            assertThat(optionalApples).hasValue(TEN_APPLES);
            assertThat(fridge.listFood()).containsExactly(TEN_BANANAS.name);
        }
    }

    @Nested
    class GetSomeTests {
        @Test
        public void given_empty_fridge_when_get_an_apple_then_not_enough_food_exception_is_thrown() {
            // Given
            fridge.with();

            // When
            Executable executable = () -> fridge.getSome(AN_APPLE.name, AN_APPLE.quantity);

            // Then
            NotEnoughFoodException exception = assertThrows(NotEnoughFoodException.class, executable);
            assertThat(exception).hasMessageThat().contains("Not enough " + AN_APPLE.name + " in fridge");
        }

        @Test
        public void given_fridge_with_an_apple_when_get_ten_apples_then_not_enough_food_exception_is_thrown() {
            // Given
            fridge.with(AN_APPLE);

            // When
            Executable executable = () -> fridge.getSome(TEN_APPLES.name, TEN_APPLES.quantity);

            // Then
            NotEnoughFoodException exception = assertThrows(NotEnoughFoodException.class, executable);
            assertThat(exception).hasMessageThat().contains("Not enough " + TEN_APPLES.name + " in fridge");
        }

        @Test
        public void given_fridge_with_a_banana_when_get_an_apple_then_not_enough_food_exception_is_thrown() {
            // Given
            fridge.with(A_BANANA);

            // When
            Executable executable = () -> fridge.getSome(AN_APPLE.name, AN_APPLE.quantity);

            // Then
            NotEnoughFoodException exception = assertThrows(NotEnoughFoodException.class, executable);
            assertThat(exception).hasMessageThat().contains("Not enough " + AN_APPLE.name + " in fridge");
            assertThat(fridge.listFood()).containsExactly(A_BANANA.name);
        }

        @Test
        public void given_fridge_with_an_apple_when_get_one_apple_then_apple_is_returned_and_fridge_is_empty() {
            // Given
            fridge.with(AN_APPLE);

            // When
            Food food = fridge.getSome(AN_APPLE.name, AN_APPLE.quantity);

            // Then
            assertThat(food).isEqualTo(AN_APPLE);
            assertThat(fridge.getEmptySpace()).isEqualTo(MAX_SPACE);
            assertThat(fridge.listFood()).isEmpty();
        }

        @Test
        public void given_fridge_with_ten_apples_when_get_one_apple_then_apple_is_returned_and_fridge_is_not_empty() {
            // Given
            fridge.with(TEN_APPLES);

            // When
            Food food = fridge.getSome(AN_APPLE.name, AN_APPLE.quantity);

            // Then
            assertThat(food).isEqualTo(AN_APPLE);
            assertThat(fridge.getEmptySpace()).isEqualTo(MAX_SPACE - TEN_APPLES.quantity + AN_APPLE.quantity);
            assertThat(fridge.listFood()).isNotEmpty();
        }
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
