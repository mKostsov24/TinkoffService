package com.mkostsov.tinkoffservice.service.impl;

import com.mkostsov.tinkoffservice.dto.*;
import com.mkostsov.tinkoffservice.exception.StockNotFoundException;
import com.mkostsov.tinkoffservice.model.Stock;
import com.mkostsov.tinkoffservice.model.enums.CurrencyEnum;
import com.mkostsov.tinkoffservice.service.api.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.tinkoff.invest.openapi.OpenApi;
import ru.tinkoff.invest.openapi.model.rest.MarketInstrumentList;
import ru.tinkoff.invest.openapi.model.rest.Orderbook;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TinkoffStockService implements StockService {
    private final OpenApi openApi;

    @Async
    public CompletableFuture<MarketInstrumentList> getMarketInstrumentTicker(String ticker) {
        log.info("Getting {} from Tinkoff", ticker);
        var context = openApi.getMarketContext();
        return context.searchMarketInstrumentsByTicker(ticker);
    }

    @Override
    public Stock getStockByTicker(String ticker) {
        var cfList = getMarketInstrumentTicker(ticker);
        var list = cfList.join().getInstruments();
        if (list.isEmpty()) {
            throw new StockNotFoundException(String.format("Stock %S not found,", ticker));
        }
        var item = list.get(0);
        return Stock.builder()
                .ticker(item.getTicker())
                .figi(item.getFigi())
                .name(item.getName())
                .type(item.getType().getValue())
                .currencyEnum(CurrencyEnum.valueOf(item.getCurrency().getValue()))
                .source("TINKOFF")
                .build();
    }

    @Override
    public StocksDto getStocksByTickers(TickersDto tickers) {
        var marketInstrumentList = new ArrayList<CompletableFuture<MarketInstrumentList>>();
        tickers.getTickers().forEach(ticker -> marketInstrumentList.add(getMarketInstrumentTicker(ticker)));
        List<Stock> stocks = marketInstrumentList.stream()
                .map(CompletableFuture::join)
                .map(mi -> {
                    if (!mi.getInstruments().isEmpty()) {
                        return mi.getInstruments().get(0);
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .map(mi -> Stock.builder()
                        .ticker(mi.getTicker())
                        .figi(mi.getFigi())
                        .name(mi.getName())
                        .type(mi.getType().getValue())
                        .currencyEnum(CurrencyEnum.valueOf(mi.getCurrency().getValue()))
                        .source("TINKOFF")
                        .build()
                )
                .collect(Collectors.toList());
        return StocksDto.builder().stocks(stocks).build();
    }

    @Async
    public CompletableFuture<Optional<Orderbook>> getOrderBookByFigi(String figi) {
        var orderbook = openApi.getMarketContext().getMarketOrderbook(figi,0);
        log.info("Getting price {} from Tinkoff", figi);
        return orderbook;
    }

    @Override
    public StocksPricesDto getPricesStocksByFigies(FigiesDto figiesDto) {
        List<CompletableFuture<Optional<Orderbook>>> orderBooks = new ArrayList<>();
        figiesDto.getFigies().forEach(figi -> orderBooks.add(getOrderBookByFigi(figi)));
        List<StockPrice> prices =  orderBooks.stream()
                .map(CompletableFuture::join)
                .map(oo -> oo.orElseThrow(() -> new StockNotFoundException("Stock not found.")))
                .map(orderBook -> StockPrice.builder()
                        .figi(orderBook.getFigi())
                        .price(orderBook.getLastPrice().doubleValue())
                        .build())
                .collect(Collectors.toList());
        return new StocksPricesDto(prices);
    }

}
