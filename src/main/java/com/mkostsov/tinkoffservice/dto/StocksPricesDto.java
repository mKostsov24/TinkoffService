package com.mkostsov.tinkoffservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@AllArgsConstructor
@Value
@Builder
public class StocksPricesDto {
    List<StockPrice> prices;
}
