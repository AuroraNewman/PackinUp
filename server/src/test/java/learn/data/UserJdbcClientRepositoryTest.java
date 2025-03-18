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
        User actual = repository.findById(TestHelper.existingUser.getUserId());

        assertNotNull(actual);
        assertEquals(1, actual.getUserId());
        assertEquals(actual, TestHelper.existingUser);
    }

    @Test
    void findByEmail() {
    }

    @Test
    void create() {
       User actual = repository.create(TestHelper.goodUser);

        assertNotNull(actual);
        assertEquals(4, actual.getUserId());
    }

    @Test
    void updateUsername() {
    }

    @Test
    void delete() {
    }
}