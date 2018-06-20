package ru.tsystems.javaschool.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.tsystems.javaschool.model.Driver;

import java.util.regex.Pattern;

@Component
public class DriverValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Driver.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Driver driver = (Driver) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                "name", "name", "Name is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                "surname", "surname", "Surname is required.");


        Pattern pattern = Pattern.compile("[a-z[A-Z[-[ ]]]]*");
        if(!pattern.matcher(driver.getName()).matches()){
            errors.rejectValue("name", "name",
                    "Name can contains only ' ', '-', a-z and A-Z symbols.");
        }
        if(!pattern.matcher(driver.getSurname()).matches()){
            errors.rejectValue("surname", "surname",
                    "Surname can contains only ' ', '-', a-z and A-Z symbols.");
        }
    }
}
