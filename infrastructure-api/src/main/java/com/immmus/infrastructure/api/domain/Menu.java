package com.immmus.infrastructure.api.domain;

import java.util.List;
import java.util.stream.Collectors;

public interface Menu<P extends Position> {

    /**
     * @return list with all menu positions
     **/
    List<P> viewPositions();


    /**
     * Return all positions by category {@link Position.Category}
     *
     * @param byCategory menu which need for view
     * @return list filtered by category
     */
    default List<P> viewPositions(final Position.Category byCategory) {
            if (Position.Category.ALL != byCategory && byCategory != null) {
                return viewPositions()
                        .stream()
                        .filter(pos -> pos.category().equals(byCategory))
                        .collect(Collectors.toList());
            } else {
                return viewPositions();
            }
        }
}
