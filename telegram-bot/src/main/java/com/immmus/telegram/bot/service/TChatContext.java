package com.immmus.telegram.bot.service;

import com.immmus.infrastructure.api.domain.Menu;
import com.immmus.infrastructure.api.domain.Position;
import com.immmus.infrastructure.api.service.ChatContext;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.MessageFormat;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TChatContext implements ChatContext {
    private Menu<Position> menu = null;


    @Override
    public void loadState(Menu<Position> menu) {
        this.menu = menu;
    }

    public Menu<Position> getMenu() {
        return this.menu;
    }

    public String positionsToString() {
        return positionsToString(getMenu().viewPositions(), defaultFormat());
    }

    public String positionsToString(Function<Position, String> format) {
        return positionsToString(getMenu().viewPositions(), format);
    }

    public String positionsToString(Position.Category byCategory) {
        return positionsToString(getMenu().viewPositions(byCategory), defaultFormat());
    }

    public String positionsToString(Position.Category byCategory, Function<Position, String> format) {
        return positionsToString(getMenu().viewPositions(byCategory), format);
    }

    private String positionsToString(List<Position> positions, Function<Position, String> format) {
        if (positions != null && positions.size() > 0) {
            return positions.stream().map(format).collect(Collectors.joining("\n\n"));
        } else {
            return null;
        }
    }

    final Function<Position, String> defaultFormat() {
        final var nameAndPrice = new MessageFormat("<i>{0}</i> - цена {1}");
        final var composition = new MessageFormat("\n<u>Состав</u>: {0}");
        final var description = new MessageFormat("\n<u>Описание</u>: {0}");

        return pos ->
                        MessageFormat.format(nameAndPrice.toPattern(), pos.name().toUpperCase(), pos.price()) +
                        MessageFormat.format(composition.toPattern(), pos.toStringComposition()) +
                        MessageFormat.format(description.toPattern(), pos.description());
    }
}
