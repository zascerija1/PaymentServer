package ba.unsa.etf.si.payment.response;

import java.util.Date;

public class BankAccountDataResponse {

    private Long id;

    private String  accountOwner;

    private String  bankName;

    private Date expiryDate;

    private String cardNumber;

    private Double monthlyLimit;

    private Double balanceLowerLimit;

    private Double transactionAmountLimit;

    public BankAccountDataResponse() {
    }

    public BankAccountDataResponse(Long id, String accountOwner, String bankName, Date expiryDate, String cardNumber, Double monthlyLimit, Double balanceLowerLimit, Double transactionAmountLimit) {
        this.id = id;
        this.accountOwner = accountOwner;
        this.bankName = bankName;
        this.expiryDate = expiryDate;
        this.cardNumber = cardNumber;
        this.monthlyLimit = monthlyLimit;
        this.balanceLowerLimit = balanceLowerLimit;
        this.transactionAmountLimit = transactionAmountLimit;
    }

    public String getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(String accountOwner) {
        this.accountOwner = accountOwner;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMonthlyLimit() {
        return monthlyLimit;
    }

    public Double getBalanceLowerLimit() {
        return balanceLowerLimit;
    }

    public void setBalanceLowerLimit(Double balanceLowerLimit) {
        this.balanceLowerLimit = balanceLowerLimit;
    }

    public Double getTransactionAmountLimit() {
        return transactionAmountLimit;
    }

    public void setTransactionAmountLimit(Double transactionAmountLimit) {
        this.transactionAmountLimit = transactionAmountLimit;
    }
}
