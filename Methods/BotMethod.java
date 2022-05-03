package ru.telebot.Methods;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.telebot.DataClass.User;
import ru.telebot.State.BotState;

public abstract class BotMethod {
     BotState botState;
        public abstract BotApiMethod response(User user, Message message);
        public abstract BotState getstate() ;
        public abstract String getCommand();
}


