package ru.telebot.Facade;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import ru.telebot.DataClass.User;
import ru.telebot.Handler.Handler;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramFacade {

    Handler handler;

    @Autowired
    public TelegramFacade(Handler handler) {
        this.handler = handler;
    }

    public BotApiMethod<?> onWebhookUpdateReceived(Update update) throws TelegramApiRequestException {


            if (update.getMessage() != null && update.getMessage().hasText()) {
                long chat_id = update.getMessage().getChatId();
                long user_tel_id = update.getMessage().getFrom().getId();
                User user=new User();
                user.setChatTelId(chat_id);
                user.setUserTelId(user_tel_id);
                Message message = update.getMessage();
                if (message.hasText()) {
                    return handler.handle(user,message);
                }
            }
            return null;





    }
}