import io.ExchangeParser;
import model.Bank;
import model.ExchangeRate;
import service.AnalyticsService;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        ExchangeParser exchangeParser = new ExchangeParser();
        AnalyticsService analyticsService = new AnalyticsService();

        List<Bank> banks = exchangeParser.parseBanksFromFile("banks.txt");
        List<ExchangeRate> rates = exchangeParser.parseExchangeRatesFromFile("test1.txt");

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
    }
}
