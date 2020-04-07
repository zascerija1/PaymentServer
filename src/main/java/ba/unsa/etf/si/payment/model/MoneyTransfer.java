package ba.unsa.etf.si.payment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "money_transfers")
public class MoneyTransfer extends AuditModel {
    @Id
    @GeneratedValue(generator = "money_transfer_generator")
    @SequenceGenerator(
            name = "money_transfer_generator",
            sequenceName = "money_transfer_sequence",
            initialValue = 100
    )
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "receives_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BankAccount receives;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "sends_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BankAccount sends;

    @Column(name = "date_and_time", nullable = false, updatable = false)
    @JsonFormat(pattern="dd.MM.yyyy HH:mm:ss")
    private LocalDateTime dateAndTime;

    @Column(name = "money_amount", nullable = false)
    private Double moneyAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public Double getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(Double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }
}

