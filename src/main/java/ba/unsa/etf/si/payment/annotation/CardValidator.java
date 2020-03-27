package ba.unsa.etf.si.payment.annotation;

import ba.unsa.etf.si.payment.model.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CardValidator implements ConstraintValidator<CardValidation, String> {


   public void initialize(CardValidation constraint) {
   }

   public boolean isValid(String number, ConstraintValidatorContext context) {
      return number.matches("^[0-9]*$");
   }
}
