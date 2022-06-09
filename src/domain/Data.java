package domain;

public class Data {
    private int id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private int age;
    private int income;
    private int numberOfInstallments;
    private int funds;
    private String accountNumber;

    public Data(int id, String username, String password, String name, String surname, int age, int income, int numberOfInstallments, int funds, String accountNumber) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.income = income;
        this.numberOfInstallments=numberOfInstallments;
        this.funds=funds;
        this.accountNumber=accountNumber;
    }

    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public int getAge() {
        return age;
    }
    public int getIncome() {
        return income;
    }
    public int getNumberOfInstallments() {
        return numberOfInstallments;
    }
        public int getFunds() {
            return funds;
        }
        public String getAccountNumber(){
        return accountNumber;
        }


}