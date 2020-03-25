package ba.unsa.etf.si.payment.controller;


import ba.unsa.etf.si.payment.exception.ResourceNotFoundException;
import ba.unsa.etf.si.payment.model.ApplicationUser;
import ba.unsa.etf.si.payment.repository.AnswerRepository;
import ba.unsa.etf.si.payment.repository.QuestionRepository;
import ba.unsa.etf.si.payment.service.ApplicationUserService;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class ApplicationUserController {

    private final AnswerRepository answerRepository;

    private final QuestionRepository questionRepository;

    private final ApplicationUserService applicationUserService;

    public ApplicationUserController(AnswerRepository answerRepository, QuestionRepository questionRepository, ApplicationUserService applicationUserService) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.applicationUserService = applicationUserService;
    }

    @GetMapping("/{userId}")
    public ApplicationUser getUserByUserId(@PathVariable Long userId) {

        return applicationUserService.find(userId);
    }

    @GetMapping("/all")
    public List<ApplicationUser> getUsers() {

        return applicationUserService.getAll();
    }

    @PostMapping("/{questionId}/register")
    public ApplicationUser addUser(@PathVariable Long questionId,
                                   @Valid @RequestBody ApplicationUser applicationUser) {
        return questionRepository.findById(questionId)
                .map(question -> {
                    applicationUser.getAnswer().setQuestion(question);
                    answerRepository.save(applicationUser.getAnswer());
                    //Ovo ce ovako izgledati kada dodamo fajl web config i java bean
                    // applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
                    return applicationUserService.save(applicationUser);
                }).orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + questionId));
    }
}