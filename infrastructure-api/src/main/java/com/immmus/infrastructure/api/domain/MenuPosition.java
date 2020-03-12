package com.immmus.infrastructure.api.domain;

public class MenuPosition implements Position {

    @Override
    public double price() {
        return 0;
    }

    @Override
    public String name() {
        return null;
    }

    public static Position.Builder<MenuPosition> builder() {
        return new PositionBuilder();
    }

    public final static class PositionBuilder implements Position.Builder<MenuPosition> {

        @Override
        public Builder<? extends Position> price(double price) {
            return null;
        }

        @Override
        public Builder<? extends Position> name(String name) {
            return null;
        }

        @Override
        public MenuPosition create() {
            return null;
        }
    }
}
