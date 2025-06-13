package org.example.kitchen.utils;

import org.example.kitchen.Fridge;
import org.example.kitchen.model.Food;

import java.util.ArrayList;
import java.util.List;

public class TestableFridge extends Fridge {

    // In a real case scenario here we could pass mocks or implement the whole thing as a factory method
    public void with(Food... foods) {
        if (foods != null && foods.length > 0) {
            this.setContent(new ArrayList<>(List.of(foods)));
        } else {
            this.empty();
        }
    }

    public static final Food AN_APPLE = new Food("apple", 1);
    public static final Food A_BANANA = new Food("banana", 1);

    public static final Food TEN_APPLES = new Food("apple", 10);
    public static final Food TEN_BANANAS = new Food("banana", 10);

    public static final Food HUNDRED_APPLES = new Food("apple", 100);

    public static final Food TOO_MUCH_FOOD = new Food("pizza", 101);
}
