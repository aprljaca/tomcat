package com.tomcat.validation;

import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class PasswordConstraintValidator implements ConstraintValidator<CustomPassword, String> {
    
    @Override
    public void initialize(CustomPassword arg0) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 16), // min 8 and max 16 characters
                new CharacterRule(EnglishCharacterData.UpperCase, 1), // at least one lowercase
                new CharacterRule(EnglishCharacterData.LowerCase, 1), // at least one lowercase
                new CharacterRule(EnglishCharacterData.Digit, 1), // at least one digit
                new WhitespaceRule())); // no whitespaces

        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }

        // puts the first validation violation in response
        context.buildConstraintViolationWithTemplate(
                        validator.getMessages(result).get(0))
                .addConstraintViolation();

        return false;
    }
}