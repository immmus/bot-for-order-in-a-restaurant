package com.immmus.infrastructure.api.domain;

public interface Position {
    double price();
    String name();

    interface Builder<P extends Position> {
        Builder<? extends Position> price(double price);
        Builder<? extends Position> name(String name);
        P create();
    }
}
