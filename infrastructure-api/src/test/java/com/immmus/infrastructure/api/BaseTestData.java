package com.immmus.infrastructure.api;

import com.immmus.infrastructure.api.domain.Menu;
import com.immmus.infrastructure.api.domain.MenuForTest;
import com.immmus.infrastructure.api.repository.MenuPosition;
import com.immmus.infrastructure.api.domain.Position;

import java.util.List;

public class BaseTestData {
    static final Position.Builder<MenuPosition> POSITION_BUILDER = MenuPosition.builder();
    public static final Position SOUP_POSITION = POSITION_BUILDER.name("Soup").price(21.50).category(Position.Category.FOOD).create();
    public static final Position MEAT_POSITION = POSITION_BUILDER.name("Meat").price(130).category(Position.Category.FOOD).create();
    public static final Position VINE_POSITION = POSITION_BUILDER.name("Vine").price(130).category(Position.Category.BAR).create();

    public static final List<Position> POSITIONS  = List.of(SOUP_POSITION, MEAT_POSITION, VINE_POSITION);

    public static Menu<Position> menu() { return new MenuForTest(POSITIONS); }
}