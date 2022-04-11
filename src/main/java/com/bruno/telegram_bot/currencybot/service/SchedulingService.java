package com.bruno.telegram_bot.currencybot.service;

import com.bruno.telegram_bot.currencybot.bot.CurrencyBot;
import com.bruno.telegram_bot.currencybot.configuration.TelegramConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Service
public class SchedulingService {

    private final CurrencyBot currencyBot;
    private final Logger logger = LoggerFactory.getLogger(SchedulingService.class);
    @Value("${bot.chat.id}")
    private String chatId;

    @Autowired
    public SchedulingService(CurrencyBot currencyBot) {
        this.currencyBot = currencyBot;
    }

    // 9:00 every day
    @Scheduled(cron = "0 0 9 * * *", zone = "America/Sao_Paulo")
    private void notifyWithCurrenciesMorning() throws IOException, ExecutionException, InterruptedException {
        currencyBot.sendCurrenciesTo(chatId);
    }

    // 21:00 every day
    @Scheduled(cron = "0 10 21 * * *", zone = "America/Sao_Paulo")
    private void notifyWithCurrenciesEvening() throws IOException, ExecutionException, InterruptedException {
        currencyBot.sendCurrenciesTo(chatId);
    }

    @Scheduled(cron = "* * * * * *", zone = "America/Sao_Paulo")
    private void healthCheck(){
    }

}
