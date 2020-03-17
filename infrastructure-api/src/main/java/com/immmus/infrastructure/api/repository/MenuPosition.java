package com.immmus.infrastructure.api.repository;

import com.immmus.infrastructure.api.domain.Position;
import lombok.NonNull;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@ToString
public class MenuPosition extends AbstractBaseEntity implements Position {
    private double price;
    private String name;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String description;
    private String composition;
    transient private String[] ingredients;

    protected MenuPosition() { }

    private MenuPosition(final double price,
                         final String name,
                         final Category category,
                         final String description,
                         final String[] ingredientsComposition) {

        this.price = price;
        this.name = name;
        this.category = category;
        this.description = description;
        this.ingredients = ingredientsComposition;
        this.composition = Position.toStringComposition(ingredientsComposition);
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public String[] ingredients() {
        return ingredients;
    }

    public static Position.Builder<MenuPosition> builder() {
        return new PositionBuilder();
    }

    public final static class PositionBuilder implements Position.Builder<MenuPosition> {
        private double price;
        private String name;
        private Category category;
        private String description;
        private String[] ingredients;

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
        public Builder<? extends Position> composition(@NonNull String... ingredients) {
            this.ingredients = ingredients;
            return this;
        }

        @Override
        public MenuPosition create() {
            return new MenuPosition(price, name, category, description, ingredients);
        }
    }
}