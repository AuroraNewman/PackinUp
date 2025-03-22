package learn.data;

import learn.models.TripType;

import java.util.List;

public interface TripTypeRepository {
    TripType findById(int id);
    List<TripType> findAllByUserId(int userId);
}
