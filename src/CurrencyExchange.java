/*
    Курсы валют
*/

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CurrencyExchange {

    public static void main(String[] args) throws IOException, ParseException {
        List<InfoExchange> data = getDataFromFile("test1.txt");
        List<Bank> banks = getBanks("banks.txt");

        Bank maxChet = banks.getFirst();
        for(Bank bank : banks) {
            if (bank.getCountMembers() % 2 > maxChet.getCountMembers()) {
                maxChet = bank;
            }

            System.out.println(bank);
        }

        String minCurrency = "";
        double minExchangeRate = Double.MAX_VALUE;
        for(InfoExchange info : data) {
            if (info.exchangeRate < minExchangeRate) {
                minExchangeRate = info.exchangeRate;
                minCurrency = info.getCurrency1();
            }

            System.out.println(info);
        }

        System.out.println("\nМИНИМАЛЬНЫЙ КУРС ВАЛЮТЫ: валюта " + minCurrency + " курс: " + minExchangeRate);
    }
    public static List<Bank> getBanks(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        List<Bank> banks = new ArrayList<>();

        for(String line : lines) {
            String[] parts = line.split(" ");
            String name = parts[0];
            long countMembers = Integer.parseInt(parts[1]);

            banks.add(
                    new Bank(
                            name,
                            countMembers
                    )
            );
        }

        return banks;
    }

    public static List<InfoExchange> getDataFromFile(String filename) throws IOException, ParseException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        List<InfoExchange> infoExchangeList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

        for(String line : lines) {
            String[] parts = line.split(" ");
            String currency1 = parts[0];
            String currency2 = parts[1];
            double exchangeRate = Double.parseDouble(parts[2]);
            Date date = dateFormat.parse(parts[3]);

            infoExchangeList.add(
                    new InfoExchange(
                            currency1,
                            currency2,
                            exchangeRate,
                            date
                    )
            );
        }

        return infoExchangeList;
    }
}