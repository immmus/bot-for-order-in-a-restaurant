package com.immmus.telegram.bot.service;

import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

public class Sender extends SilentSender {
    public Sender(MessageSender sender) {
        super(sender);
    }

    @Override
    public Optional<Message> send(String message, long id) {
        return this.doSendMessage(message, id, false);
    }

    @Override
    public Optional<Message> sendMd(String message, long id) {
        return this.doSendMessage(message, id, true);
    }

    private Optional<Message> doSendMessage(String txt, long groupId, boolean format) {
        SendMessage smsg = new SendMessage();
        smsg.setChatId(groupId);
        smsg.setText(txt);
        smsg.enableMarkdown(format);
        smsg.enableMarkdownV2(format);
        smsg.enableHtml(format);

        return execute(smsg);
    }
}
