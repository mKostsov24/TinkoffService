package com.mkostsov.tinkoffservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@AllArgsConstructor
@Value
@Builder
public class StockPrice {
    String figi;
    Double price;
}
