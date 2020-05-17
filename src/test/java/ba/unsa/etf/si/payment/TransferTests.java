package ba.unsa.etf.si.payment;

import ba.unsa.etf.si.payment.model.Answer;
import ba.unsa.etf.si.payment.model.ApplicationUser;
import ba.unsa.etf.si.payment.model.BankAccount;
import ba.unsa.etf.si.payment.model.MoneyTransfer;
import ba.unsa.etf.si.payment.repository.MoneyTransferRepository;
import ba.unsa.etf.si.payment.response.transferResponse.MoneyTransferResponse;
import ba.unsa.etf.si.payment.response.transferResponse.TransferResponse;
import ba.unsa.etf.si.payment.service.MoneyTransferService;
import ba.unsa.etf.si.payment.util.MoneyTransferStatus;
import ba.unsa.etf.si.payment.util.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class TransferTests {

    @TestConfiguration
    class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public MoneyTransferService employeeService() {
            return new MoneyTransferService(moneyTransferRepository);
        }
    }

    @Autowired
    private MoneyTransferService moneyTransferService;

    @MockBean
    private MoneyTransferRepository moneyTransferRepository;

    @BeforeEach
    public void setUp() {
        ApplicationUser applicationUser = new ApplicationUser("Test", "Testovic",
                "test123", "test@gmail.com", "test123pass", new Answer());
        applicationUser.setId(1L);
        BankAccount sends = new BankAccount("Test Testovic", "333", "4444444444444444", 30.00);
        BankAccount receives = new BankAccount("Meho Mehic", "444", "5555555555555555", 64.00);
        MoneyTransfer moneyTransferInsuff = new MoneyTransfer(sends, null, 45.00, PaymentStatus.INSUFFICIENT_FUNDS, applicationUser);
        moneyTransferInsuff.setId(UUID.fromString("5d897bd6-9798-11ea-bb37-0242ac130002"));
        MoneyTransfer moneyTransferSuff= new MoneyTransfer(sends, receives, 35.00, PaymentStatus.PAID, applicationUser);
        moneyTransferSuff.setId(UUID.fromString("5d897f1e-9798-11ea-bb37-0242ac130002"));


        Mockito.when(moneyTransferRepository.findById(moneyTransferInsuff.getId()))
                .thenReturn(Optional.of(moneyTransferInsuff));
        Mockito.when(moneyTransferRepository.findById(moneyTransferSuff.getId()))
                .thenReturn(Optional.of(moneyTransferSuff));
        Mockito.when(moneyTransferRepository.findById(UUID.fromString("5d89e94a-9798-11ea-bb37-0242ac130002")))
                .thenReturn(Optional.empty());



    }

    @Test
    public void whenTransferStatusIsPaid() {

        MoneyTransferResponse moneyTransferResponse = moneyTransferService.getTransferInfo(UUID.fromString("5d897f1e-9798-11ea-bb37-0242ac130002"));
        TransferResponse transferResponse = moneyTransferResponse.getTransfers().get(0);
        assert ( moneyTransferResponse.getMoneyTransferStatus())
                .equals(MoneyTransferStatus.OK);
        assert (transferResponse.getPaymentStatus())
                .equals(PaymentStatus.PAID);
        assert (transferResponse.getSourceCardNumber())
                .equals("4444444444444444");
        assert (transferResponse.getDestCardNumber())
                .equals("5555555555555555");
        assert (transferResponse.getAmount())
                .equals(35.00);
    }

    @Test
    public void whenTransferStatusIsInSufficientFunds() {

        MoneyTransferResponse moneyTransferResponse = moneyTransferService.getTransferInfo(UUID.fromString("5d897bd6-9798-11ea-bb37-0242ac130002"));
        TransferResponse transferResponse = moneyTransferResponse.getTransfers().get(0);
        System.out.println(transferResponse);
        assert (moneyTransferResponse.getMoneyTransferStatus())
                .equals(MoneyTransferStatus.OK);
        assert (transferResponse.getPaymentStatus())
                .equals(PaymentStatus.INSUFFICIENT_FUNDS);
        assert (transferResponse.getSourceCardNumber())
                .equals("4444444444444444");
        assert (transferResponse.getDestCardNumber()) == null;
        assert (transferResponse.getAmount())
                .equals(45.00);
    }

    @Test
    public void whenTransferStatusIsCanceled() {
        MoneyTransferResponse moneyTransferResponse = moneyTransferService.getTransferInfo(UUID.fromString("5d89e94a-9798-11ea-bb37-0242ac130002"));
        assert (moneyTransferResponse.getMoneyTransferStatus())
                .equals(MoneyTransferStatus.CANCELED);
    }

}


