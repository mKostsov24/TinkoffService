package com.mkostsov.tinkoffservice.dto;

import com.mkostsov.tinkoffservice.model.Stock;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StocksDto {
    List<Stock> stocks;
}
