package learn.data_transfer_objects;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class UserToFindByEmail {
    @Size(max = 100, message = "Email must be fewer than 100 characters.")
    @NotBlank(message = "Email is required.")
    @NotNull(message = "Email is required.")
    @Email(message = "Email must be a valid email address.")
    private String email;

    public UserToFindByEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserToFindByEmail that = (UserToFindByEmail) o;
        return Objects.equals(getEmail(), that.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEmail());
    }
}
