package ba.unsa.etf.si.payment.request;

import javax.validation.constraints.NotBlank;

public class PasswordRequest {
    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    private String answer;

    public PasswordRequest(String usernameOrEmail, String answer) {
        this.usernameOrEmail = usernameOrEmail;
        this.answer = answer;
    }

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
