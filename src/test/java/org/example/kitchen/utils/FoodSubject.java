package org.example.kitchen.utils;

import com.google.common.truth.FailureMetadata;
import com.google.common.truth.Subject;
import com.google.common.truth.Truth;
import org.example.kitchen.model.Food;

public class FoodSubject extends Subject {

    private final Food actual;

    // Constructor where we set the actual food to test
    public FoodSubject(FailureMetadata failureMetadata, Food actual) {
        super(failureMetadata, actual);
        this.actual = actual;
    }

    // Method to create the factory necessary to set the object type in truth
    private static Subject.Factory<FoodSubject, Food> getFactory() {
        return FoodSubject::new;
    }

    // Entry point for the assertion
    public static FoodSubject assertThatFood(Food actual) {
        return Truth.assertAbout(FoodSubject.getFactory()).that(actual);
    }

    public FoodSubject hasName(String expectedName) {
        check("name").that(actual.name).isEqualTo(expectedName);
        return this;
    }

    public FoodSubject hasQuantity(int expectedQuantity) {
        check("quantity").that(actual.quantity).isEqualTo(expectedQuantity);
        return this;
    }

}