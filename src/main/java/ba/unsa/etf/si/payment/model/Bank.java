package ba.unsa.etf.si.payment.model;

import javax.persistence.*;

@Entity
@Table(name = "banks")
public class Bank {
    @Id
    @GeneratedValue(generator = "bank_as_client_generator")
    @SequenceGenerator(
            name = "bank_as_client_generator",
            sequenceName = "bank_as_client_sequence",
            initialValue = 100
    )
    private Long id;

    @Column(name = "bankName")
    private String bankName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /*

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "application_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<ApplicationUser> users;
    @OneToMany(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "bank_account_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<BankAccount> bankAccount;

     */
}
