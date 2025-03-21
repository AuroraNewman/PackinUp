package learn.data;

import learn.models.PackingList;

import java.util.List;

public interface PackingListRepository {
    PackingList findByName(String templateName);
    List<PackingList> findAllListsByUserId(int userId);
    List<PackingList> findAllTemplatesByUserId(int userId);
    PackingList create(PackingList packingList);
}
