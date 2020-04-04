package ba.unsa.etf.si.payment.model;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "transactions")
@Check(constraints = "total_price >= 0")
public class Transaction extends AuditModel {
    @Id
    @GeneratedValue(generator = "transaction_generator")
    @SequenceGenerator(
            name = "transaction_generator",
            sequenceName = "transaction_sequence",
            initialValue = 100
    )
    private Long id;

    //todo
    //Ovo ce morati biti nullable jer nemamo odmah podatke o računu
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bank_account_id",nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private  BankAccount bankAccount;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "merchant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Merchant merchant;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "application_user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ApplicationUser applicationUser;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(columnDefinition = "text")
    private String service;

    @Column(columnDefinition = "text")
    private String receiptId;

    @Column(name="processed")
    private Boolean processed;

    public Transaction(Merchant merchant, ApplicationUser applicationUser, Double totalPrice, String service, String receiptId, Boolean processed) {
        this.merchant=merchant;
        this.applicationUser=applicationUser;
        this.totalPrice=totalPrice;
        this.service=service;
        this.receiptId=receiptId;
        this.processed = processed;
    }

    public Transaction() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double amount) {
        this.totalPrice = amount;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }


    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }
}
