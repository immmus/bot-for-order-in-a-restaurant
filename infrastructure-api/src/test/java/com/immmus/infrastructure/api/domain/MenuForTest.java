package com.immmus.infrastructure.api.domain;

import java.util.Collections;
import java.util.List;

public class MenuForTest implements Menu<Position> {
    private final List<Position> menuPositions;

    public MenuForTest(List<Position> menuPositions) {
        this.menuPositions = menuPositions;
    }

    @Override
    public List<Position> viewPositions() {
        return Collections.unmodifiableList(menuPositions);
    }
}
