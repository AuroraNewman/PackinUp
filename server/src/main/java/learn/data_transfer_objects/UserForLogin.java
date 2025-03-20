package learn.data_transfer_objects;

import jakarta.validation.constraints.*;

import java.util.Objects;

public class UserForLogin {
    @Size(max = 100, message = "Email must be fewer than 100 characters.")
    @NotBlank(message = "Email is required.")
    @NotNull(message = "Email is required.")
    @Email(message = "Email must be a valid email address.")
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters long.")
    @NotBlank(message = "Password is required.")
    @NotNull(message = "Password is required.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$", message = "Password must contain at least one uppercase letter, one lowercase letter, one special character, and one number.")
    private String password;

    public UserForLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        UserForLogin that = (UserForLogin) object;
        return Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getPassword(), that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getPassword());
    }
}
