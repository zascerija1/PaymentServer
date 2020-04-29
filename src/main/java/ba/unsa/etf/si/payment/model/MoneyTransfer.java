package ba.unsa.etf.si.payment.model;

import ba.unsa.etf.si.payment.util.PaymentStatus;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Table(name = "money_transfers")
public class MoneyTransfer extends AuditModel {
    @Id
    @GeneratedValue(generator = "UUID2")
    @GenericGenerator(
            name = "UUID2",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receives_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BankAccount receives;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sends_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BankAccount sends;

    @Column(name = "money_amount", nullable = false)
    private Double moneyAmount;

    private PaymentStatus paymentStatus;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "sender_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ApplicationUser sender;

    public MoneyTransfer() {
    }

    public MoneyTransfer(BankAccount sends, BankAccount receives, Double moneyAmount, PaymentStatus paymentStatus, ApplicationUser sender) {
        this.sends=sends;
        this.receives=receives;
        this.moneyAmount=moneyAmount;
        this.paymentStatus = paymentStatus;
        this.sender = sender;
    }



    public BankAccount getReceives() {
        return receives;
    }

    public void setReceives(BankAccount receives) {
        this.receives = receives;
    }

    public BankAccount getSends() {
        return sends;
    }

    public void setSends(BankAccount sends) {
        this.sends = sends;
    }

    public Double getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(Double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public ApplicationUser getSender() {
        return sender;
    }

    public void setSender(ApplicationUser sender) {
        this.sender = sender;
    }
}

