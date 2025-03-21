package learn.service;

import learn.TestHelper;
import learn.data.PackingListRepository;
import learn.models.PackingList;
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
class PackingListServiceTest {
    @Autowired
    PackingListService service;

    @MockBean
    PackingListRepository repository;
    private PackingList testPackingList;
    @BeforeEach
    void setup() {
        testPackingList = TestHelper.makeTestTemplate();
    }
    @Nested
    public class FindTests {

    }
    @Nested
    public class CreateTests {
        @Test
        void shouldCreate() {
            PackingList beforeAdd = testPackingList;
            beforeAdd.setTemplateId(0);
            PackingList afterAdd = testPackingList;
            Result<PackingList> expected = new Result<>();
            expected.setPayload(afterAdd);

            when(repository.create(beforeAdd)).thenReturn(afterAdd);

            Result<PackingList> actual = service.create(beforeAdd);

            assertEquals(expected, actual);
            assertEquals(afterAdd, actual.getPayload());
            assertTrue(actual.getErrorMessages().isEmpty());
        }
        @Test
        void shouldNotCreateWhenTemplateIsNull() {
            Result<PackingList> expected = new Result<>();
            expected.addErrorMessage("Template is required.", ResultType.INVALID);

            Result<PackingList> actual = service.create(null);

            assertEquals(expected, actual);
        }
        @Test
        void shouldNotCreateWhenTemplateNameTooLong(){
            PackingList failAdd = testPackingList;
            failAdd.setTemplateName(TestHelper.tooLongVarCharString);
            Result<PackingList> expected = new Result<>();
            expected.addErrorMessage("Template name must be between 1 and 50 characters.", ResultType.INVALID);

            Result<PackingList> actual = service.create(failAdd);

            assertEquals(expected, actual);
        }
        @Test
        void shouldNotCreateWhenTemplateNameIsNull() {
            PackingList failAdd = testPackingList;
            failAdd.setTemplateName(null);
            Result<PackingList> expected = new Result<>();
            expected.addErrorMessage("Template name is required.", ResultType.INVALID);

            Result<PackingList> actual = service.create(failAdd);

            assertEquals(expected, actual);
        }
        @Test
        void shouldNotCreateWhenTemplateNameIsBlank() {
            PackingList failAdd = testPackingList;
            failAdd.setTemplateName(" ");
            failAdd.setTemplateId(0);
            Result<PackingList> expected = new Result<>();
            expected.addErrorMessage("Template name is required.", ResultType.INVALID);

            Result<PackingList> actual = service.create(failAdd);

            assertEquals(expected, actual);
        }

        @Test
        void shouldNotCreateWhenTemplateDescriptionIsNull() {
            PackingList failAdd = testPackingList;
            failAdd.setTemplateDescription(null);
            Result<PackingList> expected = new Result<>();
            expected.addErrorMessage("Template description is required.", ResultType.INVALID);

            Result<PackingList> actual = service.create(failAdd);

            assertEquals(expected, actual);
        }
        @Test
        void shouldNotCreateWhenTemplateUserIsNull() {
            PackingList failAdd = testPackingList;
            failAdd.setTemplateUser(null);
            Result<PackingList> expected = new Result<>();
            expected.addErrorMessage("User is required.", ResultType.INVALID);

            Result<PackingList> actual = service.create(failAdd);

            assertEquals(expected, actual);
        }

        @Test
        void shouldNotCreateWhenTemplateNameAlreadyExists() {
            PackingList failAdd = testPackingList;
            failAdd.setTemplateName(TestHelper.existingPackingList.getTemplateName());
            Result<PackingList> expected = new Result<>();
            expected.addErrorMessage("Template name already exists.", ResultType.INVALID);

            when(repository.create(failAdd)).thenThrow(DuplicateKeyException.class);

            Result<PackingList> actual = service.create(failAdd);

            assertEquals(expected, actual);
        }

    }
}