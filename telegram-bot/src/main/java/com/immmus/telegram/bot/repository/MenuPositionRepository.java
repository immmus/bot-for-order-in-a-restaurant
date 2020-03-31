package com.immmus.telegram.bot.repository;

import com.immmus.infrastructure.api.domain.Menu;
import com.immmus.infrastructure.api.domain.Position;

public interface MenuPositionRepository {
    Menu<Position> getMenu();
}