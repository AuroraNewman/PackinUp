package learn.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import learn.data.UserRepository;
import learn.models.User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }
    public User findById(int userId) {
        return null;
    }
    public User findByEmail(String email) {
        return null;
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
    public boolean updateUsername(User user, String username) {
        return false;
    }

    public boolean delete(int userId) {
        return false;
    }
    private Result<User> validate(User user) {
        Result<User> result = new Result<>();

        if (user == null){
            result.addErrorMessage("User is required.", ResultType.INVALID);
            return result;
        }

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<User> violation : violations) {
                result.addErrorMessage(violation.getMessage(), ResultType.INVALID);
            }
        }
        return result;
    }
}
