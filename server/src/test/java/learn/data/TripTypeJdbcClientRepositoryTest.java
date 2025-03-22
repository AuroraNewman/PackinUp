package learn.data;

import learn.TestHelper;
import learn.models.TripType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TripTypeJdbcClientRepositoryTest {
    @Autowired
    JdbcClient client;
    @Autowired
    TripTypeJdbcClientRepository repository;

    @BeforeEach
    void setup() {
        client.sql("call set_known_good_state();").update();
    }
    @Test
    void findById() {
        TripType expected = TestHelper.existingTripType;

        TripType actual = repository.findById(expected.getTripTypeId());

        assertEquals(expected, actual);
    }
    @Test
    void shouldNotFindMissingId() {
        TripType actual = repository.findById(TestHelper.badId);

        assertNull(actual);
    }
    @Test
    void shouldFindAllByUserId() {
        List<TripType> expected = List.of(TestHelper.existingTripType);

        List<TripType> actual = repository.findAllByUserId(TestHelper.goodId);

        assertEquals(expected, actual);
    }
    @Test
    void shouldNotFindAllByMissingUserId() {
        List<TripType> actual = repository.findAllByUserId(TestHelper.badId);

        assertEquals(0, actual.size());
    }
}