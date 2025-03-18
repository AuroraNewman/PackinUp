package learn.data;

import learn.TestHelper;
import learn.models.User;
import org.junit.jupiter.api.BeforeEach;
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
    UserJdbcClientRepository repository;
    @BeforeEach
    void setup() {
        client.sql("call set_known_good_state();").update();
    }
    @Test
    void findById() {
        User user = repository.findById(1);

    }

    @Test
    void findByEmail() {
    }

    @Test
    void create() {
        User user = new User();
        user.setUsername(TestHelper.goodUsername);
        user.setEmail(TestHelper.goodEmail);
        user.setPassword(TestHelper.goodPassword);

        User actual = repository.create(user);
//        User user = repository.create(new User(TestHelper.goodUsername, TestHelper.goodEmail, TestHelper.goodPassword));

        assertNotNull(actual);
        assertEquals(4, actual.getUserId());
        //TODO
        //assertEquals actual, repository.findById(4);
    }

    @Test
    void updateUsername() {
    }

    @Test
    void delete() {
    }
}