package learn.data_transfer_objects;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Objects;

public class UserWithIdOnly {
    @PositiveOrZero(message = "User ID must be greater than 0.")
    private int userId;

    public UserWithIdOnly(int userId) {
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
        UserWithIdOnly that = (UserWithIdOnly) o;
        return getUserId() == that.getUserId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUserId());
    }
}
