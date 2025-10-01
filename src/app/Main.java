package app;

import io.ExchangeParser;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import model.Bank;
import model.ExchangeQuote;
import model.ExchangeRate;
import model.ExchangeTransaction;
import service.AnalyticsService;

public class Main {
  private static final Scanner IN = new Scanner(System.in);
  private static List<ExchangeRate> rates = new ArrayList<>();

  public static void main(String[] args) throws IOException, ParseException {
    ExchangeParser parser = new ExchangeParser();
    AnalyticsService analytics = new AnalyticsService();

    rates = parser.parseExchangeRatesFromFile("test1.txt");
    List<Bank> banks = parser.parseBanksFromFile("banks.txt");
    List<ExchangeQuote> quotes = parser.parseQuotesFromFile("quotes.txt", banks);

    printInitial(banks, rates, quotes, analytics);
    loopMenu();
  }

  private static void printInitial(
      List<Bank> banks,
      List<ExchangeRate> exchangeRates,
      List<ExchangeQuote> quotes,
      AnalyticsService analytics) {
    System.out.println("[БАНКИ]");
    banks.forEach(System.out::println);
    System.out.println("\n[КУРСЫ ВАЛЮТ]");
    exchangeRates.forEach(System.out::println);
    analytics
        .findBankWithMostClients(banks)
        .ifPresent(x -> System.out.println("\n[Банк с наибольшим кол-вом клиентов]\n" + x));
    analytics
        .findMinRate(exchangeRates)
        .ifPresent(x -> System.out.println("\n[Минимальный курс]\n" + x));
    System.out.println("\n[КОТИРОВКИ]");
    quotes.forEach(System.out::println);
    analytics.findBestQuote(quotes).ifPresent(Main::demoTransaction);
  }

  private static void demoTransaction(ExchangeQuote quote) {
    ExchangeTransaction trn = new ExchangeTransaction(quote, 150.0);
    System.out.println("\n[ЛУЧШАЯ КОТИРОВКА (мин. курс с комиссией)]\n" + quote);
    System.out.println("\n[ПРИМЕР ТРАНЗАКЦИИ]\n" + trn);
  }

  private static void loopMenu() throws IOException, ParseException {
    while (true) {
      printMenu();
      int choice = readInt();
      if (choice == 0) {
        return;
      }
      handleChoice(choice);
    }
  }

  private static void printMenu() {
    System.out.println(
        "\n--- ГЛАВНОЕ МЕНЮ ---\n1. Удалить курс\n2. Добавить курс\n3. Показать все курсы\n0. Выход");
    System.out.print("Выберите действие: ");
  }

  private static int readInt() {
    try {
      return Integer.parseInt(IN.nextLine().trim());
    } catch (Exception e) {
      System.out.println("Ошибка: введите число!");
      return -1;
    }
  }

  private static void handleChoice(int choice) throws IOException, ParseException {
    switch (choice) {
      case 1 -> deleteExchangeRate();
      case 2 -> addExchangeRate();
      case 3 -> showAllExchangeRates();
      default -> System.out.println("Неверный выбор!");
    }
  }

  private static void deleteExchangeRate() {
    if (rates.isEmpty()) {
      System.out.println("Список пуст!");
      return;
    }
    showAllExchangeRates();
    System.out.print("Номер курса для удаления: ");
    int idx = readInt();
    if (idx >= 1 && idx <= rates.size()) System.out.println("Удалён: " + rates.remove(idx - 1));
    else System.out.println("Неверный номер!");
  }

  private static void addExchangeRate() throws IOException, ParseException {
    System.out.print("Код валюты 1 (USD): ");
    String c1 = IN.nextLine().trim().toUpperCase();
    System.out.print("Код валюты 2 (EUR): ");
    String c2 = IN.nextLine().trim().toUpperCase();
    System.out.print("Курс: ");
    double rate = Double.parseDouble(IN.nextLine().trim());
    System.out.print("Дата (yyyy.MM.dd): ");
    Date d = new SimpleDateFormat("yyyy.MM.dd").parse(IN.nextLine().trim());
    rates.add(new ExchangeRate(c1, c2, rate, d));
    System.out.println("Добавлен: " + rates.getLast());
  }

  private static void showAllExchangeRates() {
    System.out.println("\n--- ВСЕ КУРСЫ ВАЛЮТ (" + rates.size() + ") ---");
    for (int i = 0; i < rates.size(); i++) System.out.println((i + 1) + ". " + rates.get(i));
  }
}
