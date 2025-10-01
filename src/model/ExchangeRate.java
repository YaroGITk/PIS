package model;

import java.util.Date;

public class ExchangeRate {
  private final String currency1;
  private final String currency2;
  private final double exchangeRate;
  private final Date date;

  public ExchangeRate(String currency1, String currency2, double rate, Date date) {
    this.currency1 = currency1;
    this.currency2 = currency2;
    this.exchangeRate = rate;
    this.date = date;
  }

  public String getCurrency1() {
    return currency1;
  }

  public String getCurrency2() {
    return currency2;
  }

  public double getRate() {
    return exchangeRate;
  }

  public Date getDate() {
    return date;
  }

  @Override
  public String toString() {
    return date + ": курс " + currency1 + " для " + currency2 + " = " + exchangeRate;
  }
}
