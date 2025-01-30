package by.alexdedul.jdbc.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateUserDto {
    String name;
    String birthday;
    String password;
    String email;
    String role;
    String gender;
}
