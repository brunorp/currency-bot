package com.bruno.telegram_bot.currencybot.service;

import com.bruno.telegram_bot.currencybot.bot.CurrencyBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Service
public class SchedulingService {

    private final CurrencyBot currencyBot;
    private final String chatId;
    private final Logger logger = LoggerFactory.getLogger(SchedulingService.class);

    @Autowired
    public SchedulingService(CurrencyBot currencyBot, @Qualifier("getBotChatId") String chatId) {
        this.currencyBot = currencyBot;
        this.chatId = chatId;
    }

    // 9:00 every day
    @Scheduled(cron = "0 0 9 * * *", zone = "America/Sao_Paulo")
    private void notifyWithCurrenciesMorning() throws IOException, ExecutionException, InterruptedException {
        logger.info("Sending morning message.");
        currencyBot.sendCurrenciesTo(chatId);
    }

    // 21:00 every day
    @Scheduled(cron = "0 16 22 * * *", zone = "America/Sao_Paulo")
    private void notifyWithCurrenciesEvening() throws IOException, ExecutionException, InterruptedException {
        logger.info("Sending night message.");
        currencyBot.sendCurrenciesTo(chatId);
    }

}
