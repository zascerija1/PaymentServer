package ba.unsa.etf.si.payment.controller.TestAnnotations;

import ba.unsa.etf.si.payment.request.LoginRequest;
import ba.unsa.etf.si.payment.request.TestAnnotations.TestRequest;
import ba.unsa.etf.si.payment.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/annotation")
public class TestAnnotationsController {

    @PostMapping("/test")
    public ResponseEntity<?> test(@Valid @RequestBody TestRequest testRequest, BindingResult result){
        if(result.hasErrors()) {
            return new ResponseEntity(new ApiResponse(false, "GRESKAA"),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(new ApiResponse(true, "PROSLO"),
                HttpStatus.BAD_REQUEST);
    }

}
