package com.immmus.infrastructure.api.domain;

import com.immmus.infrastructure.api.BaseTestData;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MenuTest {

    @Test
    public void viewPositionsByCategory() {
        final Menu<Position> menu = BaseTestData.menu();
        final List<Position> barPositions = menu.viewPositions(Position.Category.BAR);

        assertThat(barPositions).isEqualTo(List.of(BaseTestData.VINE_POSITION));
    }
}
