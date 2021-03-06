package ba.unsa.etf.si.payment.response.transferResponse;

import ba.unsa.etf.si.payment.util.MoneyTransferStatus;

import java.util.List;

public class MoneyTransferResponse {
    private MoneyTransferStatus moneyTransferStatus;
    private String message;
    private List<TransferResponse> transfers;

    public MoneyTransferResponse(MoneyTransferStatus moneyTransferStatus, String message, List<TransferResponse> transfers) {
        this.moneyTransferStatus = moneyTransferStatus;
        this.message = message;
        this.transfers = transfers;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MoneyTransferStatus getMoneyTransferStatus() {
        return moneyTransferStatus;
    }

    public void setMoneyTransferStatus(MoneyTransferStatus moneyTransferStatus) {
        this.moneyTransferStatus = moneyTransferStatus;
    }

    public List<TransferResponse> getTransfers() {
        return transfers;
    }

    public void setTransfers(List<TransferResponse> transfers) {
        this.transfers = transfers;
    }
}
