package learn.service;

import learn.data.TripTypeRepository;
import learn.models.TripType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripTypeService {
    private final TripTypeRepository repository;

    public TripTypeService(TripTypeRepository repository) {
        this.repository = repository;
    }
    public Result<TripType> findById(int id) {
        Result<TripType> result = new Result<>();
        TripType tripType = repository.findById(id);
        if (tripType == null) {
            result.addErrorMessage("TripType not found", ResultType.NOT_FOUND);
        } else {
            result.setPayload(tripType);
        }
        return result;
    }
    public List<TripType> findAllByUserId(int userId) {
        return repository.findAllByUserId(userId);
    }
}
