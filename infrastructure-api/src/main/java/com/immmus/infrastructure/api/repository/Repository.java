package com.immmus.infrastructure.api.repository;

public interface Repository<T extends Aggregate, S extends AggregateState, E extends AggregateEvent, R extends RepositoryAddEvent> {
    void add(T aggregateInstance);
}
