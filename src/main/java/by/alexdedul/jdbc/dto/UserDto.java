package by.alexdedul.jdbc.dto;

import by.alexdedul.jdbc.entity.Gender;
import by.alexdedul.jdbc.entity.Role;
import lombok.*;

import java.time.LocalDate;

@Value
@Builder
public class UserDto {
    Integer id;
    String name;
    String email;
    LocalDate birthday;
    Role role;
    Gender gender;

}
