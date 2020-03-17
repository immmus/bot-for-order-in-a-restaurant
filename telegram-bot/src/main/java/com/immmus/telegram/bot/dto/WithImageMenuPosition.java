package com.immmus.telegram.bot.dto;

import com.immmus.infrastructure.api.repository.MenuPosition;

import java.io.File;

public class WithImageMenuPosition {
    private File image;
    private MenuPosition menuPosition;

    public WithImageMenuPosition(File image, MenuPosition menuPosition) {
        this.image = image;
        this.menuPosition = menuPosition;
    }

    public MenuPosition getPosition() {
        return this.menuPosition;
    }

    public File getImage() { return image; }
}