package learn.data;

import learn.TestHelper;
import learn.models.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ItemJdbcClientRepositoryTest {
    @Autowired
    JdbcClient client;
    @Autowired
    ItemJdbcClientRepository repository;
    @BeforeEach
    void setup() {
        client.sql("call set_known_good_state();").update();
    }
    @Test
    void findById() {
        Item expected = TestHelper.existingItem;

        Item actual = repository.findById(TestHelper.existingItem.getItemId());

        assertEquals(expected, actual);


    }
}