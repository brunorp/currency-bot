package com.bruno.telegram_bot.currencybot.bot;

import com.bruno.telegram_bot.currencybot.service.CurrencyService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.concurrent.ExecutionException;

@Component
public class CurrencyBot extends TelegramLongPollingBot {
    private final CurrencyService currencyService;
    private final String botName;
    private final String botToken;

    @Autowired
    public CurrencyBot(
            CurrencyService currencyService,
            @Qualifier("getBotName") String botName,
            @Qualifier("getBotToken") String botToken) {
        this.currencyService = currencyService;
        this.botName = botName;
        this.botToken = botToken;
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {

    }

    public void sendMessageTo(String message, String chatId) {
        SendMessage msg = new SendMessage();
        msg.enableMarkdown(true);
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
        double brl = res.getJSONObject("rates").getDouble("BRL");
        double usd = res.getJSONObject("rates").getDouble("USD");

        String message = "|  *Date:* "+LocalDate.now(ZoneId.of("America/Sao_Paulo"))+"  |\n\n" +
                "*Base currency:* "+res.getString("base")+".\n\n"+
                "|  *Currencies*  |\n" +
                "*BRL:* "+brl+".\n" +
                "*USD:* "+usd+".";
        sendMessageTo(message, chatId);
    }
}
