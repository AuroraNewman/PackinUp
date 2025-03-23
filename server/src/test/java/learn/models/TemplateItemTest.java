package learn.models;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import learn.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class TemplateItemTest {
    private Validator validator;
    private static TemplateItem testTemplateItem;
    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        testTemplateItem = TestHelper.makeTestTemplateItem();
    }
    @Test
    void shouldNotSetNegativeId() {
        testTemplateItem.setTemplateItemId(-1);
        String expectedMessage = "Template Item ID must be greater than 0.";

        List<String> errorMessages = getErrorMessages(testTemplateItem);

        assertEquals(1, errorMessages.size());
        assertTrue(errorMessages.contains(expectedMessage));
    }
    @Test
    void shouldNotSetNonpositiveQuantity() {
        testTemplateItem.setQuantity(0);
        String expectedMessage = "Quantity must be greater than 0.";

        List<String> errorMessages = getErrorMessages(testTemplateItem);

        assertEquals(1, errorMessages.size());
        assertTrue(errorMessages.contains(expectedMessage));
    }
    @Test
    void shouldNotSetNullTemplate(){
        testTemplateItem.setTemplate(null);
        String expectedMessage = "Template is required.";

        List<String> errorMessages = getErrorMessages(testTemplateItem);

        assertEquals(1, errorMessages.size());
        assertTrue(errorMessages.contains(expectedMessage));
    }
    @Test
    void shouldNotSetNullItem(){
        testTemplateItem.setItem(null);
        String expectedMessage = "Item is required.";

        List<String> errorMessages = getErrorMessages(testTemplateItem);

        assertEquals(1, errorMessages.size());
        assertTrue(errorMessages.contains(expectedMessage));
    }

    private List<String> getErrorMessages(TemplateItem templateItem) {
        return validator.validate(templateItem).stream()
                .map(violation -> violation.getMessage())
                .collect(Collectors.toList());
    }
}