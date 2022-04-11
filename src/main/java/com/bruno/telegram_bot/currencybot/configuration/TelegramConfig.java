package com.bruno.telegram_bot.currencybot.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
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

    @Bean(name = "getBotChatId")
    public String getBotChatId() {
        return botChatId;
    }

    @Bean(name="getBotToken")
    public String getBotToken() {
        return botToken;
    }

    @Bean(name="getBotName")
    public String getBotName() {
        return botName;
    }


}
