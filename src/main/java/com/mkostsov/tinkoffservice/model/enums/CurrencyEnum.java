package com.mkostsov.tinkoffservice.model.enums;

public enum CurrencyEnum {
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

    CurrencyEnum(String currency) {
        this.currency = currency;
    }
}
