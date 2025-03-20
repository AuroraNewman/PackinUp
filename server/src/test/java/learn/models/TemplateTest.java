package learn.models;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import learn.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class TemplateTest {
    private Validator validator;
    private static Template testTemplate;
    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        testTemplate = new Template(TestHelper.goodId, TestHelper.goodVarCharString, TestHelper.goodVarCharString, TestHelper.goodTripType, TestHelper.goodUser);
    }
    @Test
    void nonPositiveTemplateIdIsInvalid() {
        testTemplate.setTemplateId(TestHelper.badId);
        String expectedErrorMessage = "Template ID must be greater than 0.";

        List<String> errorMessages = getErrorMessages(testTemplate);

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void tooLongTemplateNameInvalid(){
        testTemplate.setTemplateName(TestHelper.tooLongVarCharString);
        String expectedErrorMessage = "Template name must be between 1 and 50 characters.";

        List<String> errorMessages = getErrorMessages(testTemplate);

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void blankTemplateNameInvalid(){
        testTemplate.setTemplateName(" ");
        String expectedErrorMessage = "Template name is required.";

        List<String> errorMessages = getErrorMessages(testTemplate);

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void nullTemplateNameInvalid() {
        testTemplate.setTemplateName(null);
        String expectedErrorMessage = "Template name is required.";

        List<String> errorMessages = getErrorMessages(testTemplate);
    }

    void blankTemplateDescriptionInvalid(){
        testTemplate.setTemplateDescription(" ");
        String expectedErrorMessage = "Template Description is required.";

        List<String> errorMessages = getErrorMessages(testTemplate);

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    
    @Test
    void nullTemplateDescriptionInvalid() {
        testTemplate.setTemplateDescription(null);
        String expectedErrorMessage = "Template description is required.";

        List<String> errorMessages = getErrorMessages(testTemplate);

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }

    @Test
    void nullUserInvalid() {
        testTemplate.setTemplateUser(null);
        String expectedErrorMessage = "User is required.";

        List<String> errorMessages = getErrorMessages(testTemplate);

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }


    private List<String> getErrorMessages(Template template) {
        return validator.validate(template).stream()
                .map(violation -> violation.getMessage())
                .collect(Collectors.toList());
    }
}