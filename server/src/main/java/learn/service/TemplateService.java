package learn.service;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import learn.data.TemplateItemRepository;
import learn.data.TemplateRepository;
import learn.models.Template;
import learn.models.TemplateItem;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TemplateService {
    private TemplateRepository repository;
    private TemplateItemRepository templateItemRepository;

    public TemplateService(TemplateRepository repository, TemplateItemRepository templateItemRepository) {
        this.repository = repository;
        this.templateItemRepository = templateItemRepository;
    }

    private final String DUPLICATE_NAME_ERROR = "Template name already exists.";

    public Result<List<Template>> findByUserId(int userId) {
        Result<List<Template>> result = new Result<>();
        List<Template> templates = repository.findByUserId(userId);
        for (Template template : templates) {
            addItemsToTemplate(template);
        }
        result.setPayload(templates);
        return result;
    }
    public Result<Template> findById(int templateId) {
        Result<Template> result = new Result<>();
        Template foundTemplate = repository.findById(templateId);
        if (result.isSuccess()) {
            addItemsToTemplate(foundTemplate);
            result.setPayload(foundTemplate);
        } else {
            result.addErrorMessage("Template could not be found.", ResultType.NOT_FOUND);
        }
        return result;
    }
    public Result<Template> create(Template template){
        Result<Template> result = validate(template);

       if (template != null && repository.findByName(template.getTemplateName()) != null ){
              result.addErrorMessage(DUPLICATE_NAME_ERROR, ResultType.INVALID);
       }
        if (!result.isSuccess()){
            return result;
        }
        Template addedTemplate = repository.create(template);

        if (addedTemplate == null) {
            result.addErrorMessage("Template could not be created.", ResultType.INVALID);
        } else {
            addItemsBasedOnTripType(addedTemplate);
            result.setPayload(addedTemplate);
        }
        return result;
    }

    public Result<Template> update(Template template) {
        Result<Template> result = validate(template);
        Template existingTemplate = repository.findByName(template.getTemplateName());
        if (template != null && existingTemplate != null && existingTemplate.getTemplateId() != template.getTemplateId()){
            result.addErrorMessage(DUPLICATE_NAME_ERROR, ResultType.INVALID);
        }
        if (!result.isSuccess()){
            return result;
        }
        try {
            if (repository.update(template) == false) {
                result.addErrorMessage("Template could not be updated.", ResultType.NOT_FOUND);
            }
        } catch (DuplicateKeyException ex) {
            result.addErrorMessage(DUPLICATE_NAME_ERROR, ResultType.INVALID);
        }

        if (template.getTemplateTripType() != null && (!template.getTemplateTripType().equals(existingTemplate.getTemplateTripType()))) {
            addItemsBasedOnTripType(template);
        }

        result.setPayload(template);
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
    private void addItemsToTemplate(Template template) {
        List<TemplateItem> items = templateItemRepository.findAllByTemplateId(template.getTemplateId());
        template.setItems(items);
    }
    private void addItemsBasedOnTripType(Template template) {
        List<TemplateItem> items = templateItemRepository.findAllByTripTypeId(template.getTemplateTripType().getTripTypeId());
        template.setItems(items);
    }
}
