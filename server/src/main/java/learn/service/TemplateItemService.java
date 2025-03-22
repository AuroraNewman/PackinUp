package learn.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import learn.data.ItemRepository;
import learn.data.TemplateItemRepository;
import learn.data.TemplateRepository;
import learn.models.TemplateItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TemplateItemService {

    private final TemplateItemRepository repository;
    private final TemplateRepository templateRepository;
    private final ItemRepository itemRepository;

    public TemplateItemService(TemplateItemRepository repository, TemplateRepository templateRepository, ItemRepository itemRepository) {
        this.repository = repository;
        this.templateRepository = templateRepository;
        this.itemRepository = itemRepository;
    }

    public List<TemplateItem> findAllByTemplateId(int templateId) {
        return repository.findAllByTemplateId(templateId);
    }

    public Result<TemplateItem> create(TemplateItem templateItem) {
        Result<TemplateItem> result = validate(templateItem);

        if (!result.isSuccess()) {
            return result;
        }

        result.setPayload(repository.create(templateItem));
        return result;
    }

    private Result<TemplateItem> validate(TemplateItem templateItem) {
        Result<TemplateItem> result = new Result<>();

        if (templateItem == null){
            result.addErrorMessage("Template item is required.", ResultType.INVALID);
            return result;
        }

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Object>> violations = validator.validate(templateItem);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Object> violation : violations) {
                result.addErrorMessage(violation.getMessage(), ResultType.INVALID);
            }
        }
        checkParentsExist(templateItem, result);

        return result;
    }
    private void checkParentsExist(TemplateItem templateItem, Result<TemplateItem> result){
        checkTemplateExists(templateItem.getTemplateId(), result);
        checkItemExists(templateItem.getItemId(), result);
    }
    private void checkTemplateExists(int templateId, Result<TemplateItem> result){
        if (templateRepository.findById(templateId) == null){
            result.addErrorMessage("Template does not exist.", ResultType.INVALID);
        }
    }
    private void checkItemExists(int itemId, Result<TemplateItem> result){
        if (itemRepository.findById(itemId) == null){
            result.addErrorMessage("Item does not exist.", ResultType.INVALID);
        }
    }
}
