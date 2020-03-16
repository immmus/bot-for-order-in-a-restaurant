package com.immmus.infrastructure.api.domain;

import java.util.List;
import java.util.stream.Collectors;

public interface Menu<P extends Position> {

    /** @return list with all menu positions **/
    List<P> viewPositions();

    /**
     * Return all by category {@link Position.Category}
     * @param category menu which need for view
     * @return list filtered by category
     * */
    default List<P> viewPositionsByCategory(final Position.Category category) {
        return viewPositions()
                .stream()
                .filter(pos -> pos.category().equals(category))
                .collect(Collectors.toList());
    }
}
