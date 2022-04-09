package com.bruno.telegram_bot.currencybot.bot;

import com.bruno.telegram_bot.currencybot.configuration.TelegramConfig;
import com.bruno.telegram_bot.currencybot.service.CurrencyService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Component
public class CurrencyBot extends TelegramLongPollingBot {
    private final CurrencyService currencyService;
    private final TelegramConfig telegramConfig;

    @Autowired
    public CurrencyBot(CurrencyService currencyService, TelegramConfig telegramConfig) {
        this.currencyService = currencyService;
        this.telegramConfig = telegramConfig;
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    @Override
    public String getBotUsername() {
        return telegramConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return telegramConfig.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

    }

    public void sendMessageTo(String message, String chatId) {
        SendMessage msg = new SendMessage();
        msg.setText(message);
        msg.setChatId(chatId);
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendCurrenciesTo(String chatId) throws IOException, ExecutionException, InterruptedException {
        JSONObject res = currencyService.getLastCurrencies();
        double eur = res.getJSONObject("rates").getDouble("EUR");
        double brl = res.getJSONObject("rates").getDouble("BRL");
        double usd = res.getJSONObject("rates").getDouble("USD");

        String message = "Base currecy: "+res.getString("base")+". Currencies: " +
                "EUR: "+eur+". BRL: "+brl+". USD: "+usd;
        sendMessageTo(message, chatId);
    }
}
