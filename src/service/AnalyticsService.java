package service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import model.Bank;
import model.ExchangeQuote;
import model.ExchangeRate;

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
