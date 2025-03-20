package learn.models;

import learn.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

class UserTest {
    private Validator validator;
    private static User testUser;
    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        testUser = new User(TestHelper.goodId, TestHelper.goodUsername, TestHelper.goodEmail, TestHelper.goodPassword);
    }

    @Test
    void nonPositiveUserIdShouldFail() {
        User user = testUser;
        testUser.setUserId(TestHelper.badId);
        String expectedErrorMessage = "User ID must be greater than 0.";

        List<String> errorMessages = getErrorMessages(user);

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void longUsernameShouldFail() {
        User user = testUser;
        testUser.setUsername(TestHelper.tooLongVarCharString);
        String expectedErrorMessage = "Username must be fewer than 50 characters.";

        Set<ConstraintViolation<User>> violations = validator.validate(user);List<String> errorMessages = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations) {
            errorMessages.add(violation.getMessage());
        }

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void blankUsernameShouldFail(){
        User user = testUser;
        testUser.setUsername("");
        String expectedErrorMessage = "Username is required.";

        Set<ConstraintViolation<User>> violations = validator.validate(user);List<String> errorMessages = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations) {
            errorMessages.add(violation.getMessage());
        }

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void nullUsernameShouldFail(){
        User user = testUser;
        testUser.setUsername(null);
        String expectedErrorMessage = "Username is required.";

        Set<ConstraintViolation<User>> violations = validator.validate(user);List<String> errorMessages = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations) {
            errorMessages.add(violation.getMessage());
        }

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void longEmailShouldFail(){
        User user = testUser;
        testUser.setEmail(TestHelper.tooLongEmail);
        String expectedErrorMessage = "Email must be fewer than 100 characters.";

        Set<ConstraintViolation<User>> violations = validator.validate(user);List<String> errorMessages = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations) {
            errorMessages.add(violation.getMessage());
        }

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void blankEmailShouldFail(){
        User user = testUser;
        testUser.setEmail("");
        String expectedErrorMessage = "Email is required.";

        Set<ConstraintViolation<User>> violations = validator.validate(user);List<String> errorMessages = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations) {
            errorMessages.add(violation.getMessage());
        }

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void nullEmailShouldFail(){
        User user = testUser;
        testUser.setEmail(null);
        String expectedErrorMessage = "Email is required.";

        Set<ConstraintViolation<User>> violations = validator.validate(user);List<String> errorMessages = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations) {
            errorMessages.add(violation.getMessage());
        }

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void badFormatEmailShouldFail(){
        User user = testUser;
        testUser.setEmail(TestHelper.badFormatEmail);
        String expectedErrorMessage = "Email must be a valid email address.";

        Set<ConstraintViolation<User>> violations = validator.validate(user);List<String> errorMessages = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations) {
            errorMessages.add(violation.getMessage());
        }

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void blankPasswordShouldFail(){
        User user = testUser;
        testUser.setPassword("");
        String expectedErrorMessage = "Password is required.";

        List<String> errorMessages = getErrorMessages(user);

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void nullPasswordShouldFail(){
        User user = testUser;
        testUser.setPassword(null);
        String expectedErrorMessage = "Password is required.";

        List<String> errorMessages = getErrorMessages(user);

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void badFormatPasswordNoCapsShouldFail(){
        User user = testUser;
        testUser.setPassword(TestHelper.badFormatPasswordNoCaps);
        String expectedErrorMessage = "Password must contain at least one uppercase letter, one lowercase letter, one special character, and one number.";

        List<String> errorMessages = getErrorMessages(user);

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void badFormatPasswordNoSpecialShouldFail(){
        User user = testUser;
        testUser.setPassword(TestHelper.badFormatPasswordNoSpecial);
        String expectedErrorMessage = "Password must contain at least one uppercase letter, one lowercase letter, one special character, and one number.";

        List<String> errorMessages = getErrorMessages(user);

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void badFormatPasswordNoNumberShouldFail(){
        User user = testUser;
        testUser.setPassword(TestHelper.badFormatPasswordNoNumber);
        String expectedErrorMessage = "Password must contain at least one uppercase letter, one lowercase letter, one special character, and one number.";

        List<String> errorMessages = getErrorMessages(user);

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void badFormatPasswordNoLowersShouldFail(){
        User user = testUser;
        testUser.setPassword(TestHelper.badFormatPasswordNoLowers);
        String expectedErrorMessage = "Password must contain at least one uppercase letter, one lowercase letter, one special character, and one number.";

        List<String> errorMessages = getErrorMessages(user);

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void badFormatPasswordShortShouldFail(){
        User user = testUser;
        testUser.setPassword(TestHelper.badFormatPasswordShort);
        String expectedErrorMessage = "Password must be at least 8 characters long.";

        List<String> errorMessages = getErrorMessages(user);

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void shouldSucceed(){
        User user = testUser;

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertTrue(violations.isEmpty());
    }

    private List<String> getErrorMessages(User user){
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        List<String> errorMessages = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations) {
            errorMessages.add(violation.getMessage());
        }
        return errorMessages;
    }
}