package com.notatracer.data.mock.demo;

import net.andreinc.mockneat.MockNeat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * Exploring the mockneat API.
 */
public class SimpleAPI {

    public static List<String> SYMBOLS = Arrays.asList("AAPL", "BUD", "CAT", "GILD", "IBM", "YHOO");

    public static void main(String[] args) {
        MockNeat mock = MockNeat.threadLocal();

        SimpleAPI tutorial = new SimpleAPI();
        tutorial.simple(mock);

        tutorial.randomStringOfSet(mock);
        tutorial.randomPrices(mock);
        tutorial.randomDates(mock);
        tutorial.randomAccounts(mock);
    }

    private void randomAccounts(MockNeat mock) {
        System.out.println(String.format("%03d", mock.ints().range(1, 999).get()));
        System.out.println(String.format("%03d", mock.ints().range(1, 999).get()));
        System.out.println(String.format("%03d", mock.ints().range(1, 999).get()));
        System.out.println(String.format("%03d", mock.ints().range(1, 999).get()));
        System.out.println(String.format("%03d", mock.ints().range(1, 999).get()));
    }

    private void randomDates(MockNeat mock) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/YYYY");
        LocalDate maxDateInTheFuture = LocalDate.of(2025, 12, 31);
        LocalDate inTheFuture = mock.localDates()
                .future(maxDateInTheFuture)
                .get();
        System.out.println(inTheFuture.format(dtf));
    }

    private void randomPrices(MockNeat mock) {
        Integer dollars = mock.ints().range(0, 100).get();
        Integer cents = mock.ints().range(0, 99).get();
        System.out.println(String.format("%d.%02d", dollars, cents));
    }


    private void randomStringOfSet(MockNeat mock) {
        System.out.println(mock.fromStrings(SYMBOLS).get());
        System.out.println(mock.fromStrings(SYMBOLS).get());
        System.out.println(mock.fromStrings(SYMBOLS).get());
        System.out.println(mock.fromStrings(SYMBOLS).get());
    }

    private void simple(MockNeat mock) {
        // Gets a random int, long and string

        int x = mock.ints().get();
        long y = mock.longs().get();
        String s = mock.strings().get();

        System.out.println(x);
        System.out.println(y);
        System.out.println(s);
    }

}
