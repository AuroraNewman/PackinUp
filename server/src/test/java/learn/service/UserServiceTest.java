package learn.service;

import learn.TestHelper;
import learn.data.UserRepository;
import learn.models.User;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserServiceTest {
    @Autowired
    UserService service;

    @MockBean
    UserRepository repository;

    @Test
    void findById() {
    }

    @Test
    void findByEmail() {
    }
    @Nested
    public class CreateTests {
        @Test
        void create() {
            User toCreate = new User(
                    TestHelper.goodUser.getUsername(),
                    TestHelper.goodUser.getEmail(),
                    TestHelper.goodUser.getPassword()
            );
            User expectedUser = new User(1,
                    TestHelper.goodUser.getUsername(),
                    TestHelper.goodUser.getEmail(),
                    TestHelper.goodUser.getPassword()
            );
            Result<User> expected = new Result<>();
            expected.setPayload(expectedUser);
            when(repository.create(toCreate)).thenReturn(expectedUser);

            Result<User> actual = service.create(toCreate);

            assertEquals(expected, actual);
            assertEquals(expectedUser, actual.getPayload());
        }
        @Test
        void shouldNotCreateWhenUserIsNull() {
            Result<User> actual = service.create(null);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("User is required."));
        }
        @Test
        void shouldNotCreateLongUsername(){
            User toCreate = new User(
                    TestHelper.tooLongUsername,
                    TestHelper.goodUser.getEmail(),
                    TestHelper.goodUser.getPassword()
            );
            Result<User> actual = service.create(toCreate);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("Username must be fewer than 50 characters."));
        }
        @Test
        void shouldNotCreateBlankUsername(){
            User toCreate = new User(
                    "",
                    TestHelper.goodUser.getEmail(),
                    TestHelper.goodUser.getPassword()
            );
            Result<User> actual = service.create(toCreate);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("Username is required."));
        }
        @Test
        void shouldNotCreateNullUsername(){
            User toCreate = new User(
                    null,
                    TestHelper.goodUser.getEmail(),
                    TestHelper.goodUser.getPassword()
            );
            Result<User> actual = service.create(toCreate);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("Username is required."));
        }
        @Test
        void shouldNotCreateLongEmail(){
            User toCreate = new User(
                    TestHelper.goodUser.getUsername(),
                    TestHelper.tooLongEmail,
                    TestHelper.goodUser.getPassword()
            );
            Result<User> actual = service.create(toCreate);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("Email must be fewer than 100 characters."));
        }
        @Test
        void shouldNotCreateBlankEmail(){
            User toCreate = new User(
                    TestHelper.goodUser.getUsername(),
                    "",
                    TestHelper.goodUser.getPassword()
            );
            Result<User> actual = service.create(toCreate);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("Email is required."));
        }
        @Test
        void shouldNotCreateNullEmail(){
            User toCreate = new User(
                    TestHelper.goodUser.getUsername(),
                    null,
                    TestHelper.goodUser.getPassword()
            );
            Result<User> actual = service.create(toCreate);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("Email is required."));
        }
        @Test
        void shouldNotCreateInvalidEmailFormat(){
            User toCreate = new User(
                    TestHelper.goodUser.getUsername(),
                    TestHelper.badFormatEmail,
                    TestHelper.goodUser.getPassword()
            );
            Result<User> actual = service.create(toCreate);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("Email must be a valid email address."));
        }

        @Test
        void shouldNotCreateBlankPassword(){
            User toCreate = new User(
                    TestHelper.goodUser.getUsername(),
                    TestHelper.goodUser.getEmail(),
                    ""
            );
            Result<User> actual = service.create(toCreate);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("Password is required."));
        }
        @Test
        void shouldNotCreateNullPassword(){
            User toCreate = new User(
                    TestHelper.goodUser.getUsername(),
                    TestHelper.goodUser.getEmail(),
                    null
            );
            Result<User> actual = service.create(toCreate);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("Password is required."));
        }
        @Test
        void shouldNotCreateBadPasswordNoCaps(){
            User toCreate = new User(
                    TestHelper.goodUser.getUsername(),
                    TestHelper.goodUser.getEmail(),
                    TestHelper.badFormatPasswordNoCaps
            );
            Result<User> actual = service.create(toCreate);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("Password must contain at least one uppercase letter, one lowercase letter, one special character, and one number."));
        }
        @Test
        void shouldNotCreateBadPasswordNoSpecial(){
            User toCreate = new User(
                    TestHelper.goodUser.getUsername(),
                    TestHelper.goodUser.getEmail(),
                    TestHelper.badFormatPasswordNoSpecial
            );
            Result<User> actual = service.create(toCreate);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("Password must contain at least one uppercase letter, one lowercase letter, one special character, and one number."));
        }
        @Test
        void shouldNotCreateBadPasswordNoNumber(){
            User toCreate = new User(
                    TestHelper.goodUser.getUsername(),
                    TestHelper.goodUser.getEmail(),
                    TestHelper.badFormatPasswordNoNumber
            );
            Result<User> actual = service.create(toCreate);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("Password must contain at least one uppercase letter, one lowercase letter, one special character, and one number."));
        }
        @Test
        void shouldNotCreateBadPasswordNoLowers(){
            User toCreate = new User(
                    TestHelper.goodUser.getUsername(),
                    TestHelper.goodUser.getEmail(),
                    TestHelper.badFormatPasswordNoLowers
            );
            Result<User> actual = service.create(toCreate);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("Password must contain at least one uppercase letter, one lowercase letter, one special character, and one number."));
        }
        @Test
        void shouldNotCreateShortPassword(){
            User toCreate = new User(
                    TestHelper.goodUser.getUsername(),
                    TestHelper.goodUser.getEmail(),
                    TestHelper.badFormatPasswordShort
            );
            Result<User> actual = service.create(toCreate);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("Password must be at least 8 characters long."));
        }
    }

    @Test
    void updateUsername() {
    }

    @Test
    void delete() {
    }
}