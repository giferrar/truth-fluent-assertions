package org.example.kitchen;

import org.example.kitchen.exception.FridgeFullException;
import org.example.kitchen.exception.NotEnoughFoodException;
import org.example.kitchen.model.Food;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FridgeTest {

    private final IFridge fridge = new Fridge();

    @BeforeEach
    public void setup() {
        fridge.empty();
    }

    @Test
    public void emptyTest() {
        fridge.empty();
        assertTrue(fridge.getEmptySpace() == 100);
        assertTrue(fridge.listFood().isEmpty());
    }

    @Test
    public void notEmptyTest() {
        fridge.put(new Food("apple", 1));
        assertFalse(fridge.getEmptySpace() == 100);

        List<String> listedFood = fridge.listFood();

        assertTrue(listedFood.size() == 1);
        assertTrue(listedFood.contains("apple"));
    }

    @Test
    public void putAndPutAndKOPutTest() {
        fridge.put(new Food("apple", 1));
        assertEquals(99, fridge.getEmptySpace());
        assertEquals(1, fridge.listFood().size());
        assertEquals("apple", fridge.listFood().get(0));

        fridge.put(new Food("banana", 1));
        assertEquals(98, fridge.getEmptySpace());
        assertEquals(2, fridge.listFood().size());
        assertTrue(fridge.listFood().contains("banana"));

        try {
            fridge.put(new Food("carrot", 100));
            fail("Expected FridgeFullException");
        } catch (FridgeFullException e) {
            assertEquals("Not enough space left in fridge", e.getMessage());
        }
    }

    @Test
    public void putTwiceTest() {
        fridge.put(new Food("apple", 1));
        assertEquals(99, fridge.getEmptySpace());
        assertEquals(1, fridge.listFood().size());
        assertEquals("apple", fridge.listFood().get(0));

        fridge.put(new Food("apple", 1));
        assertEquals(98, fridge.getEmptySpace());
        assertEquals(1, fridge.listFood().size());
        assertEquals("apple", fridge.listFood().get(0));
    }

    @Test
    public void getAllTest() {
        fridge.put(new Food("apple", 10));
        assertEquals(90, fridge.getEmptySpace());
        assertEquals(1, fridge.listFood().size());
        assertEquals("apple", fridge.listFood().get(0));

        Optional<Food> food = fridge.getAll("apple");
        assertTrue(food.isPresent());
        assertEquals(10, food.get().quantity);
        assertEquals(100, fridge.getEmptySpace());
        assertEquals(0, fridge.listFood().size());
    }

    @Test
    public void getAllTwiceTest() {
        fridge.put(new Food("apple", 10));

        for (int i = 0; i < 2; i++) {
            Optional<Food> allApples = fridge.getAll("apple");
            assertTrue(fridge.getEmptySpace() == 100);
            if(i == 0) {
                assertTrue(allApples.isPresent());
                assertEquals(10, allApples.get().quantity);
            } else {
                assertFalse(allApples.isPresent());
            }
        }
    }

    @Test
    public void getSomeTest() {
        fridge.put(new Food("apple", 11));
        assertEquals(89, fridge.getEmptySpace());
        assertEquals(1, fridge.listFood().size());
        assertEquals("apple", fridge.listFood().get(0));

        Food food = fridge.getSome("apple", 10);
        assertEquals(10, food.quantity);
        assertEquals(99, fridge.getEmptySpace());
        assertEquals(1, fridge.listFood().size());

        try {
            fridge.getSome("apple", 11);
            fail("Expected NotEnoughFoodException");
        } catch (NotEnoughFoodException e) {
            assertEquals("Not enough apple in fridge", e.getMessage());
        }
    }

}