package com.bruno.telegram_bot.currencybot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CurrencyApiConfig {
    @Value("${currency.api.token}")
    private String apiToken;

    public String getApiToken() {
        return apiToken;
    }


}
