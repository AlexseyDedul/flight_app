package by.alexdedul.jdbc.dto;

import lombok.*;

@Data
@Builder
public class UserDto {
    private Long id;
    private String email;
}
