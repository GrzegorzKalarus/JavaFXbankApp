package domain;

public class DataUser {
    private int id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private int age;
    private int income;
    private int numberOfInstallments;
    private int funds;

    public DataUser(int id,String username,String password, String name, String surname, int age, int income,int numberOfInstallments, int funds) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.income = income;
        this.numberOfInstallments=numberOfInstallments;
        this.funds=funds;
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


}