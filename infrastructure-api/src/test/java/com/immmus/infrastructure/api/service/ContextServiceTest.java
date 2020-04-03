package com.immmus.infrastructure.api.service;

import com.immmus.infrastructure.api.domain.menu.CommonPosition;
import com.immmus.infrastructure.api.domain.menu.MenuContext;
import org.junit.Test;

import java.util.List;

import static com.immmus.infrastructure.api.BaseTestData.POSITIONS;
import static org.assertj.core.api.Assertions.assertThat;

public class ContextServiceTest {

    @Test
    public void createServiceTest() {
        MenuContext context = new MenuContext(POSITIONS);
        final MenuContextService service = ContextService.create(context, MenuContextService.class);
        assertThat(context).isEqualTo(service.getContext());

        final List<CommonPosition> currentMenuPositions = service.getContext().viewPositions();
        assertThat(POSITIONS).isEqualTo(currentMenuPositions);
    }
}
