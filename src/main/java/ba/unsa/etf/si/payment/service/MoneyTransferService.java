package ba.unsa.etf.si.payment.service;

import ba.unsa.etf.si.payment.model.BankAccount;
import ba.unsa.etf.si.payment.model.MoneyTransfer;
import ba.unsa.etf.si.payment.repository.MoneyTransferRepository;
import ba.unsa.etf.si.payment.response.Transfer.TransferResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
@Transactional
public class MoneyTransferService {
    private final MoneyTransferRepository moneyTransferRepository;

    public MoneyTransferService(MoneyTransferRepository moneyTransferRepository) {
        this.moneyTransferRepository = moneyTransferRepository;
    }
    public List<TransferResponse> findAllReceives(BankAccount bankAccount){
        List<TransferResponse> transfers = new ArrayList<>();
        List<MoneyTransfer> moneyTransfers = moneyTransferRepository.findMoneyTransferByReceives(bankAccount);
        moneyTransfers.forEach(moneyTransfer -> transfers.add(new TransferResponse(moneyTransfer.getId(),moneyTransfer.getReceives().getCardNumber(),
                moneyTransfer.getSends().getCardNumber(),moneyTransfer.getCreatedAt(),
                moneyTransfer.getMoneyAmount())));
        return transfers;
    }
    public List<TransferResponse> findAllSends(BankAccount bankAccount){
        List<TransferResponse> transfers = new ArrayList<>();
        List<MoneyTransfer> moneyTransfers = moneyTransferRepository.findMoneyTransferBySends(bankAccount);
        moneyTransfers.forEach(new Consumer<MoneyTransfer>() {
            @Override
            public void accept(MoneyTransfer moneyTransfer) {
                transfers.add(new TransferResponse(moneyTransfer.getId(),moneyTransfer.getReceives().getCardNumber(),
                        moneyTransfer.getSends().getCardNumber(),moneyTransfer.getCreatedAt(),
                        moneyTransfer.getMoneyAmount()));
            }
        });
        return transfers;
    }

    public MoneyTransfer save(MoneyTransfer moneyTransfer){
        return moneyTransferRepository.save(moneyTransfer);
    }
}
