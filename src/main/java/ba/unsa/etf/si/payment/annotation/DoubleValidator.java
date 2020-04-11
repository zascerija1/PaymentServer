package ba.unsa.etf.si.payment.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

public class DoubleValidator implements ConstraintValidator<DoubleValidation, Double>{

    @Override
    public void initialize(DoubleValidation constraintAnnotation) {

    }

    @Override
    public boolean isValid(Double aDouble, ConstraintValidatorContext constraintValidatorContext) {
        if(aDouble == null) return false;
        return aDouble >= 0;
    }
}
