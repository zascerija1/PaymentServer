package ba.unsa.etf.si.payment.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LongValidator.class)
public @interface LongValidation {
    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
