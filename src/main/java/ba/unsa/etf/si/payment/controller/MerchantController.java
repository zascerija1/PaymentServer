package ba.unsa.etf.si.payment.controller;


import ba.unsa.etf.si.payment.response.MerchantDataResponse;
import ba.unsa.etf.si.payment.service.MerchantService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/merchants")
public class MerchantController {

    private final MerchantService merchantService;

    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @GetMapping("/all")
    public List<MerchantDataResponse> getAllMerchants(){
       return merchantService.getAllMerchants()
                .stream()
                .map(merchant -> new MerchantDataResponse(merchant.getId(),merchant.getMerchantName()))
                .collect(Collectors.toList());
    }
}
