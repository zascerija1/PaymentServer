package ba.unsa.etf.si.payment.util.NotificationUtil;

public class MessageConstants {
    public static String SUCCESSFULL_TRANSFER_SOURCE = "You have successfully transfered funds!";
    public static String SUCCESSFULL_TRANSFER_DEST = "A payment has been made to your account!";
    public static String FAIL_TRANSFER = "There was a problem transferring your money!";
    public static String FAIL_TRANSACTION = "The transaction was not finalized due to problems encountered!";
    public static String CANCEL_TRANSACTION = "The transaction was canceled successfully!";
    public static String FAIL_TRANSFER_FUNDS = "Could not proceed with transfer due to insufficient funds!!";
    public static String FAIL_TRANSACTION_FUNDS = "Could not proceed with transaction due to insufficient funds!";
    public static String SUCCESSFULL_TRANSACTION = "Successfull transacation!";
    public static String MONTHLY_LIMIT = "You have exceeded monthly limit of ";
    public static String HUGE_TRANSACTION_MSG = "You have made a payment above ";
    public static String ACCOUNT_BALANCE = "Your account balance is now below ";
    public static String ATTEMPTS = " You have reached the limit od 5 attempts!";
    public static Double WARN_BALANCE = 5.0;
    public static Double HUGE_TRANSACTION_LIMIT = 500.0;
    public static Double MONTH_TRANSACTION_LIMIT = 1000.0;


}
