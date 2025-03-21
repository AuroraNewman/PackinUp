package learn.models;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import learn.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class PackingListTest {
    private Validator validator;
    private static PackingList testPackingList;
    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        testPackingList = TestHelper.makeTestTemplate();
    }
    @Test
    void nonPositiveTemplateIdIsInvalid() {
        testPackingList.setTemplateId(TestHelper.badId);
        String expectedErrorMessage = "Template ID must be greater than 0.";

        List<String> errorMessages = getErrorMessages(testPackingList);

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void tooLongTemplateNameInvalid(){
        testPackingList.setTemplateName(TestHelper.tooLongVarCharString);
        String expectedErrorMessage = "Template name must be between 1 and 50 characters.";

        List<String> errorMessages = getErrorMessages(testPackingList);

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void blankTemplateNameInvalid(){
        testPackingList.setTemplateName(" ");
        String expectedErrorMessage = "Template name is required.";

        List<String> errorMessages = getErrorMessages(testPackingList);

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void nullTemplateNameInvalid() {
        testPackingList.setTemplateName(null);
        String expectedErrorMessage = "Template name is required.";

        List<String> errorMessages = getErrorMessages(testPackingList);
    }

    void blankTemplateDescriptionInvalid(){
        testPackingList.setTemplateDescription(" ");
        String expectedErrorMessage = "Template Description is required.";

        List<String> errorMessages = getErrorMessages(testPackingList);

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }

    @Test
    void nullTemplateDescriptionInvalid() {
        testPackingList.setTemplateDescription(null);
        String expectedErrorMessage = "Template description is required.";

        List<String> errorMessages = getErrorMessages(testPackingList);

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }

    @Test
    void nullUserInvalid() {
        testPackingList.setTemplateUser(null);
        String expectedErrorMessage = "User is required.";

        List<String> errorMessages = getErrorMessages(testPackingList);

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }


    private List<String> getErrorMessages(PackingList packingList) {
        return validator.validate(packingList).stream()
                .map(violation -> violation.getMessage())
                .collect(Collectors.toList());
    }
}