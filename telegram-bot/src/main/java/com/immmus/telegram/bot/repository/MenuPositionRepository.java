package com.immmus.telegram.bot.repository;

import com.immmus.infrastructure.api.domain.Position;
import com.immmus.infrastructure.api.domain.menu.CommonPosition;

import java.util.List;

public interface MenuPositionRepository {
    List<CommonPosition> getMenu();
}