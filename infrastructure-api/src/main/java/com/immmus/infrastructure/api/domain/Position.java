package com.immmus.infrastructure.api.domain;

import com.immmus.infrastructure.api.repository.HasId;
import lombok.NonNull;

public interface Position extends HasId {

    double price();

    String name();

    Category category();

    String description();

    enum Category {
        FOOD, BAR, OTHER
    }

    interface Builder<P extends Position> {
        Builder<? extends Position> price(double price);
        Builder<? extends Position> name(@NonNull String name);
        Builder<? extends Position> category(@NonNull Category name);
        Builder<? extends Position> description(@NonNull String description);
        P create();
    }
}
