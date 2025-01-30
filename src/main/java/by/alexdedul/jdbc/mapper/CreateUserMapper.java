package by.alexdedul.jdbc.mapper;

import by.alexdedul.jdbc.dto.CreateUserDto;
import by.alexdedul.jdbc.entity.Gender;
import by.alexdedul.jdbc.entity.Role;
import by.alexdedul.jdbc.entity.User;
import by.alexdedul.jdbc.utlis.LocalDateFormatter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserMapper implements Mapper<User, CreateUserDto> {
    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public User mapFrom(CreateUserDto value) {
        return User.builder()
                .name(value.getName())
                .birthday(LocalDateFormatter.format(value.getBirthday()))
                .email(value.getEmail())
                .password(value.getPassword())
                .role(Role.valueOf(value.getRole()))
                .gender(Gender.valueOf(value.getGender()))
                .build();
    }
}
