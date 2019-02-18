package com.notatracer.data.mock.demo;

import com.notatracer.data.TradeDataUtil;
import com.notatracer.data.model.*;
import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.abstraction.MockUnit;
import net.andreinc.mockneat.abstraction.MockUnitInt;
import net.andreinc.mockneat.unit.seq.IntSeq;
import net.andreinc.mockneat.unit.seq.LongSeq;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

/**
 * Demonstrate mocking complex objects using the pretty cool mockneat library.
 */
public class BuildObjects {

    public static void main(String[] args) {
        BuildObjects buildObjects = new BuildObjects();
        MockNeat mock = MockNeat.threadLocal();

        buildObjects.simple(mock);
    }

    private void simple(MockNeat mock) {

        IntSeq seqTradeSeqNum = mock.intSeq().start(100000);
        LongSeq seqId = mock.longSeq().start(1);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/YYYY");
        LocalDate maxDateInTheFuture = LocalDate.of(2025, 12, 31);

        MockUnitInt strikePriceDollarRange = mock.ints().range(0, 100);
        MockUnitInt tradePriceDollarRange = mock.ints().range(0, 100);
        MockUnitInt centsRange = mock.ints().range(0, 99);


        MockUnit<Strike> strikeGenerator =
                mock.filler(Strike::new)
                .setter(Strike::setSymbol, mock.fromStrings(TradeDataUtil.SYMBOLS))
                .setter(Strike::setCallPut, mock.from(CallPut.values()))

                .setter(Strike::setExpirationDate, mock.localDates()
                        .future(maxDateInTheFuture)
                        .mapToString((d) -> d.format(dtf))
                        )
                .setter(Strike::setStrikePrice, mock.fmt("#{dollars}.#{cents}").param("dollars", strikePriceDollarRange).param("cents", centsRange));

        MockUnit<TradeSide> buySideGenerator =
                mock.filler(TradeSide::new)
                    .constant(TradeSide::setSide, BuySell.Buy)
                    .setter(TradeSide::setAccount, mock.ints().range(1, 999).mapToString());

        MockUnit<TradeSide> sellSideGenerator =
                mock.filler(TradeSide::new)
                        .constant(TradeSide::setSide, BuySell.Sell)
                        .setter(TradeSide::setAccount, mock.ints().range(1, 999).mapToString());

        MockUnit<Trade> tradeGenerator =
                mock.filler(Trade::new)
                .setter(Trade::setTradeSequenceNumber, seqTradeSeqNum)
                .setter(Trade::setId, seqId)
                .setter(Trade::setQuantity, mock.ints().range(1, 5000))
                .setter(Trade::setTradePrice, mock.fmt("#{dollars}.#{cents}").param("dollars", tradePriceDollarRange).param("cents", centsRange))
                .setter(Trade::setStrike, strikeGenerator)
                .setter(Trade::setBuySide, buySideGenerator)
                .setter(Trade::setSellSide, sellSideGenerator);

        IntStream.range(1, 10)
                .forEach(x ->
                    System.out.println(tradeGenerator.get())
        );
    }
}
