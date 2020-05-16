package ba.unsa.etf.si.payment.model;

import ba.unsa.etf.si.payment.request.BankAccountConfigRequest;
import ba.unsa.etf.si.payment.util.NotificationUtil.MessageConstants;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "bank_account_users")
public class BankAccountUser {
    @Id
    @GeneratedValue(generator = "bank_generator_user")
    @SequenceGenerator(
            name = "bank_generator_user",
            sequenceName = "bank_user_sequence",
            initialValue = 100
    )
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "bank_account_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BankAccount bankAccount;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "application_user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ApplicationUser applicationUser;

    @Column(nullable = false)
    private Double monthlyLimit = MessageConstants.MONTH_TRANSACTION_LIMIT;

    @Column(nullable = false)
    private Double balanceLowerLimit = MessageConstants.WARN_BALANCE;

    @Column(nullable = false)
    private Double transactionAmountLimit = MessageConstants.HUGE_TRANSACTION_LIMIT;

    public BankAccountUser() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public Double getMonthlyLimit() {
        return monthlyLimit;
    }

    public void setMonthlyLimit(Double monthlyLimit) {
        this.monthlyLimit = monthlyLimit;
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

    public void setConfig(BankAccountConfigRequest bankAccountConfigRequest){
        this.monthlyLimit = bankAccountConfigRequest.getMonthlyLimit();
        this.balanceLowerLimit = bankAccountConfigRequest.getBalanceLowerLimit();
        this.transactionAmountLimit = bankAccountConfigRequest.getTransactionAmountLimit();
    }
}
