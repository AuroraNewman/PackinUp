package learn.data;

import learn.TestHelper;
import learn.models.Template;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.simple.JdbcClient;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TemplateJdbcRepositoryTest {
    @Autowired
    JdbcClient client;
    @Autowired
    TemplateJdbcRepository repository;
    private Template testTemplate;
    @BeforeEach
    void setup() {
        client.sql("call set_known_good_state();").update();
        testTemplate = TestHelper.makeTestTemplate();
    }

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