package learn.data;

import learn.TestHelper;
import learn.models.TemplateItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TemplateItemJdbcClientRepositoryTest {
    @Autowired
    JdbcClient client;
    @Autowired
    TemplateItemJdbcClientRepository repository;

    @BeforeEach
    void setup() {
        client.sql("call set_known_good_state();").update();
    }
    @Test
    void create() {
        TemplateItem beforeAdd = TestHelper.makeTestTemplateItem();
        beforeAdd.setTemplateItemId(0);

        boolean actual = repository.create(beforeAdd);

        assertTrue(actual);
    }
}