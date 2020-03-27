package ba.unsa.etf.si.payment.response;

public class AccountResponse {
    Boolean success;
    String text;

    public AccountResponse(Boolean success, String text) {
        this.success = success;
        this.text = text;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
