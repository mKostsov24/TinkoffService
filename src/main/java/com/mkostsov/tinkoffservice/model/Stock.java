package com.mkostsov.tinkoffservice.model;

import com.mkostsov.tinkoffservice.model.enums.CurrencyEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor
@Builder
public class Stock {
    String ticker;
    String figi;
    String name;
    String type;
    CurrencyEnum currencyEnum;
    String source;
}
