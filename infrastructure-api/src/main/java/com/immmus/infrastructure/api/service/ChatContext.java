package com.immmus.infrastructure.api.service;


import com.immmus.infrastructure.api.domain.Menu;
import com.immmus.infrastructure.api.domain.Position;

import java.lang.reflect.Constructor;

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
}
