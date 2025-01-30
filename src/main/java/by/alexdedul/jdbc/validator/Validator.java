package by.alexdedul.jdbc.validator;

public interface Validator <T>{
    ValidationResult isValid(T t);
}
