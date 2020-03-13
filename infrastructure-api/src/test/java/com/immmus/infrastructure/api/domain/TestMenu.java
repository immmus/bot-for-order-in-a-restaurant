package com.immmus.infrastructure.api.domain;

import java.util.Collections;
import java.util.List;

public class TestMenu implements Menu<Position> {
    private final List<Position> menuPositions;

    public TestMenu(List<Position> menuPositions) {
        this.menuPositions = menuPositions;
    }

    @Override
    public List<Position> viewPositions() {
        return Collections.unmodifiableList(menuPositions);
    }
}
