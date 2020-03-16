package com.immmus.telegram.bot.service;

import com.immmus.infrastructure.api.domain.Menu;
import com.immmus.infrastructure.api.domain.Position;
import com.immmus.infrastructure.api.service.ChatContext;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TChatContext implements ChatContext {

    private Menu<Position> menu = null;

    @Override
    public void loadState(Menu<Position> menu) {
        this.menu = menu;
    }


}
