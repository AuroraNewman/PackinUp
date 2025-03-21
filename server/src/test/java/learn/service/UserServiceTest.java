package learn.service;

import learn.TestHelper;
import learn.data.UserRepository;
import learn.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserServiceTest {
    @Autowired
    UserService service;

    @MockBean
    UserRepository repository;

    private User testUser;
    private User testAddUser;
    @BeforeEach
    void setUp(){
        testUser = TestHelper.makeTestUser();
        testAddUser = TestHelper.makeTestUser();
        testAddUser.setUserId(0);
    }

    @Nested
    public class FindTests {
        @Test
        void findById() {
            Result<User> expected = new Result<>();
            expected.setPayload(TestHelper.existingUser);
            when(repository.findById(TestHelper.existingUser.getUserId())).thenReturn(TestHelper.existingUser);

            Result<User> actual = service.findById(TestHelper.existingUser.getUserId());

            assertEquals(expected, actual);
            assertEquals(TestHelper.existingUser, actual.getPayload());
        }

        @Test
        void shouldNotFindByMissingId() {
            Result<User> expected = new Result<>();
            expected.addErrorMessage("User not found.", ResultType.NOT_FOUND);
            when(repository.findById(TestHelper.badId)).thenReturn(null);

            Result<User> actual = service.findById(999);

            assertEquals(expected, actual);
        }

        @Test
        void shouldNotFindByInvalidId() {
            Result<User> expected = new Result<>();
            expected.addErrorMessage("User ID must be greater than 0.", ResultType.INVALID);

            Result<User> actual = service.findById(-1);

            assertEquals(expected, actual);
        }

        @Test
        void findByEmail() {
            Result<User> expected = new Result<>();
            expected.setPayload(TestHelper.existingUser);
            when(repository.findByEmail(TestHelper.existingUser.getEmail())).thenReturn(TestHelper.existingUser);

            Result<User> actual = service.findByEmail(TestHelper.existingUser.getEmail());

            assertEquals(expected, actual);
            assertEquals(TestHelper.existingUser, actual.getPayload());
        }

        @Test
        void shouldNotFindByMissingEmail() {
            String missingEmail = "absentemail@notthere.com";
            Result<User> expected = new Result<>();
            expected.addErrorMessage("User not found.", ResultType.NOT_FOUND);
            when(repository.findByEmail(missingEmail)).thenReturn(null);

            Result<User> actual = service.findByEmail(missingEmail);

            assertEquals(expected, actual);
        }
    }
    @Nested
    public class CreateTests {
        @Test
        void create() {
            User toCreate = testAddUser;
            User expectedUser = TestHelper.makeTestUser();
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
            User failCreate = testAddUser;
            failCreate.setUsername(TestHelper.tooLongVarCharString);

            Result<User> actual = service.create(failCreate);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("Username must be fewer than 50 characters."));
        }
        @Test
        void shouldNotCreateBlankUsername(){
            User failCreate = testAddUser;
            failCreate.setUsername("");

            Result<User> actual = service.create(failCreate);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("Username is required."));
        }
        @Test
        void shouldNotCreateNullUsername(){
            User failCreate = testAddUser;
            failCreate.setUsername(null);
            Result<User> actual = service.create(failCreate);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("Username is required."));
        }
        @Test
        void shouldNotCreateLongEmail(){
            User failCreate = testAddUser;
            failCreate.setEmail(TestHelper.tooLongEmail);
            Result<User> actual = service.create(failCreate);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("Email must be fewer than 100 characters."));
        }
        @Test
        void shouldNotCreateBlankEmail(){
            User failCreate = testAddUser;
            failCreate.setEmail("");
            Result<User> actual = service.create(failCreate);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("Email is required."));
        }
        @Test
        void shouldNotCreateNullEmail(){
            User failCreate = testAddUser;
            failCreate.setEmail(null);
            Result<User> actual = service.create(failCreate);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("Email is required."));
        }
        @Test
        void shouldNotCreateInvalidEmailFormat(){
            User failCreate = testAddUser;
            failCreate.setEmail(TestHelper.badFormatEmail);
            Result<User> actual = service.create(failCreate);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("Email must be a valid email address."));
        }

        @Test
        void shouldNotCreateBlankPassword(){
            User failCreate = testAddUser;
            failCreate.setPassword("");
            Result<User> actual = service.create(failCreate);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("Password is required."));
        }
        @Test
        void shouldNotCreateNullPassword(){
            User failCreate = testAddUser;
            failCreate.setPassword(null);
            Result<User> actual = service.create(failCreate);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("Password is required."));
        }
        @Test
        void shouldNotCreateBadPasswordNoCaps(){
            User failCreate = testAddUser;
            failCreate.setPassword(TestHelper.badFormatPasswordNoCaps);
            Result<User> actual = service.create(failCreate);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("Password must contain at least one uppercase letter, one lowercase letter, one special character, and one number."));
        }
        @Test
        void shouldNotCreateBadPasswordNoSpecial(){
            User failCreate = testAddUser;
            failCreate.setPassword(TestHelper.badFormatPasswordNoSpecial);
            Result<User> actual = service.create(failCreate);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("Password must contain at least one uppercase letter, one lowercase letter, one special character, and one number."));
        }
        @Test
        void shouldNotCreateBadPasswordNoNumber(){
            User failCreate = testAddUser;
            failCreate.setPassword(TestHelper.badFormatPasswordNoNumber);
            Result<User> actual = service.create(failCreate);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("Password must contain at least one uppercase letter, one lowercase letter, one special character, and one number."));
        }
        @Test
        void shouldNotCreateBadPasswordNoLowers(){
            User failCreate = testAddUser;
            failCreate.setPassword(TestHelper.badFormatPasswordNoLowers);
            Result<User> actual = service.create(failCreate);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("Password must contain at least one uppercase letter, one lowercase letter, one special character, and one number."));
        }
        @Test
        void shouldNotCreateShortPassword(){
            User failCreate = testAddUser;
            failCreate.setPassword(TestHelper.badFormatPasswordShort);
            Result<User> actual = service.create(failCreate);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("Password must be at least 8 characters long."));
        }
        @Test
        void shouldNotCreateDuplicateEmailUserUseError(){
            User failCreate = testAddUser;
            failCreate.setEmail(TestHelper.existingUser.getEmail());
            when(repository.create(failCreate)).thenThrow(DuplicateKeyException.class);
            Result<User> actual = service.create(failCreate);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("Email already exists."));

        }
        @Test
        void shouldNotCreateDuplicateEmailUserUseRepoCheck(){
            User failCreate = testAddUser;
            failCreate.setEmail(TestHelper.existingUser.getEmail());
            when(repository.findByEmail(failCreate.getEmail())).thenReturn(TestHelper.existingUser);
            Result<User> actual = service.create(failCreate);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("Email already exists."));

        }
    }
    @Nested
    public class UpdateTests {
        @Test
        void shouldUpdateUsername() {
            User toUpdate = new User(
                    TestHelper.existingUser.getUserId(),
                    TestHelper.goodUsername,
                    TestHelper.existingUser.getEmail(),
                    TestHelper.existingUser.getPassword()
            );
            Result<User> expected = new Result<>();
            expected.setPayload(toUpdate);
            when(repository.updateUsername(toUpdate)).thenReturn(true);

            Result<User> actual = service.updateUsername(toUpdate);

            assertEquals(expected, actual);
            assertEquals(toUpdate, actual.getPayload());
        }
        @Test
        void shouldNotUpdateWhenUserIsNull() {
            Result<User> actual = service.updateUsername(null);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("User is required."));
        }
        @Test
        void shouldNotUpdateLongUsername(){
            User toUpdate = new User(
                    TestHelper.existingUser.getUserId(),
                    TestHelper.tooLongVarCharString,
                    TestHelper.existingUser.getEmail(),
                    TestHelper.existingUser.getPassword()
            );
            Result<User> actual = service.updateUsername(toUpdate);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("Username must be fewer than 50 characters."));
        }
        @Test
        void shouldNotUpdateBlankUsername(){
            User toUpdate = new User(
                    TestHelper.existingUser.getUserId(),
                    "",
                    TestHelper.existingUser.getEmail(),
                    TestHelper.existingUser.getPassword()
            );
            Result<User> actual = service.updateUsername(toUpdate);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("Username is required."));
        }
        @Test
        void shouldNotUpdateNullUsername(){
            User toUpdate = new User(
                    TestHelper.existingUser.getUserId(),
                    null,
                    TestHelper.existingUser.getEmail(),
                    TestHelper.existingUser.getPassword()
            );
            Result<User> actual = service.updateUsername(toUpdate);

            assertFalse(actual.isSuccess());
            assertTrue(actual.getErrorMessages().contains("Username is required."));
        }
    }

    @Nested
    public class DeleteTests {
        @Test
        void shouldDelete() {
            Result<Void> expected = new Result<>();
            when(repository.delete(TestHelper.existingUser.getUserId())).thenReturn(true);

            Result<Void> actual = service.delete(TestHelper.existingUser.getUserId());

            assertEquals(expected, actual);
        }
        @Test
        void shouldNotDeleteInvalidId(){
            Result<Void> expected = new Result<>();
            expected.addErrorMessage("User id must be greater than 0.", ResultType.INVALID);

            Result<Void> actual = service.delete(TestHelper.badId);

            assertEquals(expected, actual);
        }
        @Test
        void shouldNotDeleteMissingId(){
            Result<Void> expected = new Result<>();
            expected.addErrorMessage("User not found.", ResultType.NOT_FOUND);
            when(repository.delete(-1 * TestHelper.badId)).thenReturn(false);

            Result<Void> actual = service.delete(-1 * TestHelper.badId);

            assertEquals(expected, actual);
        }
    }
}