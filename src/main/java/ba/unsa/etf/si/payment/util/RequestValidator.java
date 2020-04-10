package ba.unsa.etf.si.payment.util;

import ba.unsa.etf.si.payment.exception.BadRequestException;
import org.springframework.validation.BindingResult;

public class RequestValidator {
    public static void validateRequest(BindingResult result){
        if(result.hasErrors()) {
            throw new BadRequestException("Parameters not valid!");
        }
    }
}
