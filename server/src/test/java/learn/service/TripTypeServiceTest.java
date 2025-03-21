package learn.service;

import learn.TestHelper;
import learn.data.TripTypeRepository;
import learn.models.TripType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TripTypeServiceTest {
    @Autowired
    TripTypeService service;

    @MockBean
    TripTypeRepository repository;

    @Test
    void findById() {
        Result<TripType> expected = new Result<>();
        expected.setPayload(TestHelper.existingTripType);
        when(repository.findById(TestHelper.existingTripType.getTripTypeId())).thenReturn(TestHelper.existingTripType);

        Result<TripType> actual = service.findById(TestHelper.existingTripType.getTripTypeId());

        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindByMissingId() {
        Result<TripType> expected = new Result<>();
        expected.addErrorMessage("TripType not found", ResultType.NOT_FOUND);
        when(repository.findById(TestHelper.badId)).thenReturn(null);

        Result<TripType> actual = service.findById(TestHelper.badId);

        assertEquals(expected, actual);
    }
}