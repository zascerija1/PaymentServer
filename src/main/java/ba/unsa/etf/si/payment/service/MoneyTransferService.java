package ba.unsa.etf.si.payment.service;

import ba.unsa.etf.si.payment.model.BankAccount;
import ba.unsa.etf.si.payment.model.MoneyTransfer;
import ba.unsa.etf.si.payment.repository.MoneyTransferRepository;
import ba.unsa.etf.si.payment.response.transferResponse.MoneyTransferResponse;
import ba.unsa.etf.si.payment.response.transferResponse.TransferResponse;
import ba.unsa.etf.si.payment.util.MoneyTransferStatus;
import ba.unsa.etf.si.payment.util.PaymentStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
        List<MoneyTransfer> moneyTransfers = moneyTransferRepository.findMoneyTransferByReceivesAndPaymentStatus(bankAccount, PaymentStatus.PAID);
        moneyTransfers.forEach(moneyTransfer -> transfers.add(new TransferResponse(moneyTransfer.getId(),moneyTransfer.getReceives().getCardNumber(),
                moneyTransfer.getSends().getCardNumber(),moneyTransfer.getCreatedAt(),
                moneyTransfer.getMoneyAmount(), PaymentStatus.PAID)));
        return transfers;
    }
    public List<TransferResponse> findAllSends(BankAccount bankAccount){
        List<TransferResponse> transfers = new ArrayList<>();
        List<MoneyTransfer> moneyTransfers = moneyTransferRepository.findMoneyTransferBySendsAndPaymentStatus(bankAccount, PaymentStatus.PAID);
        moneyTransfers.forEach(new Consumer<MoneyTransfer>() {
            @Override
            public void accept(MoneyTransfer moneyTransfer) {
                transfers.add(new TransferResponse(moneyTransfer.getId(),moneyTransfer.getReceives().getCardNumber(),
                        moneyTransfer.getSends().getCardNumber(),moneyTransfer.getCreatedAt(),
                        moneyTransfer.getMoneyAmount(), PaymentStatus.PAID));
            }
        });
        return transfers;
    }

    public MoneyTransfer save(MoneyTransfer moneyTransfer){
        return moneyTransferRepository.save(moneyTransfer);
    }

    public MoneyTransferResponse getTransferInfo(UUID transferId){
        Optional<MoneyTransfer> optMoneyTransfer=moneyTransferRepository.findById(transferId);
        MoneyTransferResponse moneyTransferResponse=new MoneyTransferResponse(MoneyTransferStatus.CANCELED,
                "Invalid transfer id", null);
        if (!optMoneyTransfer.isPresent())
            return moneyTransferResponse;
        moneyTransferResponse.setMoneyTransferStatus(MoneyTransferStatus.OK);
        moneyTransferResponse.setMessage("Transfer found!");
        MoneyTransfer moneyTransfer=optMoneyTransfer.get();
        TransferResponse transferResponse=new TransferResponse(moneyTransfer.getId(), moneyTransfer.getCreatedAt(), moneyTransfer.getMoneyAmount(),
                moneyTransfer.getPaymentStatus());
        if(moneyTransfer.getReceives()!=null)
            transferResponse.setDestCardNumber(moneyTransfer.getReceives().getCardNumber());
        if(moneyTransfer.getSends()!=null)
            transferResponse.setSourceCardNumber(moneyTransfer.getSends().getCardNumber());
        moneyTransferResponse.setTransfers(Collections.singletonList(transferResponse));
        return moneyTransferResponse;
    }

}
