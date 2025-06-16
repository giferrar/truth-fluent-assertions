package org.example.kitchen.utils;

import com.google.common.truth.FailureMetadata;
import com.google.common.truth.Subject;
import com.google.common.truth.Truth;
import org.example.kitchen.Fridge;
import org.jspecify.annotations.Nullable;

public class TestableFridgeSubject extends Subject {

    private final Fridge actual;

    protected TestableFridgeSubject(FailureMetadata metadata, @Nullable Object actual) {
        super(metadata, actual);
        this.actual = (Fridge) actual;
    }

    private static Subject.Factory<TestableFridgeSubject, TestableFridge> getFactory() {
        return TestableFridgeSubject::new;
    }

    public static TestableFridgeSubject assertThatFridge(TestableFridge actual) {
        return Truth.assertAbout(TestableFridgeSubject.getFactory()).that(actual);
    }

    public void isEmpty() {
        check("empty space").that(actual.getEmptySpace()).isEqualTo(100);
        check("list food").that(actual.listFood()).isEmpty();
    }
}
