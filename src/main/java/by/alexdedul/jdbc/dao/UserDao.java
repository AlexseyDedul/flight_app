package by.alexdedul.jdbc.dao;

import by.alexdedul.jdbc.entity.Gender;
import by.alexdedul.jdbc.entity.Role;
import by.alexdedul.jdbc.entity.User;
import by.alexdedul.jdbc.exception.DaoException;
import by.alexdedul.jdbc.utlis.ConnectionManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao implements Dao<Long, User> {
    private static final UserDao INSTANCE = new UserDao();

    private static final String SAVE_SQL = """
            INSERT INTO users (name, birthday, email, password, role, gender) 
            VALUES (?, ?, ?, ?, ?, ?)
            """;

    private static final String GET_BY_EMAIL_AND_PASSWORD_SQL = """
            SELECT * 
            FROM users
            WHERE email = ? AND password = ?
            """;

    public static UserDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(GET_BY_EMAIL_AND_PASSWORD_SQL)) {
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            User user = null;

            if (resultSet.next()) {
                user = buildUserEntity(resultSet);
            }

            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private User buildUserEntity(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .birthday(resultSet.getObject("birthday", Date.class).toLocalDate())
                .email(resultSet.getString("email"))
                .password(resultSet.getString("password"))
                .role(Role.find(resultSet.getString("role")).orElse(null))
                .gender(Gender.valueOf(resultSet.getString("gender")))
                .build();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    @SneakyThrows
    public User save(User user) {
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)){
            statement.setObject(1, user.getName());
            statement.setObject(2, user.getBirthday());
            statement.setObject(3, user.getEmail());
            statement.setObject(4, user.getPassword());
            statement.setObject(5, user.getRole().name());
            statement.setObject(6, user.getGender().name());

            System.out.println(statement);

            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if(keys.next()){
                user.setId(keys.getInt("id"));
            }

            return user;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
