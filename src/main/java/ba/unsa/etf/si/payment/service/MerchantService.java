package ba.unsa.etf.si.payment.service;

import ba.unsa.etf.si.payment.model.Merchant;
import ba.unsa.etf.si.payment.repository.MerchantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantService {
    private final MerchantRepository merchantRepository;

    public MerchantService(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }
    public List<Merchant> find(String merchantName){
        return merchantRepository.findByMerchantName(merchantName);
    }

    public Merchant save(Merchant merchant){ return merchantRepository.save(merchant);}

    public void delete(Long id){ merchantRepository.deleteById(id);}
}
