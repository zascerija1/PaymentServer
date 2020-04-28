package ba.unsa.etf.si.payment.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "transaction_logs")
public class TransactionLog extends AuditModel{
    @Id
    @GeneratedValue(generator = "UUID_logs")
    @GenericGenerator(
            name = "UUID_logs",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private  Transaction transaction;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bank_account_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private  BankAccount bankAccount;

    private Boolean success;

    public TransactionLog() {
    }

    public TransactionLog(Transaction transaction, BankAccount bankAccount, Boolean success){
        this.transaction=transaction;
        this.bankAccount=bankAccount;
        this.success=success;
    }

    public TransactionLog(Transaction transaction, Boolean success){
        this.transaction=transaction;
        this.success=success;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
