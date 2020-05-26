package ba.unsa.etf.si.payment.service;


import ba.unsa.etf.si.payment.request.qrCodes.StaticQRRequest;
import ba.unsa.etf.si.payment.request.TransacationSuccessRequest;
import ba.unsa.etf.si.payment.response.MainInfoResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class RestService {

    private final RestTemplate restTemplate;

    public RestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<MainInfoResponse> getReceiptInfo(StaticQRRequest staticQRRequest){
        String url="https://main-server-si.herokuapp.com/api/receipts/info";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<StaticQRRequest> request = new HttpEntity<>(staticQRRequest, headers);
         return restTemplate.postForEntity(url, request, MainInfoResponse.class);
    }

    public void updateTransactionStatus(TransacationSuccessRequest transacationSuccessRequest,
                                        String receiptId){
        String url = "https://main-server-si.herokuapp.com/api/receipts/"+receiptId;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<TransacationSuccessRequest> entity = new HttpEntity<>(transacationSuccessRequest, headers);
        this.restTemplate.put(url, entity);
    }

}
