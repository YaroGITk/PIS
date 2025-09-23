public class Bank {
    String name;
    long countMembers;

    public Bank(String name, long countMembers) {
        this.name = name;
        this.countMembers = countMembers;
    }

    public String toString() {
        return "Название банка: " + this.name + ", кол-во клиентов: " +  this.countMembers;
    }

    public long getCountMembers() { return countMembers; }

}
