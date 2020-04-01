package ba.unsa.etf.si.payment.model;

import net.bytebuddy.build.Plugin;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "merchants")
public class Merchant {
    @Id
    @GeneratedValue(generator = "merchant_generator")
    @SequenceGenerator(
            name = "merchant_generator",
            sequenceName = "merchant_sequence"
    )
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "bank_account_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BankAccount bankAccount;

    @Column(columnDefinition = "text")
    private String merchantName;

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

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
}
