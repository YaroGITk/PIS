package model;

public class Bank {
  private final String name;
  private final long clientCount;

  public Bank(String name, long countMembers) {
    this.name = name;
    this.clientCount = countMembers;
  }

  @Override
  public String toString() {
    return "Название банка: " + this.name + ", кол-во клиентов: " + this.clientCount;
  }

  public String getName() {
    return name;
  }

  public long getClientCount() {
    return this.clientCount;
  }
}
