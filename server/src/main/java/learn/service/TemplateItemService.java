package learn.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import learn.data.TemplateItemRepository;
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
        return result;
    }

}
