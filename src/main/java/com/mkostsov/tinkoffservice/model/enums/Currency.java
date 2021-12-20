package com.mkostsov.tinkoffservice.model.enums;

public enum Currency {
    RUB("RUB"),
    USD("USD"),
    EUR("EUR"),
    GBP("GBP"),
    HKO("HKD"),
    CHF("CHF"),
    JPY("JPY"),
    CNY("CNY"),
    TRY("TRY");

    private String currency;

    Currency(String currency) {
        this.currency = currency;
    }
}
