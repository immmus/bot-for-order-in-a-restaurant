package com.immmus.infrastructure.api.service;

import com.immmus.infrastructure.api.domain.Position;
import com.immmus.infrastructure.api.domain.menu.MenuContext;
import com.immmus.infrastructure.api.domain.menu.CommonPosition;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.immmus.infrastructure.api.utils.MsgFormatUtil.getLocalizedMessage;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuContextService implements ContextService<MenuContext> {
    private MenuContext context;

    //TODO подумать каким образом с помощью этого метода будет обновляться контекст
    @Override
    public void loadState(MenuContext context) {
        this.context = context;
    }

    //TODO сделать так чтобы контекст был неизменяемым за пределами сервиса
    @Override
    public MenuContext getContext() {
        return this.context;
    }

    public String positionsToString() {
        return positionsToString(getContext().viewPositions(), defaultFormat());
    }

    public String positionsToString(Function<CommonPosition, String> format) {
        return positionsToString(getContext().viewPositions(), format);
    }

    public String positionsToString(Position.Category byCategory) {
        return positionsToString(getContext().viewPositions(byCategory), defaultFormat());
    }

    public String positionsToString(Position.Category byCategory, Function<CommonPosition, String> format) {
        return positionsToString(getContext().viewPositions(byCategory), format);
    }

    private String positionsToString(List<CommonPosition> positions, Function<CommonPosition, String> format) {
        if (positions != null && positions.size() > 0) {
            var n = System.lineSeparator();
            return positions.stream().map(format).collect(Collectors.joining(n + n));
        } else {
            return null;
        }
    }

    public Function<CommonPosition, String> defaultFormat() {
        return defaultFormat(Language.ENGLISH);
    }

    public Function<CommonPosition, String> defaultFormat(Language language) {
        return pos ->
                        getLocalizedMessage("message.nameandprice." + language.code, pos.name().toUpperCase(), pos.price()) +
                        System.lineSeparator() +
                        getLocalizedMessage("message.composition." + language.code, pos.toStringComposition()) +
                        System.lineSeparator() +
                        getLocalizedMessage("message.description." + language.code, pos.description());
    }

    public enum Language {
        RUSSIAN("rus"),
        ENGLISH("eng");

        /** ISO 639-2 */
        public String code;
        Language(String code) {
            this.code = code;
        }
    }
}
