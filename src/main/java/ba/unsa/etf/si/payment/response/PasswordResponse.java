package ba.unsa.etf.si.payment.response;

public class PasswordResponse {
    private Boolean success;
    private String password;

    public PasswordResponse(Boolean success, String password) {
        this.success = success;
        this.password = password;
    }

    //add comment
    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
