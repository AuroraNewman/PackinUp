package learn.data;

import learn.TestHelper;
import learn.models.PackingList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PackingListJdbcClientRepositoryTest {
    @Autowired
    JdbcClient client;
    @Autowired
    PackingListJdbcClientRepository repository;
    private PackingList testPackingList;
    @BeforeEach
    void setup() {
        client.sql("call set_known_good_state();").update();
        testPackingList = TestHelper.makeTestTemplate();
    }

    @Nested
    public class FindTests {
        @Test
        void shouldFindByName() {
            PackingList expected = TestHelper.existingPackingList;

            PackingList actual = repository.findByName(TestHelper.existingPackingList.getTemplateName());

            assertNotNull(actual);
            assertEquals(expected, actual);
        }

        @Test
        void shouldNotFindByMissingName() {
            PackingList actual = repository.findByName("Missing Name");

            assertNull(actual);
        }
        @Test
        void shouldFindAllListsByUserId(){
            PackingList packingList2 = new PackingList(2, "Vacation", "A trip for vacation purposes.", false, TestHelper.existingTripType, TestHelper.existingUser);
            PackingList packingList3 = new PackingList(3, "Family", "A trip for family purposes.", true, TestHelper.existingTripType, TestHelper.existingUser);
            List<PackingList> expected = List.of(TestHelper.existingPackingList, packingList2, packingList3);

            List<PackingList> actual = repository.findAllListsByUserId(TestHelper.existingPackingList.getTemplateUser().getUserId());

            assertNotNull(actual);
            assertEquals(expected, actual);
        }
        @Test
        void shouldNotFindAllListsByMissingUserId(){
            List<PackingList> expected = List.of();

            List<PackingList> actual = repository.findAllListsByUserId(TestHelper.badId);

            assertNotNull(actual);
            assertEquals(expected, actual);
        }
    }
    @Test
    void findTemplatesByUserId(){
        PackingList packingList1 = new PackingList(1, "General", "Not specified", true, TestHelper.existingTripType, TestHelper.existingUser);
        PackingList packingList2 = new PackingList(3, "Family", "A trip for family purposes.", true, TestHelper.existingTripType, TestHelper.existingUser);
        List<PackingList> expected = List.of(packingList1, packingList2);

        List<PackingList> actual = repository.findAllTemplatesByUserId(TestHelper.existingPackingList.getTemplateUser().getUserId());

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void shouldCreate() {
        PackingList beforeAdd = testPackingList;
        beforeAdd.setTemplateId(0);
        PackingList expected = TestHelper.makeTestTemplate();
        expected.setTemplateId(4);

        PackingList actual = repository.create(beforeAdd);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

}