package ba.unsa.etf.si.payment;

import ba.unsa.etf.si.payment.model.Answer;
import ba.unsa.etf.si.payment.model.ApplicationUser;
import ba.unsa.etf.si.payment.model.BankAccount;
import ba.unsa.etf.si.payment.model.BankAccountUser;
import ba.unsa.etf.si.payment.repository.BankAccountUserRepository;
import ba.unsa.etf.si.payment.response.transactionResponse.PaymentResponse;
import ba.unsa.etf.si.payment.service.BankAccountUserService;
import ba.unsa.etf.si.payment.util.PaymentStatus;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

@SpringBootTest
public class PaymentProcessingTest {
    @Test
    void contextLoads() {
    }

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public BankAccountUserService employeeService() {
            return new BankAccountUserService(bankAccountUserRepository);
        }

        @Autowired
        private BankAccountUserService bankAccountUserService;

        @MockBean
        private  BankAccountUserRepository bankAccountUserRepository;

        @BeforeEach
        public void setUp() {
            ApplicationUser applicationUser = new ApplicationUser("Test", "Testovic",
                    "test123", "test@gmail.com", "test123pass", new Answer());
            applicationUser.setId(1L);
            BankAccount bankAccount = new BankAccount("Test Testovic", "333", "4444444444444444", 30.00);
            BankAccountUser bankAccountUser = new BankAccountUser();
            bankAccountUser.setId(1L);
            bankAccountUser.setBankAccount(bankAccount);
            bankAccountUser.setApplicationUser(applicationUser);

            Mockito.when(bankAccountUserRepository.existsByIdAndApplicationUser_Id(1L, 1L))
                    .thenReturn(true);
            Mockito.when(bankAccountUserRepository.existsByIdAndApplicationUser_Id(2L, 2L))
                    .thenReturn(false);
            Mockito.when(bankAccountUserRepository.findBankAccountUserById(1L))
                    .thenReturn(bankAccountUser);
        }

        @Test
        public void whenPaymentResultIsOK() {
            PaymentResponse paymentResponse = bankAccountUserService.checkBalanceForPayment(1L, 1L, 13.00);
            assert(paymentResponse.getPaymentStatus())
                    .equals(PaymentStatus.SUFFICIENT_FUNDS);
        }
    }

}
