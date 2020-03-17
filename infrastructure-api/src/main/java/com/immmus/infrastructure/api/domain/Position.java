package com.immmus.infrastructure.api.domain;

import lombok.NonNull;

import java.util.StringJoiner;

public interface Position extends HasId {
    String COMPOSITION_DELIMITER = ", ";

    double price();

    String name();

    Category category();

    String description();

    String[] ingredients();

    default String toStringComposition() {
        return toStringComposition(ingredients());
    }

    static String toStringComposition(String... ingredients) {
        if(ingredients == null || ingredients.length == 0) return "absent";

        StringJoiner j = new StringJoiner(COMPOSITION_DELIMITER);
        for (String ingredient: ingredients) j.add(ingredient);
        return j.toString();
    }

    enum Category {
        ALL, FOOD, BAR, OTHER
    }

    interface Builder<P extends Position> {
        Builder<? extends Position> price(double price);
        Builder<? extends Position> name(@NonNull String name);
        Builder<? extends Position> category(@NonNull Category name);
        Builder<? extends Position> description(@NonNull String description);
        Builder<? extends Position> composition(@NonNull String... ingredients);
        P create();
    }
}