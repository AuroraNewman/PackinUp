package learn.data;

import learn.models.Item;

public interface ItemRepository {
    Item findById(int id);
    boolean create(Item item);
    Item findByName(String name);
}
