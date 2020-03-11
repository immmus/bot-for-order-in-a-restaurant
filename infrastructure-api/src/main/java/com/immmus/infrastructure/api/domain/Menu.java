package com.immmus.infrastructure.api.domain;

import java.util.List;

public interface Menu<P extends Position> {
    List<P> viewPositions();
}
