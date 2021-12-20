package com.mkostsov.tinkoffservice.controller;

import com.mkostsov.tinkoffservice.dto.FigiesDto;
import com.mkostsov.tinkoffservice.dto.StocksDto;
import com.mkostsov.tinkoffservice.dto.StocksPricesDto;
import com.mkostsov.tinkoffservice.dto.TickersDto;
import com.mkostsov.tinkoffservice.model.Stock;
import com.mkostsov.tinkoffservice.service.api.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;

    @GetMapping("/stocks/{ticker}")
    public Stock getStock(@PathVariable String ticker) {
        return stockService.getStockByTicker(ticker);
    }

    @PostMapping("/stocks/getStocksByTickers")
    public StocksDto getStocksByTickers(@RequestBody TickersDto tickers) {
        return stockService.getStocksByTickers(tickers);
    }

    @PostMapping("/prices")
    public StocksPricesDto getPricesStocksByFigies(@RequestBody FigiesDto figiesDto) {
        return stockService.getPricesStocksByFigies(figiesDto);
    }

}
