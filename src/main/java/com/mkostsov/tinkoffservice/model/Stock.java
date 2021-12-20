package com.mkostsov.tinkoffservice.model;

import com.mkostsov.tinkoffservice.model.enums.Currency;
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
    Currency currency;
    String source;
}
