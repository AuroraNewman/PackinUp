package learn.service;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import learn.data.TemplateRepository;
import learn.models.Template;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TemplateService {
    private TemplateRepository repository;

    public TemplateService(TemplateRepository repository) {
        this.repository = repository;
    }
    private final String DUPLICATE_NAME_ERROR = "Template name already exists.";

    public Result<List<Template>> findByUserId(int userId) {
        Result<List<Template>> result = new Result<>();
        result.setPayload(repository.findByUserId(userId));
        return result;
    }
    public Result<Template> create(Template template){
        Result<Template> result = validate(template);
       if (template != null && repository.findByName(template.getTemplateName()) != null){
              result.addErrorMessage(DUPLICATE_NAME_ERROR, ResultType.INVALID);
       }
        if (!result.isSuccess()){
            return result;
        }
        Template addedTemplate = new Template();
        try {
            addedTemplate = repository.create(template);
        } catch (DuplicateKeyException e) {
            result.addErrorMessage(DUPLICATE_NAME_ERROR, ResultType.INVALID);
            return result;
        }
        if (addedTemplate == null) {
            result.addErrorMessage("Template could not be created.", ResultType.INVALID);
        } else {
            result.setPayload(addedTemplate);
        }
        return result;
    }
    private Result<Template> validate(Template template) {
        Result<Template> result = new Result<>();

        if (template == null){
            result.addErrorMessage("Template is required.", ResultType.INVALID);
            return result;
        }

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Object>> violations = validator.validate(template);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Object> violation : violations) {
                result.addErrorMessage(violation.getMessage(), ResultType.INVALID);
            }
        }
        return result;
    }
}
