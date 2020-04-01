package ba.unsa.etf.si.payment.model;

import org.hibernate.annotations.Check;

import javax.persistence.*;

@Entity
@Table(name = "ATM")
@Check( constraints = "balance >= 0, limit >= 0")//popraviti kad napravim kolone
public class ATM {
    @Id
    @GeneratedValue(generator = "ATM_generator")
    @SequenceGenerator(
            name = "ATM_generator",
            sequenceName = "ATM_sequence"
    )
    private Long id;

    @Column(columnDefinition = "latitude")
    private Double  latitude;

    @Column(columnDefinition = "longitude")
    private Double  longitude;

    @Column(name = "balance", nullable = false)
    private Double balance;

    @Column(name = "limit", nullable = false)
    private Double limit;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank banks;
}
