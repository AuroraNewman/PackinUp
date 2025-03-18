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
    void failToFindBadId(){
        User actual = repository.findById(TestHelper.badId);

        assertNull(actual);
    }

    @Test
    void findByEmail() {
        User actual = repository.findByEmail(TestHelper.existingUser.getEmail());

        assertNotNull(actual);
        assertEquals(1, actual.getUserId());
        assertEquals(actual, TestHelper.existingUser);
    }

    @Test
    void failToFindByEmailBadEmail(){
        User actual = repository.findByEmail(TestHelper.tooLongEmail);

        assertNull(actual);
    }

    @Test
    void create() {
       User actual = repository.create(TestHelper.goodUser);

        assertNotNull(actual);
        assertEquals(4, actual.getUserId());
    }

    @Test
    void updateUsername() {
        boolean actual = repository.updateUsername(TestHelper.existingUser, "Bernard");

        assertTrue(actual);
        assertEquals("Bernard", repository.findById(TestHelper.existingUser.getUserId()).getUsername());
    }

    @Test
    void failToUpdateUsernameBadId() {
        User failUser = new User();
        failUser.setUserId(TestHelper.badId);
        failUser.setUsername("Bernard");
        failUser.setPassword(TestHelper.goodPassword);
        failUser.setEmail(TestHelper.goodEmail);

        boolean actual = repository.updateUsername(failUser, "Bernard");

        assertFalse(actual);
    }

    @Test
    void delete() {
        boolean actual = repository.delete(TestHelper.existingUser.getUserId());

        assertTrue(actual);
        assertNull(repository.findById(TestHelper.existingUser.getUserId()));
    }

    @Test
    void failToDeleteBadId(){
        boolean actual = repository.delete(TestHelper.badId);

        assertFalse(actual);
    }
}