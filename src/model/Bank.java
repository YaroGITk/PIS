package model;

public class Bank {
    String name;
    long clientCount;

    public Bank(String name, long countMembers) {
        this.name = name;
        this.clientCount = countMembers;
    }

    public String toString() {
        return "Название банка: " + this.name + ", кол-во клиентов: " +  this.clientCount;
    }

    public long getClientCount() { return this.clientCount; }

}
