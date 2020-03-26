package ba.unsa.etf.si.payment.model;

import ba.unsa.etf.si.payment.model.auth.Role;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

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

    @NaturalId
    @NotBlank
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    @Size(max = 100)
    private String password;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "answer_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Answer answer;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public ApplicationUser() {
    }

    public ApplicationUser(String firstName, String lastName, String username, String email, String password, Answer answer) {
        this.firstName = firstName;
        this.lastName=lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.answer=answer;
    }

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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
