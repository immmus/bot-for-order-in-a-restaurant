package com.immmus.infrastructure.api.service;


import com.immmus.infrastructure.api.domain.Context;

import java.lang.reflect.Constructor;

public interface ContextService<Cxt extends Context> {

    static <CS extends ContextService<Cxt>, Cxt extends Context>
    CS create(final Cxt context, final Class<CS> classService) {
        try {
            final Constructor<CS> ctor = classService.getDeclaredConstructor();
            ctor.setAccessible(true);
            final CS chatService = ctor.newInstance();
            chatService.loadState(context);
            return chatService;
        } catch (final Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    void loadState(Cxt context);

    Cxt getContext();
}
