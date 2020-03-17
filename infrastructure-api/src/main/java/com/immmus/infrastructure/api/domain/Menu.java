package com.immmus.infrastructure.api.domain;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

public interface Menu<P extends Position> {
    MessageFormat DEFAULT_MENU_FORMAT = new MessageFormat("{0} - price {1} \nDescription: {2}");

    /**
     * @return list with all menu positions
     **/
    List<P> viewPositions();

    /**
     * Return all by category {@link Position.Category}
     *
     * @param category menu which need for view
     * @return list filtered by category
     */
    default List<P> viewPositionsByCategory(final Position.Category category) {
        return viewPositions()
                .stream()
                .filter(pos -> pos.category().equals(category))
                .collect(Collectors.toList());
    }

    default String positionsToString() {
        return positionsToString(DEFAULT_MENU_FORMAT);
    }

    default String positionsToString(MessageFormat format) {
        return viewPositions()
                .stream()
                .map(pos ->
                        MessageFormat.format(format.toPattern(),
                                pos.name().toUpperCase(),
                                pos.price(),
                                pos.description()))
                .collect(Collectors.joining("\n\n"));
    }
}
