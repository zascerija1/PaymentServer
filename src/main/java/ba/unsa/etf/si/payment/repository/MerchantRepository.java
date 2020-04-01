package ba.unsa.etf.si.payment.repository;

import ba.unsa.etf.si.payment.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    List<Merchant> findByMerchantName(String merchantName);
}
