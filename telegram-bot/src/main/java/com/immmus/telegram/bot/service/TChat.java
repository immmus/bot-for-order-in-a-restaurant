package com.immmus.telegram.bot.service;

import com.immmus.infrastructure.api.domain.ChatService;
import com.immmus.infrastructure.api.domain.Menu;
import com.immmus.infrastructure.api.domain.Position;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TChat implements ChatService {

    @Override
    public void loadState(Menu<Position> menu) {
        final List<Position> positions = menu.viewPositions();
    }
}
