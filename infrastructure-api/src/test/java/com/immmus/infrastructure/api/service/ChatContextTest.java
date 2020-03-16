package com.immmus.infrastructure.api.service;

import com.immmus.infrastructure.api.BaseTestData;
import com.immmus.infrastructure.api.domain.Menu;
import com.immmus.infrastructure.api.domain.Position;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ChatContextTest {

    @Test
    public void createServiceTest() {
        final Menu<Position> menu = BaseTestData.menu();
        final ChatContextForTest service = ChatContext.createContext(menu, ChatContextForTest.class);
        final List<Position> currentMenuPositions = service.getCurrentMenuPositions();

        assertThat(BaseTestData.POSITIONS).isEqualTo(currentMenuPositions);
    }
}
