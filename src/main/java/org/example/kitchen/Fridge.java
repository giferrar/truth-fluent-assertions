package org.example.kitchen;

import org.example.kitchen.exception.FridgeFullException;
import org.example.kitchen.exception.NotEnoughFoodException;
import org.example.kitchen.model.Food;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Fridge implements IFridge {

    protected static final int MAX_SPACE = 100;

    private int emptySpace = MAX_SPACE;

    private final ArrayList<Food> content = new ArrayList<>();


    @Override
    public void put(Food food) throws FridgeFullException {
        if (emptySpace < food.quantity) {
            throw new FridgeFullException("Not enough space left in fridge");
        }
        content.stream()
                .filter(f -> f.name.equals(food.name))
                .findFirst()
                .ifPresentOrElse(
                        f -> f.quantity += food.quantity,
                        () -> content.add(new Food(food.name, food.quantity))
                );
        emptySpace -= food.quantity;
    }

    @Override
    public Optional<Food> getAll(String foodName) {
        Optional<Food> optionalFood = content.stream().filter(f -> f.name.equals(foodName)).findFirst();
        if (optionalFood.isPresent()) {
            content.removeIf(food -> food.name.equals(foodName));
            emptySpace += optionalFood.get().quantity;
        }
        return optionalFood;
    }

    @Override
    public Food getSome(String foodName, int quantity) throws NotEnoughFoodException {
        content.stream().filter(f -> f.name.equals(foodName)).findFirst().ifPresentOrElse(f -> {
            if (f.quantity < quantity) {
                throw new NotEnoughFoodException("Not enough " + foodName + " in fridge");
            }
            f.quantity -= quantity;
        }, () -> {
            throw new NotEnoughFoodException("Not enough " + foodName + " in fridge");
        });
        content.removeIf(food -> food.name.equals(foodName) && food.quantity == 0);
        emptySpace += quantity;
        return new Food(foodName, quantity);
    }

    @Override
    public List<String> listFood() {
        return content.stream().map(f -> f.name).toList();
    }

    @Override
    public int getEmptySpace() {
        return emptySpace;
    }

    @Override
    public void empty() {
        content.clear();
        emptySpace = MAX_SPACE;
    }

    protected void setContent(ArrayList<Food> content) {
        this.empty();
        content.forEach(this::put);
    }
}
