package model;

public class ExchangeTransaction {
  private final ExchangeQuote quote;
  private final double amountFrom;

  public ExchangeTransaction(ExchangeQuote quote, double amountFrom) {
    this.quote = quote;
    this.amountFrom = amountFrom;
  }

  public ExchangeQuote getQuote() {
    return quote;
  }

  public double getAmountFrom() {
    return amountFrom;
  }

  public double computeAmountTo() {
    return amountFrom * quote.rateWithCommission();
  }

  @Override
  public String toString() {
    return "транзакция: "
        + amountFrom + " "
        + quote.getCurrency1() + " -> "
        + String.format(java.util.Locale.US, "%.4f", computeAmountTo()) + " "
        + quote.getCurrency2() + " ("
        + quote.getBank() + ")";
  }
}
