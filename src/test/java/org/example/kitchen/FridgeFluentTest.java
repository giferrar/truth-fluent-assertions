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

    }

}
