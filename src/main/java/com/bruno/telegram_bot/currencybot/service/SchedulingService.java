package com.bruno.telegram_bot.currencybot.service;

import com.bruno.telegram_bot.currencybot.bot.CurrencyBot;
import com.bruno.telegram_bot.currencybot.http.HttpCurrencyRequest;
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
    private final HttpCurrencyRequest httpCurrencyRequest;
    private final String chatId;
    private final String appUrl;
    private final Logger logger = LoggerFactory.getLogger(SchedulingService.class);

    @Autowired
    public SchedulingService(
            CurrencyBot currencyBot,
            HttpCurrencyRequest httpCurrencyRequest,
            @Qualifier("getBotChatId") String chatId,
            @Qualifier("getAppUrl") String appUrl) {
        this.currencyBot = currencyBot;
        this.httpCurrencyRequest = httpCurrencyRequest;
        this.chatId = chatId;
        this.appUrl = appUrl;
    }

    // 9:00 every day
    @Scheduled(cron = "0 0 9 * * *", zone = "America/Sao_Paulo")
    private void notifyWithCurrenciesMorning() throws IOException, ExecutionException, InterruptedException {
        logger.info("Sending morning message.");
        currencyBot.sendCurrenciesTo(chatId);
    }

    // 21:00 every day
    @Scheduled(cron = "0 0 21 * * *", zone = "America/Sao_Paulo")
    private void notifyWithCurrenciesEvening() throws IOException, ExecutionException, InterruptedException {
        logger.info("Sending night message.");
        currencyBot.sendCurrenciesTo(chatId);
    }

    //health check
    @Scheduled(cron = "* * * * * *", zone = "America/Sao_Paulo")
    private void healthCheck() throws IOException, InterruptedException {
        var response = httpCurrencyRequest.apiRequest(appUrl+"/actuator/health");
        logger.info("Health check: " + response.getString("Status"));
    }

}
