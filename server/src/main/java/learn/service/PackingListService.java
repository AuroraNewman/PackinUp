package learn.service;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import learn.data.PackingListRepository;
import learn.models.PackingList;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PackingListService {
    private PackingListRepository repository;

    public PackingListService(PackingListRepository repository) {
        this.repository = repository;
    }
    private final String DUPLICATE_NAME_ERROR = "Template name already exists.";

    public Result<List<PackingList>> findAllListsByUserId(int userId) {
        Result<List<PackingList>> result = new Result<>();
        result.setPayload(repository.findAllListsByUserId(userId));
        return result;
    }
    public Result<List<PackingList>> findAllTemplatesByUserId(int userId) {
        Result<List<PackingList>> result = new Result<>();
        result.setPayload(repository.findAllTemplatesByUserId(userId));
        return result;
    }
    public Result<PackingList> create(PackingList packingList){
        Result<PackingList> result = validate(packingList);
       if (packingList != null && repository.findByName(packingList.getTemplateName()) != null){
              result.addErrorMessage(DUPLICATE_NAME_ERROR, ResultType.INVALID);
       }
        if (!result.isSuccess()){
            return result;
        }
        PackingList addedPackingList = new PackingList();
        try {
            addedPackingList = repository.create(packingList);
        } catch (DuplicateKeyException e) {
            result.addErrorMessage(DUPLICATE_NAME_ERROR, ResultType.INVALID);
            return result;
        }
        if (addedPackingList == null) {
            result.addErrorMessage("Template could not be created.", ResultType.INVALID);
        } else {
            result.setPayload(addedPackingList);
        }
        return result;
    }
    private Result<PackingList> validate(PackingList packingList) {
        Result<PackingList> result = new Result<>();

        if (packingList == null){
            result.addErrorMessage("Template is required.", ResultType.INVALID);
            return result;
        }

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Object>> violations = validator.validate(packingList);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Object> violation : violations) {
                result.addErrorMessage(violation.getMessage(), ResultType.INVALID);
            }
        }
        return result;
    }
}
