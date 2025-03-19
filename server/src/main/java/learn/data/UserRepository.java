package learn.data;

import learn.models.User;

public interface UserRepository {
    User findById(int userId);
    User findByEmail(String email);
    User create(User user);
    boolean updateUsername(User user);
    boolean delete(int userId);
}
