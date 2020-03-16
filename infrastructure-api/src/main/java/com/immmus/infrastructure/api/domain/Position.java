package com.immmus.infrastructure.api.domain;

public interface Position {
    double price();

    String name();

    Category category();

    String description();

    enum Category {
        FOOD, BAR, OTHER
    }

    interface Builder<P extends Position> {
        Builder<? extends Position> price(double price);
        Builder<? extends Position> name(String name);
        Builder<? extends Position> category(Category name);
        Builder<? extends Position> description(String description);
        P create();
    }
}
