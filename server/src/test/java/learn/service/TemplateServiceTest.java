package learn.service;

import jakarta.validation.constraints.NotNull;
import learn.TestHelper;
import learn.data.TemplateRepository;
import learn.data.UserRepository;
import learn.models.Template;
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
class TemplateServiceTest {
    @Autowired
    TemplateService service;

    @MockBean
    TemplateRepository repository;
    private Template testTemplate;
    @BeforeEach
    void setup() {
        testTemplate = new Template(TestHelper.goodTemplate.getTemplateId(), TestHelper.goodTemplate.getTemplateName(), TestHelper.goodTemplate.getTemplateDescription(), TestHelper.goodTemplate.getTemplateTripType(),TestHelper.goodTemplate.getTemplateUser());
    }
    @Nested
    public class CreateTests {
        @Test
        void shouldCreate() {
            Template beforeAdd = testTemplate;
            beforeAdd.setTemplateId(0);
            Template afterAdd = testTemplate;
            Result<Template> expected = new Result<>();
            expected.setPayload(afterAdd);

            when(repository.create(beforeAdd)).thenReturn(afterAdd);

            Result<Template> actual = service.create(beforeAdd);

            assertEquals(expected, actual);
            assertEquals(afterAdd, actual.getPayload());
            assertTrue(actual.getErrorMessages().isEmpty());
        }
        @Test
        void shouldNotCreateWhenTemplateIsNull() {
            Result<Template> expected = new Result<>();
            expected.addErrorMessage("Template is required.", ResultType.INVALID);

            Result<Template> actual = service.create(null);

            assertEquals(expected, actual);
        }
        @Test
        void shouldNotCreateWhenTemplateNameTooLong(){
            testTemplate.setTemplateName(TestHelper.tooLongVarCharString);
            Result<Template> expected = new Result<>();
            expected.addErrorMessage("Template name must be between 1 and 50 characters.", ResultType.INVALID);

            Result<Template> actual = service.create(testTemplate);

            assertEquals(expected, actual);
        }
        @Test
        void shouldNotCreateWhenTemplateNameIsNull() {
            testTemplate.setTemplateName(null);
            Result<Template> expected = new Result<>();
            expected.addErrorMessage("Template name is required.", ResultType.INVALID);

            Result<Template> actual = service.create(testTemplate);

            assertEquals(expected, actual);
        }
        @Test
        void shouldNotCreateWhenTemplateNameIsBlank() {
            testTemplate.setTemplateName("      ");
            Result<Template> expected = new Result<>();
            expected.addErrorMessage("Template name is required.", ResultType.INVALID);

            Result<Template> actual = service.create(testTemplate);

            assertEquals(expected, actual);
        }

        @Test
        void shouldNotCreateWhenTemplateDescriptionIsNull() {
            testTemplate.setTemplateDescription(null);
            Result<Template> expected = new Result<>();
            expected.addErrorMessage("Template description is required.", ResultType.INVALID);

            Result<Template> actual = service.create(testTemplate);

            assertEquals(expected, actual);
        }
        @Test
        void shouldNotCreateWhenTemplateUserIsNull() {
            testTemplate.setTemplateUser(null);
            Result<Template> expected = new Result<>();
            expected.addErrorMessage("User is required.", ResultType.INVALID);

            Result<Template> actual = service.create(testTemplate);

            assertEquals(expected, actual);
        }

        @Test
        void shouldNotCreateWhenTemplateNameAlreadyExists() {
            testTemplate.setTemplateName(TestHelper.existingTemplate.getTemplateName());
            Result<Template> expected = new Result<>();
            expected.addErrorMessage("Template name already exists.", ResultType.INVALID);

            when(repository.create(testTemplate)).thenThrow(DuplicateKeyException.class);

            Result<Template> actual = service.create(testTemplate);

            assertEquals(expected, actual);
        }

    }
}