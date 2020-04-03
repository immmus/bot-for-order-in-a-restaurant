package com.immmus.infrastructure.api.domain.menu;

import com.immmus.infrastructure.api.domain.Context;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

//TODO доделать логику контекста
@RequiredArgsConstructor
public class MenuContext implements Context, Menu<CommonPosition> {
    private final List<CommonPosition> positions;

    @Override
    public List<CommonPosition> viewPositions() {
        return Collections.unmodifiableList(positions);
    }
}
