package org.example.kitchen.model;

public class Food implements Comparable<Food> {
    public String name;
    public int quantity;

    public Food(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return quantity == food.quantity && name.equals(food.name);
    }

    @Override
    public int compareTo(Food other) {
        return this.name.compareToIgnoreCase(other.name);
    }
}
