package com.bruno.telegram_bot.currencybot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:telegram.properties")
public class TelegramConfig {

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;

    @Value("${bot.chat.id}")
    private String botChatId;

    public String getBotChatId() {
        return botChatId;
    }

    public String getBotToken() {
        return botToken;
    }

    public String getBotName() {
        return botName;
    }


}
