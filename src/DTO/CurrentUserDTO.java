package DTO;

public class CurrentUserDTO {
    public String userName;
    public int funds;
    public boolean isEuroMainCurrency;
    public int income;
    public int instalmentsNo;

    public CurrentUserDTO(String userName, int funds, boolean isEuroMainCurrency, int income, int instalmentsNo)
    {
        this.userName = userName;
        this.funds = funds;
        this.isEuroMainCurrency = isEuroMainCurrency;
        this.income = income;
        this.instalmentsNo = instalmentsNo;
    }
}
