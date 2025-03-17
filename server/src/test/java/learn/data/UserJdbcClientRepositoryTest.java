package learn.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserJdbcClientRepositoryTest {
    @Autowired
    JdbcClient client;
    @Autowired
    UserRepository repository;
    @Test
    void findById() {
    }

    @Test
    void findByEmail() {
    }

    @Test
    void create() {

    }

    @Test
    void updateUsername() {
    }

    @Test
    void delete() {
    }
}