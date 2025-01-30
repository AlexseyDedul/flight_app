package by.alexdedul.jdbc.validator;

import by.alexdedul.jdbc.dto.CreateUserDto;
import by.alexdedul.jdbc.entity.Gender;
import by.alexdedul.jdbc.entity.Role;
import by.alexdedul.jdbc.utlis.LocalDateFormatter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserValidator implements Validator<CreateUserDto> {
    private static final CreateUserValidator INSTANCE = new CreateUserValidator();

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult isValid(CreateUserDto createUserDto) {
        ValidationResult validationResult = new ValidationResult();
        if(!LocalDateFormatter.isValid(createUserDto.getBirthday())){
            validationResult.add(Error.of("user.birthday", "Birthday is invalid"));
        }

        if(Gender.find(createUserDto.getGender()).isEmpty()){
            validationResult.add(Error.of("user.gender", "Gender is invalid"));
        }

        if(Role.find(createUserDto.getRole()).isEmpty()){
            validationResult.add(Error.of("user.role", "Role is invalid"));
        }

        if(createUserDto.getName().isEmpty()){
            validationResult.add(Error.of("user.name", "Name is invalid"));
        }
        if(createUserDto.getPassword().isEmpty()){
            validationResult.add(Error.of("user.password", "Password is invalid"));
        }
        if(createUserDto.getEmail().isEmpty()){
            validationResult.add(Error.of("user.email", "Email is invalid"));
        }

        return validationResult;
    }
}
