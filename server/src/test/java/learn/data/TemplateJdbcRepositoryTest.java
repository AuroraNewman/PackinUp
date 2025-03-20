package learn.data;

import learn.TestHelper;
import learn.models.Template;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        testTemplate = new Template(TestHelper.goodId, TestHelper.goodVarCharString, TestHelper.goodVarCharString, TestHelper.goodTripType, TestHelper.goodUser);
    }

    @Test
    void findById() {
    }

    @Test
    void shouldCreate() {
        Template beforeAdd = testTemplate;
        beforeAdd.setTemplateId(0);
        Template expected = new Template(4, TestHelper.goodTemplate.getTemplateName(), TestHelper.goodTemplate.getTemplateDescription(), TestHelper.goodTemplate.getTemplateTripType(), TestHelper.goodTemplate.getTemplateUser());

        Template actual = repository.create(beforeAdd);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

}