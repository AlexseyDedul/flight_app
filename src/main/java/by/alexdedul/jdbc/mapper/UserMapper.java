package by.alexdedul.jdbc.mapper;

import by.alexdedul.jdbc.dto.UserDto;
import by.alexdedul.jdbc.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper implements Mapper<UserDto, User>{
    private static final UserMapper INSTANCE = new UserMapper();

    public static UserMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public UserDto mapFrom(User value) {
        return UserDto.builder()
                .id(value.getId())
                .name(value.getName())
                .email(value.getEmail())
                .birthday(value.getBirthday())
                .role(value.getRole())
                .gender(value.getGender())
                .build();
    }
}
