package io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import model.Bank;
import model.ExchangeQuote;
import model.ExchangeRate;

public class ExchangeParser {
  private static final SimpleDateFormat DF = new SimpleDateFormat("yyyy.MM.dd", Locale.US);

  public List<ExchangeRate> parseExchangeRatesFromFile(String file) throws IOException {
    List<ExchangeRate> out = new ArrayList<>();
    for (String line : Files.readAllLines(Path.of(file))) addRate(out, line);
    return out;
  }

  public List<Bank> parseBanksFromFile(String file) throws IOException {
    List<Bank> out = new ArrayList<>();
    for (String line : Files.readAllLines(Path.of(file))) addBank(out, line);
    return out;
  }

  public List<ExchangeQuote> parseQuotesFromFile(String file, List<Bank> banks) throws IOException {
    List<ExchangeQuote> out = new ArrayList<>();
    for (String line : Files.readAllLines(Path.of(file))) addQuote(out, line, banks);
    return out;
  }

  private void addRate(List<ExchangeRate> out, String line) {
    try {
      String[] parts = line.split(" ");
      out.add(
          new ExchangeRate(parts[0], parts[1], Double.parseDouble(parts[2]), DF.parse(parts[3])));
    } catch (Exception ignored) {
    }
  }

  private void addBank(List<Bank> out, String line) {
    try {
      String[] parts = line.split(" ");
      out.add(new Bank(parts[0], Long.parseLong(parts[1])));
    } catch (Exception ignored) {
    }
  }

  private void addQuote(List<ExchangeQuote> out, String line, List<Bank> banks) {
    try {
      String[] parts = line.split(" ");
      Bank bank =
          banks.stream()
              .filter(x -> x.getName().equalsIgnoreCase(parts[4]))
              .findFirst()
              .orElse(new Bank(parts[4], 0));
      out.add(
          new ExchangeQuote(
              parts[0],
              parts[1],
              Double.parseDouble(parts[2]),
              DF.parse(parts[3]),
              bank,
              Double.parseDouble(parts[5])));
    } catch (Exception ignored) {
    }
  }
}
