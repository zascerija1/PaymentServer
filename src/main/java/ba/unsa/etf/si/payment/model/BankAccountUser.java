package ba.unsa.etf.si.payment.model;

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
}
