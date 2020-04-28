package ba.unsa.etf.si.payment.controller;

import ba.unsa.etf.si.payment.model.ApplicationUser;
import ba.unsa.etf.si.payment.model.Question;
import ba.unsa.etf.si.payment.request.PasswordChangeRequest;
import ba.unsa.etf.si.payment.response.ApiResponse;
import ba.unsa.etf.si.payment.security.CurrentUser;
import ba.unsa.etf.si.payment.security.UserPrincipal;
import ba.unsa.etf.si.payment.service.ApplicationUserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/change")
public class PasswordChangeController {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;
    private final AuthenticationManager authenticationManager;

    public PasswordChangeController(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService, AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/securityquestion")
    public Question getSecurityQuestion(@CurrentUser UserPrincipal currentUser){
        ApplicationUser user=applicationUserService.find(currentUser.getId());
        return user.getAnswer().getQuestion();
    }

    @PostMapping("/newpassword")
    public ApiResponse getNewPassword(@Valid @RequestBody PasswordChangeRequest passwordChangeRequest, @CurrentUser UserPrincipal currentUser) {
        ApplicationUser user=applicationUserService.find(currentUser.getId());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        currentUser.getUsername(),
                        passwordChangeRequest.getOldPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        if(passwordEncoder.matches(passwordChangeRequest.getAnswer(), user.getAnswer().getText())){
        //if(passwordChangeRequest.getAnswer().equals(user.getAnswer().getText())) {
            user.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
            applicationUserService.save(user);
            return new ApiResponse(true, "Password changed successfully!");
        }
         return new ApiResponse(false, "Password change failed! Wrong answer!");
    }
}