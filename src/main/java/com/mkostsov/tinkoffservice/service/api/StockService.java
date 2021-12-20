package com.mkostsov.tinkoffservice.service.api;


import com.mkostsov.tinkoffservice.dto.FigiesDto;
import com.mkostsov.tinkoffservice.dto.StocksDto;
import com.mkostsov.tinkoffservice.dto.StocksPricesDto;
import com.mkostsov.tinkoffservice.dto.TickersDto;
import com.mkostsov.tinkoffservice.model.Stock;

public interface StockService {
    Stock getStockByTicker(String ticker);
    StocksDto getStocksByTickers(TickersDto tickers);
    StocksPricesDto getPricesStocksByFigies(FigiesDto figiesDto);
}
