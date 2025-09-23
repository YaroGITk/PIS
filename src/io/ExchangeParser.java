package io;

import model.Bank;
import model.ExchangeQuote;
import model.ExchangeRate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExchangeParser {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

    public List<ExchangeRate> parseExchangeRatesFromFile(String filename) throws IOException, ParseException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        List<ExchangeRate> infoExchangeRatesList = new ArrayList<>();

        for(String line : lines) {
            String[] parts = line.split(" ");
            String currency1 = parts[0];
            String currency2 = parts[1];
            double exchangeRate = Double.parseDouble(parts[2]);
            Date date = dateFormat.parse(parts[3]);

            infoExchangeRatesList.add(
                    new ExchangeRate(
                            currency1,
                            currency2,
                            exchangeRate,
                            date
                    )
            );
        }

        return infoExchangeRatesList;
    }

    public List<Bank> parseBanksFromFile(String filename) throws IOException, ParseException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        List<Bank> banks = new ArrayList<>();

        for(String line : lines) {
            String[] parts = line.split(" ");
            String name = parts[0];
            long countMembers = Integer.parseInt(parts[1]);

            banks.add(
                    new Bank(
                            name,
                            countMembers
                    )
            );
        }

        return banks;
    }

    public List<ExchangeQuote> parseQuotesFromFile(String filename, List<Bank> knownBanks)
            throws IOException, ParseException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        List<ExchangeQuote> response = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(" ");
            String currency1 = parts[0];
            String currency2 = parts[1];
            double rate = Double.parseDouble(parts[2]);
            Date date = dateFormat.parse(parts[3]);
            String bankName = parts[4];
            double commission = Double.parseDouble(parts[5]);

            Bank bank = knownBanks.stream()
                    .filter(b -> b.toString().contains(bankName) || b.getClientCount() >= 0)
                    .filter(b -> b.toString().contains(bankName))
                    .findFirst()
                    .orElse(new Bank(bankName, 0));

            response.add(new ExchangeQuote(currency1, currency2, rate, date, bank, commission));
        }
        return response;
    }
}
