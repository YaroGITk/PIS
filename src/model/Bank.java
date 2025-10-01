package model;

public class Bank {
  private final String name;
  private final long clientCount;

  public Bank(String name, long clientCount) {
    this.name = name;
    this.clientCount = clientCount;
  }

  public String getName() {
    return name;
  }

  public long getClientCount() {
    return clientCount;
  }

  @Override
  public String toString() {
    return "Название банка: " + name + ", кол-во клиентов: " + clientCount;
  }
}
