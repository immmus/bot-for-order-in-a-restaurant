package com.immmus.infrastructure.api.service;

import com.immmus.infrastructure.api.domain.Menu;
import com.immmus.infrastructure.api.domain.MenuPosition;
import com.immmus.infrastructure.api.domain.Position;
import com.immmus.infrastructure.api.domain.TestMenu;
import org.junit.Test;

import static com.immmus.infrastructure.api.domain.Position.Builder;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

public class ChatServiceTest {

    @Test
    public void createServiceTest() {
        Builder<MenuPosition> positionBuilder = MenuPosition.builder();
        List<Position> positions = List.of(
                positionBuilder.name("Суп").price(21.50).create(),
                positionBuilder.name("Мясо").price(130).create());

        Menu<Position> menu = new TestMenu(positions);

        final TestChatService service = ChatService.createService(menu, TestChatService.class);
        final List<Position> currentMenuPositions = service.getCurrentMenuPositions();

        assertThat(positions).isEqualTo(currentMenuPositions);
    }
}
