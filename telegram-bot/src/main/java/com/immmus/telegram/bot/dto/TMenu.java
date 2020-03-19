package com.immmus.telegram.bot.dto;

import com.immmus.infrastructure.api.domain.Menu;
import com.immmus.infrastructure.api.domain.Position;

import java.util.Collections;
import java.util.List;

public class TMenu implements Menu<Position> {
    private final List<Position> menuPositions;

    public TMenu(List<Position> menuPositions) {
        this.menuPositions = menuPositions;
    }

    @Override
    public List<Position> viewPositions() {
        return Collections.unmodifiableList(menuPositions);
    }
}