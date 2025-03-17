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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

class UserTest {
    private Validator validator;
    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void nonPositiveUserIdShouldFail() {
        User user = new User(TestHelper.badId, TestHelper.goodUsername, TestHelper.goodEmail, TestHelper.goodPassword);
        String expectedErrorMessage = "User ID must be greater than 0.";

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        List<String> errorMessages = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations) {
            errorMessages.add(violation.getMessage());
        }

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void longUsernameShouldFail() {
        User user = new User(TestHelper.goodId, TestHelper.tooLongUsername, TestHelper.goodEmail, TestHelper.goodPassword);
        String expectedErrorMessage = "Customer username must be fewer than 50 characters.";

        Set<ConstraintViolation<User>> violations = validator.validate(user);List<String> errorMessages = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations) {
            errorMessages.add(violation.getMessage());
        }

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void blankUsernameShouldFail(){
        User user = new User(TestHelper.goodId, "", TestHelper.goodEmail, TestHelper.goodPassword);
        String expectedErrorMessage = "Customer username is required.";

        Set<ConstraintViolation<User>> violations = validator.validate(user);List<String> errorMessages = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations) {
            errorMessages.add(violation.getMessage());
        }

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void nullUsernameShouldFail(){
        User user = new User(TestHelper.goodId, null, TestHelper.goodEmail, TestHelper.goodPassword);
        String expectedErrorMessage = "Customer username is required.";

        Set<ConstraintViolation<User>> violations = validator.validate(user);List<String> errorMessages = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations) {
            errorMessages.add(violation.getMessage());
        }

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void longEmailShouldFail(){
        User user = new User(TestHelper.goodId, TestHelper.goodUsername, TestHelper.tooLongEmail, TestHelper.goodPassword);
        String expectedErrorMessage = "Customer email must be fewer than 100 characters.";

        Set<ConstraintViolation<User>> violations = validator.validate(user);List<String> errorMessages = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations) {
            errorMessages.add(violation.getMessage());
        }

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void blankEmailShouldFail(){
        User user = new User(TestHelper.goodId, TestHelper.goodUsername, "", TestHelper.goodPassword);
        String expectedErrorMessage = "Customer email is required.";

        Set<ConstraintViolation<User>> violations = validator.validate(user);List<String> errorMessages = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations) {
            errorMessages.add(violation.getMessage());
        }

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void nullEmailShouldFail(){
        User user = new User(TestHelper.goodId, TestHelper.goodUsername, null, TestHelper.goodPassword);
        String expectedErrorMessage = "Customer email is required.";

        Set<ConstraintViolation<User>> violations = validator.validate(user);List<String> errorMessages = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations) {
            errorMessages.add(violation.getMessage());
        }

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void badFormatEmailShouldFail(){
        User user = new User(TestHelper.goodId, TestHelper.goodUsername, TestHelper.badFormatEmail, TestHelper.goodPassword);
        String expectedErrorMessage = "Customer email must be a valid email address.";

        Set<ConstraintViolation<User>> violations = validator.validate(user);List<String> errorMessages = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations) {
            errorMessages.add(violation.getMessage());
        }

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void blankPasswordShouldFail(){
        User user = new User(TestHelper.goodId, TestHelper.goodUsername, TestHelper.goodEmail, "");
        String expectedErrorMessage = "Customer password is required.";

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        List<String> errorMessages = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations) {
            errorMessages.add(violation.getMessage());
        }

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void nullPasswordShouldFail(){
        User user = new User(TestHelper.goodId, TestHelper.goodUsername, TestHelper.goodEmail, null);
        String expectedErrorMessage = "Customer password is required.";

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        List<String> errorMessages = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations) {
            errorMessages.add(violation.getMessage());
        }

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void badFormatPasswordNoCapsShouldFail(){
        User user = new User(TestHelper.goodId, TestHelper.goodUsername, TestHelper.goodEmail, TestHelper.badFormatPasswordNoCaps);
        String expectedErrorMessage = "Password must contain at least one uppercase letter, one lowercase letter, one special character, and one number.";

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        List<String> errorMessages = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations) {
            errorMessages.add(violation.getMessage());
        }

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void badFormatPasswordNoSpecialShouldFail(){
        User user = new User(TestHelper.goodId, TestHelper.goodUsername, TestHelper.goodEmail, TestHelper.badFormatPasswordNoSpecial);
        String expectedErrorMessage = "Password must contain at least one uppercase letter, one lowercase letter, one special character, and one number.";

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        List<String> errorMessages = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations) {
            errorMessages.add(violation.getMessage());
        }

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void badFormatPasswordNoNumberShouldFail(){
        User user = new User(TestHelper.goodId, TestHelper.goodUsername, TestHelper.goodEmail, TestHelper.badFormatPasswordNoNumber);
        String expectedErrorMessage = "Password must contain at least one uppercase letter, one lowercase letter, one special character, and one number.";

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        List<String> errorMessages = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations) {
            errorMessages.add(violation.getMessage());
        }

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void badFormatPasswordNoLowersShouldFail(){
        User user = new User(TestHelper.goodId, TestHelper.goodUsername, TestHelper.goodEmail, TestHelper.badFormatPasswordNoLowers);
        String expectedErrorMessage = "Password must contain at least one uppercase letter, one lowercase letter, one special character, and one number.";

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        List<String> errorMessages = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations) {
            errorMessages.add(violation.getMessage());
        }

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void badFormatPasswordShortShouldFail(){
        User user = new User(TestHelper.goodId, TestHelper.goodUsername, TestHelper.goodEmail, TestHelper.badFormatPasswordShort);
        String expectedErrorMessage = "Password must be at least 8 characters long.";

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        List<String> errorMessages = new ArrayList<>();
        for (ConstraintViolation<User> violation : violations) {
            errorMessages.add(violation.getMessage());
        }

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void shouldSucceed(){
        User user = new User(TestHelper.goodId, TestHelper.goodUsername, TestHelper.goodEmail, TestHelper.goodPassword);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertTrue(violations.isEmpty());
    }

    /*
     @Test
    void emptyTicketShouldFailValidation() {
        Ticket ticket = new Ticket();

        // Grab a Validator instance and validate the ticket.

        Set<ConstraintViolation<Ticket>> violations = validator.validate(ticket);

        assertEquals(9, violations.size());
    }
     */
}