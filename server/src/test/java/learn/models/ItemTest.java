package learn.models;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import learn.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    private Validator validator;
    private Item testItem;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        testItem = TestHelper.makeTestItem();
    }

    @Test
    void shouldNotSetNegativeId() {
        testItem.setItemId(-1);
        String expectedMessage = "Item ID must be greater than 0.";

        List<String> errorMessages = getErrorMessages(testItem);

        assertEquals(1, errorMessages.size());
        assertTrue(errorMessages.contains(expectedMessage));
    }

    @Test
    void itemNameShouldNotBeBlank(){
        testItem.setItemName("              ");
        String expectedMessage = "Item name is required.";

        List<String> errorMessages = getErrorMessages(testItem);

        assertEquals(1, errorMessages.size());
        assertTrue(errorMessages.contains(expectedMessage));
    }
    @Test
    void itemNameShouldNotBeNull(){
        testItem.setItemName(null);
        String expectedMessage = "Item name is required.";

        List<String> errorMessages = getErrorMessages(testItem);

        assertEquals(1, errorMessages.size());
        assertTrue(errorMessages.contains(expectedMessage));
    }
    @Test
    void itemNameShouldNotBeTooLong(){
        testItem.setItemName(TestHelper.tooLongVarCharString);
        String expectedMessage = "Item name must be between 1 and 50 characters.";

        List<String> errorMessages = getErrorMessages(testItem);

        assertEquals(1, errorMessages.size());
        assertTrue(errorMessages.contains(expectedMessage));
    }
    @Test
    void itemUserNotNull(){
        testItem.setUser(null);
        String expectedMessage = "User is required.";

        List<String> errorMessages = getErrorMessages(testItem);

        assertEquals(1, errorMessages.size());
        assertTrue(errorMessages.contains(expectedMessage));
    }
    @Test
    void itemCategoryNotNull(){
        testItem.setCategory(null);
        String expectedMessage = "Category is required.";

        List<String> errorMessages = getErrorMessages(testItem);

        assertEquals(1, errorMessages.size());
        assertTrue(errorMessages.contains(expectedMessage));
    }
    private List<String> getErrorMessages(Item item) {
        return validator.validate(item).stream()
                .map(violation -> violation.getMessage())
                .collect(Collectors.toList());
    }
}