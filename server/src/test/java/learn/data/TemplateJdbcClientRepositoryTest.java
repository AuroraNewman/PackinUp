package learn.data;

import learn.TestHelper;
import learn.models.Template;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TemplateJdbcClientRepositoryTest {
    @Autowired
    JdbcClient client;
    @Autowired
    TemplateJdbcClientRepository repository;
    private Template testTemplate;
    @BeforeEach
    void setup() {
        client.sql("call set_known_good_state();").update();
        testTemplate = TestHelper.makeTestTemplate();
    }

    @Nested
    public class FindTests {
        @Test
        void shouldFindByName() {
            Template expected = TestHelper.existingTemplate;

            Template actual = repository.findByName(TestHelper.existingTemplate.getTemplateName());

            assertNotNull(actual);
            assertEquals(expected, actual);
        }

        @Test
        void shouldNotFindByMissingName() {
            Template actual = repository.findByName("Missing Name");

            assertNull(actual);
        }
        @Test
        void shouldFindByUserId(){
            List<Template> expected = List.of(TestHelper.existingTemplate);

            List<Template> actual = repository.findByUserId(TestHelper.existingTemplate.getTemplateUser().getUserId());

            assertNotNull(actual);
            assertEquals(expected, actual);
        }
        @Test
        void shouldNotFindByMissingUserId(){
            List<Template> expected = List.of();

            List<Template> actual = repository.findByUserId(TestHelper.badId);

            assertNotNull(actual);
            assertEquals(expected, actual);
        }
    }

    @Test
    void shouldCreate() {
        Template beforeAdd = testTemplate;
        beforeAdd.setTemplateId(0);
        Template expected = TestHelper.makeTestTemplate();
        expected.setTemplateId(4);

        Template actual = repository.create(beforeAdd);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }
    @Test
    void shouldNotCreateDuplicateName(){
        Template beforeAdd = testTemplate;
        beforeAdd.setTemplateId(0);
        beforeAdd.setTemplateName(TestHelper.existingTemplate.getTemplateName());

        assertThrows(DuplicateKeyException.class, () -> repository.create(beforeAdd));
    }

}