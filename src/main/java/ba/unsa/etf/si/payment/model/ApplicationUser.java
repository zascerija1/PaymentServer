package ba.unsa.etf.si.payment.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="application_users")
public class ApplicationUser extends AuditModel{
    @Id
    @GeneratedValue(generator = "user_generator")
    @SequenceGenerator(
            name="user_generator",
            sequenceName = "user_sequence"
    )
    private Long id;

    @NotBlank
    @Column(columnDefinition = "text")
    private String firstName;

    @NotBlank
    @Column(columnDefinition = "text")
    private String lastName;

    @NotBlank
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "answer_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Answer answer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
