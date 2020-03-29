package ba.unsa.etf.si.payment.repository;

import ba.unsa.etf.si.payment.model.auth.Role;
import ba.unsa.etf.si.payment.model.auth.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}