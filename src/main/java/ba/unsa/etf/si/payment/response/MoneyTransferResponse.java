package ba.unsa.etf.si.payment.response;

import ba.unsa.etf.si.payment.model.MoneyTransfer;
import ba.unsa.etf.si.payment.util.MoneyTransferStatus;

import java.util.List;

public class MoneyTransferResponse {
    private MoneyTransferStatus moneyTransferStatus;
    private List<MoneyTransfer> transfers;

    public MoneyTransferResponse(MoneyTransferStatus moneyTransferStatus, List<MoneyTransfer> transfers) {
        this.moneyTransferStatus = moneyTransferStatus;
        this.transfers = transfers;
    }

    public MoneyTransferStatus getMoneyTransferStatus() {
        return moneyTransferStatus;
    }

    public void setMoneyTransferStatus(MoneyTransferStatus moneyTransferStatus) {
        this.moneyTransferStatus = moneyTransferStatus;
    }

    public List<MoneyTransfer> getTransfers() {
        return transfers;
    }

    public void setTransfers(List<MoneyTransfer> transfers) {
        this.transfers = transfers;
    }
}
