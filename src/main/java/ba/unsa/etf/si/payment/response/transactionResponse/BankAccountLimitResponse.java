package ba.unsa.etf.si.payment.response.transactionResponse;

import java.util.List;

public class BankAccountLimitResponse {

    private Long bankAccountId;
    private String bankName;
    private String cardNumber;
    private Boolean aboveLimit;
    private Double expenses;
    private List<TransactionDataResponse> monthTransactions;

    public BankAccountLimitResponse() {
    }

    public BankAccountLimitResponse(Long bankAccountId, String bankName, String cardNumber, Boolean aboveLimit,
                                    Double expenses, List<TransactionDataResponse> monthTransactions) {
        this.bankAccountId = bankAccountId;
        this.bankName = bankName;
        this.cardNumber = cardNumber;
        this.aboveLimit = aboveLimit;
        this.expenses = expenses;
        this.monthTransactions = monthTransactions;
    }

    public BankAccountLimitResponse(Long bankAccountId, String bankName, String cardNumber, Boolean aboveLimit) {
        this.bankAccountId = bankAccountId;
        this.bankName = bankName;
        this.cardNumber = cardNumber;
        this.aboveLimit = aboveLimit;
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Boolean getAboveLimit() {
        return aboveLimit;
    }

    public void setAboveLimit(Boolean aboveLimit) {
        this.aboveLimit = aboveLimit;
    }

    public Double getExpenses() {
        return expenses;
    }

    public void setExpenses(Double expenses) {
        this.expenses = expenses;
    }

    public List<TransactionDataResponse> getMonthTransactions() {
        return monthTransactions;
    }

    public void setMonthTransactions(List<TransactionDataResponse> monthTransactions) {
        this.monthTransactions = monthTransactions;
    }
}
