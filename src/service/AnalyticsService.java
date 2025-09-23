package service;

import model.Bank;
import model.ExchangeQuote;
import model.ExchangeRate;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class AnalyticsService {
    public Optional<Bank> findBankWithMostClients(List<Bank> banks) {
        return banks.stream().max(Comparator.comparingLong(Bank::getClientCount));
    }

    public Optional<ExchangeRate> findMinRate(List<ExchangeRate> rates) {
        return rates.stream().min(Comparator.comparing(ExchangeRate::getRate));
    }

    public Optional<ExchangeQuote> findBestQuote(List<ExchangeQuote> quotes) {
        return quotes.stream().min(Comparator.comparing(ExchangeQuote::rateWithCommission));
    }
}
