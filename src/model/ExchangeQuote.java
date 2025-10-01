package model;

import java.util.Date;

public class ExchangeQuote extends ExchangeRate {
  private final Bank bank;
  private final double commission;

  public ExchangeQuote(
      String currency1, String currency2, double rate, Date date, Bank bank, double commission) {
    super(currency1, currency2, rate, date);
    this.bank = bank;
    this.commission = commission;
  }

  public Bank getBank() {
    return bank;
  }

  public double getCommission() {
    return commission;
  }

  public double rateWithCommission() {
    return getRate() * (1.0 + commission / 100.0);
  }

  @Override
  public String toString() {
    return String.format(
        java.util.Locale.US,
        "%s | банк=%s | комиссия=%.3f%% | курс c комиссией=%.6f",
        super.toString(),
        bank,
        commission,
        rateWithCommission());
  }
}
