package ba.unsa.etf.si.payment.service;

import ba.unsa.etf.si.payment.exception.ResourceNotFoundException;
import ba.unsa.etf.si.payment.model.ApplicationUser;
import ba.unsa.etf.si.payment.repository.ApplicationUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

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

    public List<ApplicationUser> getAll(){
        return applicationUserRepository.findAll();
    }
    public ApplicationUser save(ApplicationUser applicationUser) {
        return  applicationUserRepository.save(applicationUser);
    }

    public ApplicationUser getUserByUsernameOrEmail(String usernameOrEmail){
        ApplicationUser user=new ApplicationUser();
        if(!applicationUserRepository.existsByUsername(usernameOrEmail)) {
            if(!applicationUserRepository.existsByEmail(usernameOrEmail)) {
               return null;
            }
            else{
                user=applicationUserRepository.findByEmail(usernameOrEmail).get();
            }
        }
        else {
            user=applicationUserRepository.findByUsername(usernameOrEmail).get();
        }
        return user;
    }
}
