import java.util.Date;

public class InfoExchange {
    String currency1;
    String currency2;
    double exchangeRate;
    Date date;

    public InfoExchange(String currency1, String currency2, double exchangeRate, Date date) {
        this.currency1 = currency1;
        this.currency2 = currency2;
        this.exchangeRate = exchangeRate;
        this.date = date;
    }

    public String toString(){
        return date + ": курс " + currency1 + " для " + currency2 + " = " + exchangeRate;
    }

    public String getCurrency1() { return currency1; }

    public String getCurrency2() { return currency2; }

    public double getExchangeRate() { return exchangeRate; }

    public Date getDate() { return date; }

}
