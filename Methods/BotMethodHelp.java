package ru.telebot.Methods;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import ru.telebot.DataClass.User;
import ru.telebot.State.BotState;

import java.io.Serializable;
import java.util.List;

import static ru.telebot.State.BotState.Help;

@Component
public class BotMethodHelp extends BotMethod {
    private final BotState botState= Help;
    private final String command ="/help";
    @Override
    public BotApiMethod response(User user, Message message){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(user.getChatTelId().toString());
        sendMessage.setText("this help method");
        return sendMessage;
    }

    @Override
    public BotState getstate() {
        return botState;
    }

    @Override
    public String getCommand() {
        return command;
    }


}
