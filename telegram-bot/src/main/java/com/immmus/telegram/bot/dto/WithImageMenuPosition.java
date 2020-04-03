package com.immmus.telegram.bot.dto;

import com.immmus.infrastructure.api.domain.menu.CommonPosition;

import java.io.File;

public class WithImageMenuPosition {
    private File image;
    private CommonPosition menuPosition;

    public WithImageMenuPosition(File image, CommonPosition menuPosition) {
        this.image = image;
        this.menuPosition = menuPosition;
    }

    public CommonPosition getPosition() {
        return this.menuPosition;
    }

    public File getImage() { return image; }
}