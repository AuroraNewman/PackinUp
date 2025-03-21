package learn.data;

import learn.models.TripType;

public interface TripTypeRepository {
    TripType findById(int id);
}
