package ba.unsa.etf.si.payment.controller;

import ba.unsa.etf.si.payment.exception.AppException;
import ba.unsa.etf.si.payment.exception.ResourceNotFoundException;
import ba.unsa.etf.si.payment.model.ApplicationUser;
import ba.unsa.etf.si.payment.model.Question;
import ba.unsa.etf.si.payment.model.auth.Role;
import ba.unsa.etf.si.payment.model.auth.RoleName;
import ba.unsa.etf.si.payment.repository.ApplicationUserRepository;
import ba.unsa.etf.si.payment.repository.RoleRepository;
import ba.unsa.etf.si.payment.request.LoginRequest;
import ba.unsa.etf.si.payment.request.SignUpRequest;
import ba.unsa.etf.si.payment.response.ApiResponse;
import ba.unsa.etf.si.payment.response.JwtAuthenticationResponse;
import ba.unsa.etf.si.payment.security.JwtTokenProvider;
import ba.unsa.etf.si.payment.service.AnswerService;
import ba.unsa.etf.si.payment.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final
    AuthenticationManager authenticationManager;

    private final
    ApplicationUserRepository userRepository;

    private final
    RoleRepository roleRepository;

    private final
    PasswordEncoder passwordEncoder;

    private final
    JwtTokenProvider tokenProvider;

    private final
    QuestionService questionService;

    private final AnswerService answerService;

    public AuthController(AuthenticationManager authenticationManager, ApplicationUserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider, QuestionService questionService, AnswerService answerService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup/{questionId}")
    public ResponseEntity<?> registerUser(@PathVariable Long questionId, @Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(signUpRequest.getAnswer()==null || signUpRequest.getAnswer().getText().isEmpty()){
            return new ResponseEntity(new ApiResponse(false, "Answer text must not be empty!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        ApplicationUser user = new ApplicationUser(signUpRequest.getFirstName(),signUpRequest.getLastName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword(), signUpRequest.getAnswer());

        user.setPassword(passwordEncoder.encode(user.getPassword()));


        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        if(!questionService.existsById(questionId)) {
            return new ResponseEntity(new ApiResponse(false, "Question with given id does not exist!"),
                    HttpStatus.BAD_REQUEST);
        }

        Question question=questionService.findById(questionId).get();
        user.getAnswer().setQuestion(question);
        answerService.save(user.getAnswer());
        ApplicationUser result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();
                return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));

    }
}