package com.immmus.infrastructure.api.service;

import com.immmus.infrastructure.api.domain.Menu;
import com.immmus.infrastructure.api.domain.Position;

import java.util.Collections;
import java.util.List;

public class TestChatService implements ChatService {
    private List<Position> currentMenuPositions = null;

    @Override
    public void loadState(Menu<Position> menu) {
        final List<Position> positions = menu.viewPositions();
        positions.forEach(System.out::println);
        this.currentMenuPositions = Collections.unmodifiableList(positions);
    }

    public List<Position> getCurrentMenuPositions() {
        return currentMenuPositions;
    }
}
