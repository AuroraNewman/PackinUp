package learn.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import learn.data.UserRepository;
import learn.data_transfer_objects.UserWithIdOnly;
import learn.data_transfer_objects.UserToFindByEmail;
import learn.models.User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }
    public Result<User> findById(int userId) {
        Result<User> result = validate(new UserWithIdOnly(userId));
        if (!result.isSuccess()){
            return result;
        }

        User foundUser = repository.findById(userId);

        if (foundUser == null) {
            result.addErrorMessage("User not found.", ResultType.NOT_FOUND);
        } else {
            result.setPayload(foundUser);
        }
        return result;
    }
    public Result<User> findByEmail(String email) {
        Result<User> result = validate(new UserToFindByEmail(email));
        if (!result.isSuccess()){
            return result;
        }

        User foundUser = repository.findByEmail(email);
        if (foundUser == null) {
            result.addErrorMessage("User not found.", ResultType.NOT_FOUND);
        } else {
            result.setPayload(foundUser);
        }
        return result;
    }
    public Result<User> create(User user) {
        Result<User> result = validate(user);

        if (!result.isSuccess()){
            return result;
        }

        User addedUser = repository.create(user);

        if (addedUser == null) {
            result.addErrorMessage("User could not be created.", ResultType.INVALID);
        } else {
            result.setPayload(addedUser);
        }
        return result;

    }
    public Result<User> updateUsername(User user) {
        Result<User> result = validate(user);

        if (!result.isSuccess()){
            return result;
        }
        boolean updated = repository.updateUsername(user);
        if (!updated) {
            result.addErrorMessage("User could not be updated.", ResultType.INVALID);
        } else {
            result.setPayload(user);
        }
        return result;
    }

    public Result<Void> delete(int userId) {
        Result<Void> result = new Result<>();

        boolean deleted = false;

        if (userId <= 0) {
            result.addErrorMessage("User id must be greater than 0.", ResultType.INVALID);
            return result;
        } else {
            deleted = repository.delete(userId);
        }
        if (!deleted) {
            result.addErrorMessage("User not found.", ResultType.NOT_FOUND);
        }
        return result;
    }
    private Result<User> validate(Object object) {
        Result<User> result = new Result<>();

        if (object == null){
            result.addErrorMessage("User is required.", ResultType.INVALID);
            return result;
        }

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Object>> violations = validator.validate(object);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<Object> violation : violations) {
                result.addErrorMessage(violation.getMessage(), ResultType.INVALID);
            }
        }
        return result;
    }
}
