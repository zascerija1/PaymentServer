package ba.unsa.etf.si.payment.model;

import ba.unsa.etf.si.payment.model.auth.Role;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="bank")
public class Bank {
    @Id
    @GeneratedValue(generator = "bank_as_client_generator")
    @Column(name = "bank_id")
    @SequenceGenerator(
            name = "bank_as_client_generator",
            sequenceName = "bank_as_client_sequence"
    )
    private Long id;

    @Column(name = "name")
    private Double name;

    @OneToMany(mappedBy = "bank")
    private Set<ATM> atms;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "application_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<ApplicationUser> users;

    @OneToMany(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "bank_account_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<BankAccount> bankAccount;
}
