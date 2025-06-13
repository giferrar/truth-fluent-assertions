package org.example.kitchen;

import org.example.kitchen.exception.FridgeFullException;
import org.example.kitchen.exception.NotEnoughFoodException;
import org.example.kitchen.model.Food;

import java.util.List;
import java.util.Optional;

public interface IFridge {

    void put(Food food) throws FridgeFullException;

    Optional<Food> getAll(String foodName);

    Food getSome(String foodName, int quantity) throws NotEnoughFoodException;

    List<String> listFood();

    int getEmptySpace();

    void empty();

}
