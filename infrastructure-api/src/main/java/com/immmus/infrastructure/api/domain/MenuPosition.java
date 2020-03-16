package com.immmus.infrastructure.api.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuPosition implements Position {

    private double price;
    private String name;
    private Category category;
    private String description;

    @Override
    public double price() {
        return this.price;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public Category category() {
        return this.category;
    }

    @Override
    public String description() {
        return this.description;
    }

    public static Position.Builder<MenuPosition> builder() {
        return new PositionBuilder();
    }

    public final static class PositionBuilder implements Position.Builder<MenuPosition> {
        private double price;
        private String name;
        private Category category;
        private String description;

        @Override
        public Builder<? extends Position> price(double price) {
            this.price = price;
            return this;
        }

        @Override
        public Builder<? extends Position> name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public Builder<? extends Position> category(Category category) {
            this.category = category;
            return this;
        }

        @Override
        public Builder<? extends Position> description(String description) {
            this.description = description;
            return this;
        }

        @Override
        public MenuPosition create() {
            return new MenuPosition(price, name, category, description);
        }
    }
}
