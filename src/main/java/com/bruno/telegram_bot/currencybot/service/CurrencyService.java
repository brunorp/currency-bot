package com.bruno.telegram_bot.currencybot.service;

import com.bruno.telegram_bot.currencybot.configuration.CurrencyApiConfig;
import com.bruno.telegram_bot.currencybot.data.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class CurrencyService {

    private final List<Currency> currencies;
    private final CurrencyApiConfig currencyApiConfig;

    @Autowired
    public CurrencyService(List<Currency> currencies, CurrencyApiConfig currencyApiConfig) {
        this.currencies = currencies;
        this.currencyApiConfig = currencyApiConfig;
    }

    public JSONObject getLastCurrencies() throws IOException, InterruptedException, ExecutionException {
        StringBuilder currenciesURLParameter = new StringBuilder();
        for(Currency item : currencies){
            currenciesURLParameter.append(item);
            if(currencies.indexOf(item) != currencies.size()-1)
                currenciesURLParameter.append(",");

        }
        String uri = "http://api.exchangeratesapi.io/v1/latest?" +
                "access_key="+currencyApiConfig.getApiToken()+"&" +
                "symbols="+currenciesURLParameter+"&format=1";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("accept", "application/json")
                .build();

        var response = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(3))
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());

        return new JSONObject(response.body());
    }

    @Override
    public String toString() {
        try {
            var res = getLastCurrencies();
            return "Base currecy: "+res.getString("base")+". Currencies: "+res.getJSONObject("rates");
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return "error";
        }
    }
}
