package ru.telebot.DataClass;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.telebot.State.BotState;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Entity
@Data
@Table(name = "\"User\"")
@FieldDefaults(level = AccessLevel.PRIVATE)

public class User {
    @Column(name = "user_bot_nick")
    String userBotNick;
    @Enumerated(EnumType.STRING)
    @Column(name = "user_state",nullable = false)
    BotState botState;
    @Column(name = "user_id",nullable = false)
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long userId;
    @Column(name = "user_tel_id",nullable = false)
    Long userTelId;
    @Column(name = "chat_tel_id",nullable = false,unique = true)
    Long chatTelId;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    List<Quiz> quizList;
    @Override
    public String toString(){
        return userBotNick;
    }
}
