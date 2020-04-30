package ba.unsa.etf.si.payment.response.transactionResponse;


import ba.unsa.etf.si.payment.util.PaymentStatus;

import java.util.List;

public class TransactionDetailResponse {

    private TransactionDataResponse transactionDataResponse;
    private List<TransactionLogResponse> transactionLogs;
    private PaymentStatus paymentStatus;

    public TransactionDetailResponse() {
    }

    public TransactionDetailResponse(TransactionDataResponse transactionDataResponse, List<TransactionLogResponse> transactionLogs, PaymentStatus paymentStatus) {
        this.transactionDataResponse = transactionDataResponse;
        this.transactionLogs = transactionLogs;
        this.paymentStatus = paymentStatus;
    }

    public TransactionDataResponse getTransactionDataResponse() {
        return transactionDataResponse;
    }

    public void setTransactionDataResponse(TransactionDataResponse transactionDataResponse) {
        this.transactionDataResponse = transactionDataResponse;
    }

    public List<TransactionLogResponse> getTransactionLogs() {
        return transactionLogs;
    }

    public void setTransactionLogs(List<TransactionLogResponse> transactionLogs) {
        this.transactionLogs = transactionLogs;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
