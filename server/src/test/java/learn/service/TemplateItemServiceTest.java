package learn.service;

import learn.TestHelper;
import learn.data.TemplateItemRepository;
import learn.models.TemplateItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TemplateItemServiceTest {
    @Autowired
    TemplateItemService service;
    @MockBean
    TemplateItemRepository repository;

    private TemplateItem testTemplateItem;

    @BeforeEach
    void setUp(){
        testTemplateItem = TestHelper.makeTestTemplateItem();
    }

    @Nested
    public class CreateTests {
        @Test
        void shouldCreate() {
            TemplateItem beforeAdd = TestHelper.makeTestTemplateItem();
            beforeAdd.setTemplateItemId(0);
            TemplateItem afterAdd = TestHelper.makeTestTemplateItem();

            when(repository.create(beforeAdd)).thenReturn(afterAdd);
            Result<TemplateItem> expected = new Result<>();
            expected.setPayload(afterAdd);

            Result<TemplateItem> actual = service.create(beforeAdd);

            assertEquals(expected, actual);
        }
        @Test
        void shouldNotCreateNonpositiveQuantity(){
            TemplateItem beforeAdd = TestHelper.makeTestTemplateItem();
            beforeAdd.setTemplateItemId(0);
            beforeAdd.setQuantity(0);
            Result<TemplateItem> expected = new Result<>();
            expected.addErrorMessage("Quantity must be greater than 0.", ResultType.INVALID);

            Result<TemplateItem> actual = service.create(beforeAdd);

            assertEquals(expected, actual);
        }
        @Test
        void shouldNotCreateNullTemplate(){
            TemplateItem beforeAdd = TestHelper.makeTestTemplateItem();
            beforeAdd.setTemplateItemId(0);
            beforeAdd.setTemplate(null);
            Result<TemplateItem> expected = new Result<>();
            expected.addErrorMessage("Template is required.", ResultType.INVALID);

            Result<TemplateItem> actual = service.create(beforeAdd);

            assertEquals(expected, actual);
        }
        @Test
        void shouldNotCreateNullItem(){
            TemplateItem beforeAdd = TestHelper.makeTestTemplateItem();
            beforeAdd.setTemplateItemId(0);
            beforeAdd.setItem(null);
            Result<TemplateItem> expected = new Result<>();
            expected.addErrorMessage("Item is required.", ResultType.INVALID);

            Result<TemplateItem> actual = service.create(beforeAdd);

            assertEquals(expected, actual);
        }
    }
}