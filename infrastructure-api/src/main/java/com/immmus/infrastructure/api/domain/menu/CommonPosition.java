package com.immmus.infrastructure.api.domain.menu;

import com.immmus.infrastructure.api.domain.AbstractBaseEntity;
import com.immmus.infrastructure.api.domain.Position;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ToString
@EqualsAndHashCode(callSuper = true)
public class CommonPosition extends AbstractBaseEntity implements Position {
    private double price;
    private String name;
    private Category category;
    private String description;
    private String composition;
    private boolean isActive;
    private List<String> ingredients;

    private CommonPosition(final Integer id,
                           final double price,
                           final String name,
                           final Category category,
                           final String description,
                           final boolean isActive,
                           final String... ingredientsComposition) {

        super(id);
        this.price = price;
        this.name = name;
        this.category = category;
        this.description = description;
        this.isActive = isActive;
        this.ingredients = Optional.ofNullable(ingredientsComposition).map(Arrays::asList).orElse(new ArrayList<>());
        this.composition = Position.toStringComposition(ingredientsComposition);
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
    public List<String> ingredients() {
        if (ingredients == null) {
            return new ArrayList<>();
        } else if (ingredients.isEmpty()) {
            if (composition == null || composition.isBlank()) {
                return ingredients;
            } else {
                return Arrays.asList(composition.split(COMPOSITION_DELIMITER));
            }
        } else return ingredients;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void deactivate() {
        this.isActive = false;
    }

    public void activate() {
        this.isActive = true;
    }

    public static Builder builder() {
        return new Builder();
    }

    public final static class Builder implements Position.Builder<CommonPosition> {
        private double price;
        private String name;
        private Category category;
        private String description;
        private boolean isActive = true;
        private String[] ingredients;


        @Override
        public Builder price(double price) {
            this.price = price;
            return this;
        }

        @Override
        public Builder name(@NonNull String name) {
            this.name = name;
            return this;
        }

        @Override
        public Builder category(@NonNull Category category) {
            this.category = category;
            return this;
        }

        @Override
        public Builder description(@NonNull String description) {
            this.description = description;
            return this;
        }

        @Override
        public Builder composition(@NonNull String... ingredients) {
            this.ingredients = ingredients;
            return this;
        }

        public Builder activate(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        @Override
        public CommonPosition create() {
            return new CommonPosition(null, price, name, category, description, isActive, ingredients);
        }
    }
}