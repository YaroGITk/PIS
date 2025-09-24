import io.ExchangeParser;
import model.Bank;
import model.ExchangeQuote;
import model.ExchangeRate;
import model.ExchangeTransaction;
import service.AnalyticsService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
  private static List<ExchangeRate> rates;
  private static Scanner input = new Scanner(System.in);

  public static void main(String[] args) throws IOException, ParseException {
    ExchangeParser exchangeParser = new ExchangeParser();
    AnalyticsService analyticsService = new AnalyticsService();

    List<Bank> banks = exchangeParser.parseBanksFromFile("banks.txt");
    rates = exchangeParser.parseExchangeRatesFromFile("test1.txt");
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

    showCurrencyManagementMenu();
  }

  private static void showCurrencyManagementMenu() {
    List<String[]> pages = new ArrayList<>();
    pages.add(new String[]{
            "--- ГЛАВНОЕ МЕНЮ ---",
            "1. Удалить курс валюты",
            "2. Добавить курс валюты",
            "3. Показать все курсы валют",
            "0. Выход"
    });

    int currentPage = 0;
    int choice;

    do {
      System.out.println(String.join("\n", pages.get(currentPage)));
      System.out.print("Выберите действие: ");

      try {
        choice = input.nextInt();
        input.nextLine();

        switch (choice) {
          case 1:
            deleteExchangeRate();
            break;
          case 2:
            addExchangeRate();
            break;
          case 3:
            showAllExchangeRates();
            break;
          case 0:
            System.out.println("Выход из программы...");
            return;
          default:
            System.out.println("Неверный выбор! Попробуйте снова.");
        }

        System.out.println("\nНажмите Enter для продолжения...");
        input.nextLine();

      } catch (InputMismatchException e) {
        System.out.println("Ошибка: введите число!");
        input.nextLine();
        choice = -1;
      }

    } while (true);
  }

  private static void deleteExchangeRate() {
    if (rates.isEmpty()) {
      System.out.println("Список курсов валют пуст!");
      return;
    }

    System.out.println("\n--- УДАЛЕНИЕ КУРСА ВАЛЮТЫ ---");
    showAllExchangeRates();

    System.out.print("Введите номер курса для удаления: ");
    try {
      int index = input.nextInt();
      input.nextLine();

      if (index >= 1 && index <= rates.size()) {
        ExchangeRate removedRate = rates.remove(index - 1);
        System.out.println("Курс удален: " + removedRate);
      } else {
        System.out.println("Неверный номер курса!");
      }
    } catch (InputMismatchException e) {
      System.out.println("Ошибка: введите число!");
      input.nextLine();
    }
  }

  private static void addExchangeRate() {
    System.out.println("\n--- ДОБАВЛЕНИЕ НОВОГО КУРСА ВАЛЮТЫ ---");

    try {
      System.out.print("Введите код валюты (например, USD, EUR): ");
      String currency1 = input.nextLine().toUpperCase();

      System.out.print("Введите второй код валюты (например, USD, EUR): ");
      String currency2 = input.nextLine();

      System.out.print("Введите курс для валюты: ");
      String exchangeRateString = input.nextLine();
      double exchangeRate = Double.parseDouble(exchangeRateString);

      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
      System.out.print("Введите дату (2000.00.00): ");
      Date date = dateFormat.parse(input.nextLine());

      ExchangeRate newRate = new ExchangeRate(currency1, currency2, exchangeRate, date);
      rates.add(newRate);

      System.out.println("Новый курс добавлен: " + newRate);

    } catch (InputMismatchException e) {
      System.out.println("Ошибка: неверный формат числа!");
      input.nextLine();
    } catch (ParseException e) {
        throw new RuntimeException(e);
    }
  }

  private static void showAllExchangeRates() {
    System.out.println("\n--- ВСЕ КУРСЫ ВАЛЮТ (" + rates.size() + ") ---");

    if (rates.isEmpty()) {
      System.out.println("Список курсов валют пуст.");
      return;
    }

    for (int i = 0; i < rates.size(); i++) {
      System.out.println((i + 1) + ". " + rates.get(i));
    }
  }
}