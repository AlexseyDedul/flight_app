package by.alexdedul.jdbc.service;

import by.alexdedul.jdbc.dao.UserDao;
import by.alexdedul.jdbc.dto.CreateUserDto;
import by.alexdedul.jdbc.dto.UserDto;
import by.alexdedul.jdbc.entity.User;
import by.alexdedul.jdbc.exception.ValidationException;
import by.alexdedul.jdbc.mapper.CreateUserMapper;
import by.alexdedul.jdbc.mapper.UserMapper;
import by.alexdedul.jdbc.validator.CreateUserValidator;
import by.alexdedul.jdbc.validator.ValidationResult;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService {
    private static final UserService INSTANCE = new UserService();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final UserDao userDao = UserDao.getInstance();
    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();

    public Integer create(CreateUserDto createUserDto) {
        ValidationResult validationResult = createUserValidator.isValid(createUserDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }

        User user = createUserMapper.mapFrom(createUserDto);
        userDao.save(user);

        return user.getId();
    }

    public Optional<UserDto> login(String email, String password) {
        return userDao.findByEmailAndPassword(email, password).map(userMapper::mapFrom);
    }

    public static UserService getInstance() {
        return INSTANCE;
    }

}
