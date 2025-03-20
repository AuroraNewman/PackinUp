package learn.models;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import learn.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class TripTypeTest {
    private Validator validator;
    private static TripType testTripType;
    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        testTripType = new TripType(TestHelper.goodId, TestHelper.goodVarCharString, TestHelper.goodVarCharString);
    }

    @Test
    void nonPositiveTripTypeIdIsInvalid() {
        testTripType.setTripTypeId(TestHelper.badId);
        String expectedErrorMessage = "Trip Type ID must be greater than or equal to 0.";

        List<String> errorMessages = getErrorMessages(testTripType);

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void tooLongTripTypeNameInvalid(){
        testTripType.setTripTypeName(TestHelper.tooLongTripTypeName);
        String expectedErrorMessage = "Trip Type Name must be between 1 and 50 characters.";

        List<String> errorMessages = getErrorMessages(testTripType);

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void blankTripTypeNameInvalid(){
        testTripType.setTripTypeName(" ");
        String expectedErrorMessage = "Trip Type Name is required.";

        List<String> errorMessages = getErrorMessages(testTripType);

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void nullTripTypeNameInvalid() {
        testTripType.setTripTypeName(null);
        String expectedErrorMessage = "Trip Type Name is required.";

        List<String> errorMessages = getErrorMessages(testTripType);
    }

    void blankTripTypeDescriptionInvalid(){
        testTripType.setTripTypeDescription(" ");
        String expectedErrorMessage = "Trip Type Description is required.";

        List<String> errorMessages = getErrorMessages(testTripType);

        assertTrue(errorMessages.contains(expectedErrorMessage));
    }
    @Test
    void nullTripTypeDescriptionInvalid() {
        testTripType.setTripTypeDescription(null);
        String expectedErrorMessage = "Trip Type Description is required.";

        List<String> errorMessages = getErrorMessages(testTripType);
    }



        private List<String> getErrorMessages(TripType tripType) {
        return validator.validate(tripType).stream()
                .map(violation -> violation.getMessage())
                .collect(Collectors.toList());
    }
}