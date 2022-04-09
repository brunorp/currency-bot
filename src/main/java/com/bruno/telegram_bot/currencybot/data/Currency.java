package com.bruno.telegram_bot.currencybot.data;

public enum Currency {
    USD("United States Dollar"), EUR("Euro"), BRL("Brazilian Real");

    private final String description;

    Currency(String description) {
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
