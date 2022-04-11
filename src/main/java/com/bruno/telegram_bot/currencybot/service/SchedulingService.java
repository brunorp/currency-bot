package com.bruno.telegram_bot.currencybot.service;

import com.bruno.telegram_bot.currencybot.bot.CurrencyBot;
import com.bruno.telegram_bot.currencybot.configuration.TelegramConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Service
public class SchedulingService {

    private final CurrencyBot currencyBot;
    private final TelegramConfig telegramConfig;
    private final Logger logger = LoggerFactory.getLogger(SchedulingService.class);
    @Autowired
    @Qualifier("getAppUrl")
    private String appUrl;

    @Autowired
    public SchedulingService(CurrencyBot currencyBot, TelegramConfig telegramConfig) {
        this.currencyBot = currencyBot;
        this.telegramConfig = telegramConfig;
    }

    // 9:00 every day
    @Scheduled(cron = "0 0 9 * * *", zone = "America/Sao_Paulo")
    private void notifyWithCurrenciesMorning() throws IOException, ExecutionException, InterruptedException {
        currencyBot.sendCurrenciesTo(telegramConfig.getBotChatId());
    }

    // 21:00 every day
    @Scheduled(cron = "0 10 21 * * *", zone = "America/Sao_Paulo")
    private void notifyWithCurrenciesEvening() throws IOException, ExecutionException, InterruptedException {
        currencyBot.sendCurrenciesTo(telegramConfig.getBotChatId());
    }

    @Scheduled(cron = "* * * * * *", zone = "America/Sao_Paulo")
    private void healthCheck(){
        RestTemplate restTemplate = new RestTemplate();
        var res = restTemplate.getForEntity(appUrl, String.class);
        logger.info("Health check. Status code: "+res.getStatusCode());
    }

}
