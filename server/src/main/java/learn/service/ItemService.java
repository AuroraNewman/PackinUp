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
    public Result<Item> create(Item item) {
        Result<Item> result = new Result<>();
        if (repository.create(item)) {
            result.setPayload(item);
        } else {
        result.addErrorMessage("Unable to create item", ResultType.INVALID);
        }
        return result;
    }
}
