package com.immmus.infrastructure.api.domain;

import java.util.List;

public interface Order<P extends Position> {
    void addPosition(P position);
    void removePosition(P position);
    List<P> viewAddedPositions();

    public interface Builder<O extends Order> {
        public O create();
    }
}
