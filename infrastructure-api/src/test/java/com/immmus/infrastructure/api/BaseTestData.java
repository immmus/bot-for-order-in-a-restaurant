package com.immmus.infrastructure.api;

import com.immmus.infrastructure.api.domain.menu.Menu;
import com.immmus.infrastructure.api.domain.menu.CommonPosition;
import com.immmus.infrastructure.api.domain.Position;
import com.immmus.infrastructure.api.domain.menu.MenuContext;

import java.util.List;

public class BaseTestData {
    static final CommonPosition.Builder POSITION_BUILDER = CommonPosition.builder();
    public static final CommonPosition SOUP_POSITION = POSITION_BUILDER.name("Soup").price(21.50).category(Position.Category.FOOD).create();
    public static final CommonPosition MEAT_POSITION = POSITION_BUILDER.name("Meat").price(130).category(Position.Category.FOOD).create();
    public static final CommonPosition VINE_POSITION = POSITION_BUILDER.name("Vine").price(130).category(Position.Category.BAR).create();

    public static final List<CommonPosition> POSITIONS  = List.of(SOUP_POSITION, MEAT_POSITION, VINE_POSITION);

    public static Menu<CommonPosition> menu() { return new MenuContext(POSITIONS); }
}