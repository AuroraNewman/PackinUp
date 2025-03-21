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
        void shouldFindAllListsByUserId(){
            Template template2 = new Template(2, "Vacation", "A trip for vacation purposes.", false, TestHelper.existingTripType, TestHelper.existingUser);
            Template template3 = new Template(3, "Family", "A trip for family purposes.", true, TestHelper.existingTripType, TestHelper.existingUser);
            List<Template> expected = List.of(TestHelper.existingTemplate, template2, template3);

            List<Template> actual = repository.findAllListsByUserId(TestHelper.existingTemplate.getTemplateUser().getUserId());

            assertNotNull(actual);
            assertEquals(expected, actual);
        }
        @Test
        void shouldNotFindAllListsByMissingUserId(){
            List<Template> expected = List.of();

            List<Template> actual = repository.findAllListsByUserId(TestHelper.badId);

            assertNotNull(actual);
            assertEquals(expected, actual);
        }
    }
    @Test
    void findTemplatesByUserId(){
        Template template1 = new Template(1, "General", "Not specified", true, TestHelper.existingTripType, TestHelper.existingUser);
        Template template2 = new Template(3, "Family", "A trip for family purposes.", true, TestHelper.existingTripType, TestHelper.existingUser);
        List<Template> expected = List.of(template1, template2);

        List<Template> actual = repository.findAllTemplatesByUserId(TestHelper.existingTemplate.getTemplateUser().getUserId());

        assertNotNull(actual);
        assertEquals(expected, actual);
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

}