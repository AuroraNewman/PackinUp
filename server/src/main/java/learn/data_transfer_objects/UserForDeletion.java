package learn.data_transfer_objects;

import jakarta.validation.constraints.Min;

import java.util.Objects;

public class UserForDeletion {
    @Min(value = 0, message = "User ID must be greater than 0.")
    private int userId;

    public UserForDeletion(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserForDeletion that = (UserForDeletion) o;
        return getUserId() == that.getUserId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUserId());
    }
}
