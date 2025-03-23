package learn.service;

import learn.data.ItemRepository;
import learn.models.Item;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    private final ItemRepository repository;

    public ItemService(ItemRepository repository) {
        this.repository = repository;
    }
    public Item findById(int itemId) {
        return repository.findById(itemId);
    }
}
