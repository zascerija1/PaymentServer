package ba.unsa.etf.si.payment.controller;

import ba.unsa.etf.si.payment.exception.ResourceNotFoundException;
import ba.unsa.etf.si.payment.model.ApplicationUser;
import ba.unsa.etf.si.payment.repository.ApplicationUserRepository;
import ba.unsa.etf.si.payment.availability.UserIdentityAvailability;
import ba.unsa.etf.si.payment.response.UserProfileResponse;
import ba.unsa.etf.si.payment.response.UserSummaryResponse;
import ba.unsa.etf.si.payment.security.CurrentUser;
import ba.unsa.etf.si.payment.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final
    ApplicationUserRepository applicationUserRepository;

    private static final
    Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummaryResponse getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummaryResponse userSummary = new UserSummaryResponse(currentUser.getId(), currentUser.getFirstName(), currentUser.getLastName(), currentUser.getUsername(), currentUser.getEmail());
        return userSummary;
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !applicationUserRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !applicationUserRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{username}")
    public UserProfileResponse getUserProfile(@PathVariable(value = "username") String username) {
        ApplicationUser user = applicationUserRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(username + " not found"));

        UserProfileResponse userProfile = new UserProfileResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail(), user.getCreatedAt());

        return userProfile;
    }
}
