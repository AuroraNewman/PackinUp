package learn.models;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import learn.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {
    private Validator validator;
    private Category testCategory;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        testCategory = TestHelper.makeTestCategory();
    }

    @Test
    void shouldNotSetNegativeId() {
        testCategory.setCategoryId(-1);
        String expectedMessage = "Category ID must be greater than 0.";

        List<String> errorMessages = getErrorMessages(testCategory);

        assertEquals(1, errorMessages.size());
        assertTrue(errorMessages.contains(expectedMessage));
    }

    @Test
    void categoryNameShouldNotBeBlank(){
        testCategory.setCategoryName("              ");
        String expectedMessage = "Category name is required.";

        List<String> errorMessages = getErrorMessages(testCategory);

        assertEquals(1, errorMessages.size());
        assertTrue(errorMessages.contains(expectedMessage));
    }
    @Test
    void categoryNameShouldNotBeNull(){
        testCategory.setCategoryName(null);
        String expectedMessage = "Category name is required.";

        List<String> errorMessages = getErrorMessages(testCategory);

        assertEquals(1, errorMessages.size());
        assertTrue(errorMessages.contains(expectedMessage));
    }
    @Test
    void categoryNameShouldNotBeTooLong(){
        testCategory.setCategoryName(TestHelper.tooLongVarCharString);
        String expectedMessage = "Category name must be between 1 and 50 characters.";

        List<String> errorMessages = getErrorMessages(testCategory);

        assertEquals(1, errorMessages.size());
        assertTrue(errorMessages.contains(expectedMessage));
    }

    private List<String> getErrorMessages(Category category) {
        return validator.validate(category).stream()
                .map(violation -> violation.getMessage())
                .collect(Collectors.toList());
    }
}