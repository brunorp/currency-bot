package com.bruno.telegram_bot.currencybot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("{currency.bot.url}")
    private String appUrl;

    @Bean(name="getAppUrl")
    public String getAppUrl(){
        return appUrl;
    }
}
