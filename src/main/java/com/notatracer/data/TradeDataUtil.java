package com.notatracer.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notatracer.data.model.*;
import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.abstraction.MockUnit;
import net.andreinc.mockneat.abstraction.MockUnitInt;
import net.andreinc.mockneat.unit.seq.IntSeq;
import net.andreinc.mockneat.unit.seq.LongSeq;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TradeDataUtil {
    public static List<String> SYMBOLS = Arrays.asList("AAPL", "BUD", "CAT", "GILD", "IBM", "YHOO");

    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/YYYY");
    static LocalDate maxDateInTheFuture = LocalDate.of(2025, 12, 31);

    public static MockUnit<Strike> getStrikeGenerator(MockNeat mock) {
        MockUnitInt strikePriceDollarRange = mock.ints().range(0, 100);
        MockUnitInt centsRange = mock.ints().range(0, 99);

        return
                mock.filler(Strike::new)
                        .setter(Strike::setSymbol, mock.fromStrings(TradeDataUtil.SYMBOLS))
                        .setter(Strike::setCallPut, mock.from(CallPut.values()))

                        .setter(Strike::setExpirationDate, mock.localDates()
                                .future(maxDateInTheFuture)
                                .mapToString((d) -> d.format(dtf))
                        )
                        .setter(Strike::setStrikePrice, mock.fmt("#{dollars}.#{cents}").param("dollars", strikePriceDollarRange).param("cents", centsRange));
    }

    public static MockUnit<TradeSide> getBuySideGenerator(MockNeat mock) {
        return
                mock.filler(TradeSide::new)
                        .constant(TradeSide::setSide, BuySell.Buy)
                        .setter(TradeSide::setAccount, mock.ints().range(1, 999).mapToString());
    }

    public static MockUnit<TradeSide> getSellSideGenerator(MockNeat mock) {
        return
                mock.filler(TradeSide::new)
                        .constant(TradeSide::setSide, BuySell.Sell)
                        .setter(TradeSide::setAccount, mock.ints().range(1, 999).mapToString());
    }

    public static MockUnit<Trade> getTradeGenerator(MockNeat mock) {

        IntSeq seqTradeSeqNum = mock.intSeq().start(100000);
        LongSeq seqId = mock.longSeq().start(1);
        MockUnitInt tradePriceDollarRange = mock.ints().range(0, 100);
        MockUnitInt centsRange = mock.ints().range(0, 99);

        return
                mock.filler(Trade::new)
                        .setter(Trade::setTradeSequenceNumber, seqTradeSeqNum)
                        .setter(Trade::setId, seqId)
                        .setter(Trade::setQuantity, mock.ints().range(1, 5000))
                        .setter(Trade::setTradePrice, mock.fmt("#{dollars}.#{cents}").param("dollars", tradePriceDollarRange).param("cents", centsRange))
                        .setter(Trade::setStrike, getStrikeGenerator(mock))
                        .setter(Trade::setBuySide, getBuySideGenerator(mock))
                        .setter(Trade::setSellSide, getSellSideGenerator(mock));
    }

    public static List<Trade> getNTrades(int number) {
        MockNeat mock = MockNeat.threadLocal();
        MockUnit<Trade> tradeGenerator = getTradeGenerator(mock);
        return IntStream.range(1, number)
                .mapToObj(x -> tradeGenerator.get())
                .collect(
                        Collectors.toList()
                );
    }

    public static Trade getTrade() {
        MockNeat mock = MockNeat.threadLocal();
        return getTradeGenerator(mock).get();
    }

    public static List<Map> getNTradeMaps(int number) {
        MockNeat mock = MockNeat.threadLocal();
        MockUnit<Trade> tradeGenerator = getTradeGenerator(mock);
        ObjectMapper mapper = new ObjectMapper();
        List<Map> tradeListOfMaps = IntStream.range(1, number)
                .mapToObj(
                        x -> tradeGenerator.get()
                )
                .map(t ->  {
                    return mapper.convertValue(t, Map.class);
                })
                .collect(Collectors.toList());
        return tradeListOfMaps;
    }

    public static void main(String[] args) {
//        MockNeat mock = MockNeat.threadLocal();

        getNTradeMaps(20).stream().forEach(
                t -> System.out.println(t)
        );

    }
}
