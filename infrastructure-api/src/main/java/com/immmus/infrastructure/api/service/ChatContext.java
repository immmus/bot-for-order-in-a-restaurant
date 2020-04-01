package com.immmus.infrastructure.api.service;


import com.immmus.infrastructure.api.domain.Menu;
import com.immmus.infrastructure.api.domain.Position;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.immmus.infrastructure.api.utils.MsgFormatUtil.getLocalizedMessage;

public interface ChatContext {

    static <CS extends ChatContext> CS createContext(final Menu<Position> menu, final Class<CS> classService) {
        try {
            final Constructor<CS> ctor = classService.getDeclaredConstructor();
            ctor.setAccessible(true);
            final CS chatService = ctor.newInstance();
            chatService.loadState(menu);
            return chatService;
        } catch (final Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    void loadState(Menu<Position> menu);

    Menu<Position> getMenu();

    enum Language {
        RUSSIAN("rus"),
        ENGLISH("eng");

        /** ISO 639-2 */
        public String code;
        Language(String code) {
            this.code = code;
        }
    }

    default String positionsToString() {
        return positionsToString(getMenu().viewPositions(), defaultFormat());
    }

    default String positionsToString(Function<Position, String> format) {
        return positionsToString(getMenu().viewPositions(), format);
    }

    default String positionsToString(Position.Category byCategory) {
        return positionsToString(getMenu().viewPositions(byCategory), defaultFormat());
    }

    default String positionsToString(Position.Category byCategory, Function<Position, String> format) {
        return positionsToString(getMenu().viewPositions(byCategory), format);
    }

    private String positionsToString(List<Position> positions, Function<Position, String> format) {
        if (positions != null && positions.size() > 0) {
            return positions.stream().map(format).collect(Collectors.joining("\n\n"));
        } else {
            return null;
        }
    }

    default Function<Position, String> defaultFormat() {
        return defaultFormat(Language.ENGLISH);
    }
    default Function<Position, String> defaultFormat(Language language) {
        return pos ->
                        getLocalizedMessage("message.nameandprice." + language.code, pos.name().toUpperCase(), pos.price()) +
                        System.lineSeparator() +
                        getLocalizedMessage("message.composition." + language.code, pos.toStringComposition()) +
                        System.lineSeparator() +
                        getLocalizedMessage("message.description." + language.code, pos.description());
    }
}
