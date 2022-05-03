package ru.telebot.Cache;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.telebot.Methods.BotMethod;
import ru.telebot.State.BotState;


import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Component
public class MethodCache {

    private Map<BotState, BotMethod> mapMethods;
    public BotMethod getBotMethodCommand(BotState botState){
        return mapMethods.get(botState);
    }
    public Map<BotState, BotMethod> getMapMethods(){
        return mapMethods;
    }

    @Bean
    public Map<BotState, BotMethod> setMapMethods(@NonNull Collection<BotMethod> hashMap) {
        System.out.println(hashMap);
        this.mapMethods=hashMap.stream()
                .collect(toMap(BotMethod::getstate, Function.identity()));
        return hashMap.stream()
                .collect(toMap(BotMethod::getstate, Function.identity()));
    }
}
