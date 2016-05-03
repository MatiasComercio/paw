package ar.edu.itba.paw.webapp.forms.validators;

import ar.edu.itba.paw.webapp.forms.PasswordForm;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class PasswordValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return PasswordForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PasswordForm pw = (PasswordForm) target;
        final String newPassword = pw.getNewPassword();
        final String repeatNewPassword = pw.getRepeatNewPassword();

        if(!newPassword.equals(repeatNewPassword)) {
            errors.rejectValue("repeatNewPassword", "repeatNewPassword.notequal");
        }
    }
}
