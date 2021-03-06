package com.bruno.telegram_bot.currencybot.service;


import com.bruno.telegram_bot.currencybot.data.Currency;
import com.bruno.telegram_bot.currencybot.http.HttpCurrencyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.json.JSONObject;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class CurrencyService {

    private final List<Currency> currencies;
    private final HttpCurrencyRequest httpCurrencyRequest;
    private final String apiToken;

    @Autowired
    public CurrencyService(List<Currency> currencies, HttpCurrencyRequest httpCurrencyRequest, @Qualifier("getApiToken") String apiToken) {
        this.currencies = currencies;
        this.httpCurrencyRequest = httpCurrencyRequest;
        this.apiToken = apiToken;
    }

    public JSONObject getLastCurrencies() throws IOException, InterruptedException {
        StringBuilder currenciesURLParameter = new StringBuilder();
        for(Currency item : currencies){
            currenciesURLParameter.append(item);
            if(currencies.indexOf(item) != currencies.size()-1)
                currenciesURLParameter.append(",");

        }
        String uri = "http://api.exchangeratesapi.io/v1/latest?" +
                "access_key="+apiToken+"&" +
                "symbols="+currenciesURLParameter+"&format=1";

       return httpCurrencyRequest.apiRequest(uri);
    }
}
