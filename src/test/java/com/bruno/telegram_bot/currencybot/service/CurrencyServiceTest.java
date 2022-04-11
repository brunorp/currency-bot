package com.bruno.telegram_bot.currencybot.service;

import com.bruno.telegram_bot.currencybot.data.Currency;
import com.bruno.telegram_bot.currencybot.http.HttpCurrencyRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {

    @Mock
    private HttpCurrencyRequest httpCurrencyRequest;
    private static CurrencyService currencyService;

    @BeforeEach
    void setUp() {
        List<Currency> currencies = Arrays.asList(Currency.BRL, Currency.USD);
        currencyService = new CurrencyService(currencies, httpCurrencyRequest, "1234");
    }


    @Test
    void getLastCurrencies() throws IOException, InterruptedException, JSONException {
        JSONObject json_resp = new JSONObject("{'id': 'teste'}");
        when(httpCurrencyRequest.apiRequest("http://api.exchangeratesapi.io/v1/latest?access_key=1234&symbols=BRL,USD&format=1"))
                .thenReturn(json_resp);

        var response = currencyService.getLastCurrencies();

        verify(httpCurrencyRequest, times(1))
                .apiRequest("http://api.exchangeratesapi.io/v1/latest?access_key=1234&symbols=BRL,USD&format=1");
        assertEquals(response, json_resp);
    }

}