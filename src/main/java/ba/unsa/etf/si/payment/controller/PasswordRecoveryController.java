package ba.unsa.etf.si.payment.controller;

import ba.unsa.etf.si.payment.exception.ResourceNotFoundException;
import ba.unsa.etf.si.payment.model.ApplicationUser;
import ba.unsa.etf.si.payment.model.Question;
import ba.unsa.etf.si.payment.request.PasswordRecoverRequest;
import ba.unsa.etf.si.payment.request.RecoverRequest;
import ba.unsa.etf.si.payment.response.PasswordResponse;
import ba.unsa.etf.si.payment.service.ApplicationUserService;
import ba.unsa.etf.si.payment.util.PasswordGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/recover")
public class PasswordRecoveryController {

    private final
    PasswordEncoder passwordEncoder;

    private final ApplicationUserService applicationUserService;

    public PasswordRecoveryController(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;
    }

    @PostMapping("/securityquestion")
    public Question getSecurityQuestion(@Valid @RequestBody RecoverRequest recoverRequest){
        ApplicationUser user=applicationUserService.getUserByUsernameOrEmail(recoverRequest.getUsernameOrEmail());
        if(user==null)
            throw new ResourceNotFoundException("User with credentials " + recoverRequest.getUsernameOrEmail() + " does not exist");
        return user.getAnswer().getQuestion();
    }

    @PostMapping("/newpassword")
    public PasswordResponse getNewPassword(@Valid @RequestBody PasswordRecoverRequest passwordRecoverRequest) {
        ApplicationUser user=applicationUserService.getUserByUsernameOrEmail(passwordRecoverRequest.getUsernameOrEmail());
        if(user==null)
            throw new ResourceNotFoundException("User with credentials" + passwordRecoverRequest.getUsernameOrEmail() + " does not exist");
        if(passwordRecoverRequest.getAnswer().equals(user.getAnswer().getText())){
            PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                    .useDigits(true)
                    .useLower(true)
                    .useUpper(true)
                    .build();
            String password=passwordGenerator.generate(8);
            user.setPassword(passwordEncoder.encode(password));
            applicationUserService.save(user);
            return new PasswordResponse(true, password);
        }
        return new PasswordResponse(false, "");
    }
}