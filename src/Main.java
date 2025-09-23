import io.ExchangeParser;
import model.Bank;
import model.ExchangeQuote;
import model.ExchangeRate;
import model.ExchangeTransaction;
import service.AnalyticsService;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public class Main {
  public static void main(String[] args) throws IOException, ParseException {
    ExchangeParser exchangeParser = new ExchangeParser();
    AnalyticsService analyticsService = new AnalyticsService();

    List<Bank> banks = exchangeParser.parseBanksFromFile("banks.txt");
    List<ExchangeRate> rates = exchangeParser.parseExchangeRatesFromFile("test1.txt");
    List<ExchangeQuote> quotes = exchangeParser.parseQuotesFromFile("quotes.txt", banks);

    System.out.println("[БАНКИ]");
    banks.forEach(System.out::println);

    System.out.println("\n[КУРСЫ ВАЛЮТ]:");
    rates.forEach(System.out::println);

    Optional<Bank> topBank = analyticsService.findBankWithMostClients(banks);
    System.out.println("\n[Банк с наибольщим кол-вом клиентов]");
    topBank.ifPresent(System.out::println);

    Optional<ExchangeRate> minRate = analyticsService.findMinRate(rates);
    System.out.println("\n[Минимальный курс]");
    minRate.ifPresent(System.out::println);

    System.out.println("\n[КОТИРОВКИ]");
    quotes.forEach(System.out::println);

    Optional<ExchangeQuote> bestQuote = analyticsService.findBestQuote(quotes);
    System.out.println("\n[ЛУЧШАЯ КОТИРОВКА (минимальный курс c комиссией)]");
    bestQuote.ifPresent(System.out::println);

    if (bestQuote.isPresent()) {
      ExchangeTransaction trn = new ExchangeTransaction(bestQuote.get(), 150.00);
      System.out.println("\n[ПРИМЕР ТРАНЗАКЦИИ]");
      System.out.println(trn);
    }
  }
}
