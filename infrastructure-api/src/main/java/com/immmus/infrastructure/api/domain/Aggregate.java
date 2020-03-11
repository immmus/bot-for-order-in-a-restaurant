package com.immmus.infrastructure.api.domain;

import java.util.function.BiFunction;

public interface Aggregate<E extends AggregateEvent> {
    Aggregate<E> identity();

    BiFunction<Aggregate<E>, E, Aggregate<E>> accumulatorFunction();
}
