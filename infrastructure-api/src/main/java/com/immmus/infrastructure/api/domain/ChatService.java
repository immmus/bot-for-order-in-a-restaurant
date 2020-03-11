package com.immmus.infrastructure.api.domain;


import java.lang.reflect.Constructor;

public interface ChatService {

    static <CS extends ChatService> CS createChatService(final Menu<Position> menu, final Class<CS> classService) {
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
