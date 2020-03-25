package ba.unsa.etf.si.payment.service;

import ba.unsa.etf.si.payment.exception.ResourceNotFoundException;
import ba.unsa.etf.si.payment.model.ApplicationUser;
import ba.unsa.etf.si.payment.repository.ApplicationUserRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService {
    private final ApplicationUserRepository applicationUserRepository;

    public ApplicationUserService(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }
    public ApplicationUser find(Long userId){
        return applicationUserRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }

    public ApplicationUser save(ApplicationUser applicationUser) {
        return  applicationUserRepository.save(applicationUser);
    }
}
