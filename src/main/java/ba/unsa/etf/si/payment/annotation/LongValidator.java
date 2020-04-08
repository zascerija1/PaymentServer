package ba.unsa.etf.si.payment.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LongValidator implements ConstraintValidator<LongValidation, Long> {
    @Override
    public void initialize(LongValidation constraintAnnotation) {

    }

    @Override
    public boolean isValid(Long aLong, ConstraintValidatorContext constraintValidatorContext) {
        return aLong != null;
    }
}
