package learn.data;

import learn.data.mappers.UserMapper;
import learn.models.User;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserJdbcClientRepository implements UserRepository{
    private final String SELECT = """
            select user_id, username, email, `password`
            from users
            """;
    private JdbcClient jdbcClient;

    public UserJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public User findById(int userId) {
        final String sql = SELECT + " where user_id = :user_id;";
        return jdbcClient.sql(sql)
                .param("user_id", userId)
                .query(new UserMapper())
                .optional().orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        final String sql = SELECT + " where email = :email;";
        return jdbcClient.sql(sql)
                .param("email", email)
                .query(new UserMapper())
                .optional().orElse(null);
    }

    @Override
    public User create(User user) throws DuplicateFieldException {
        int rowsAffected = 0;
        final String sql = """
                insert into users (username, email, `password`)
                values (:username, :email, :`password`);
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
            rowsAffected = jdbcClient.sql(sql)
                    .param("username", user.getUsername())
                    .param("email", user.getEmail())
                    .param("`password`", user.getPassword())
                    .update(keyHolder, "user_id");

        if (rowsAffected <= 0) {
            return null;
        }
        user.setUserId(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public boolean updateUsername(User user) {
        final String sql = """
                update users
                set username = :username
                where user_id = :user_id;
                """;
        return jdbcClient.sql(sql)
                .param("username", user.getUsername())
                .param("user_id", user.getUserId())
                .update() > 0;

    }

    @Override
    public boolean delete(int userId) {
        final String sql = "delete from users where user_id = :user_id;";
        return jdbcClient.sql(sql)
                .param("user_id", userId)
                .update() > 0;
    }
}
