package learn.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import learn.data.TemplateItemRepository;
import learn.models.Template;
import learn.models.TemplateItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TemplateItemService {

    private final TemplateItemRepository repository;

    public TemplateItemService(TemplateItemRepository repository) {
        this.repository = repository;
    }

    public List<TemplateItem> findAllByTemplateId(int templateId) {
        return repository.findAllByTemplateId(templateId);
    }

    public Result<TemplateItem> create(TemplateItem templateItem) {
        Result<TemplateItem> result = checkNulls(templateItem);
        if (!result.isSuccess()) {
            return result;
        }
        validate(templateItem, result);
        if (!result.isSuccess()) {
            return result;
        }

        result.setPayload(repository.create(templateItem));
        return result;
    }

    public Result<Void> deleteById(int templateItemId) {
        Result<Void> result = new Result<>();
        if (templateItemId <= 0) {
            result.addErrorMessage("Template item id must be greater than 0.", ResultType.INVALID);
            return result;
        }
        if (!repository.deleteById(templateItemId)) {
            result.addErrorMessage("Template item not found.", ResultType.NOT_FOUND);
        }
        return result;
    }

    public void addTemplateItemsToTemplate(Template template){
        for (TemplateItem templateItem : template.getItems()) {
            TemplateItem createdTemplateItem = repository.create(templateItem);
        }
    }

    private Result<TemplateItem> checkNulls(TemplateItem templateItem) {
        Result<TemplateItem> result = new Result<>();

        if (templateItem == null || templateItem.getOutgoingItem() == null) {
            result.addErrorMessage("Template item is required.", ResultType.INVALID);
            return result;
        }
        if (templateItem.getTemplate() == null) {
            result.addErrorMessage("Template is required.", ResultType.INVALID);
        }
        return result;
    }

    private Result<TemplateItem> validate(TemplateItem templateItem, Result<TemplateItem> result) {
//        Result<TemplateItem> result = new Result<>();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Object>> violations = validator.validate(templateItem);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Object> violation : violations) {
                result.addErrorMessage(violation.getMessage(), ResultType.INVALID);
            }
        }
        return result;
    }

}
