package ba.unsa.etf.si.payment.request;

import javax.validation.constraints.NotBlank;

public class PasswordChangeRequest {

    public PasswordChangeRequest(String oldPassword, String newPassword, String answer) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.answer = answer;
    }

    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;

    @NotBlank
    private String answer;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
