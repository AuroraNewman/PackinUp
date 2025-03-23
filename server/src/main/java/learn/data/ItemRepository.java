package learn.data;

import learn.models.Item;

public interface ItemRepository {
    Item findById(int id);
}
