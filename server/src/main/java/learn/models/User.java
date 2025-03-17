package learn.models;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.*;
import java.util.Objects;


public class User {
    @Min(value = 0, message = "User ID must be greater than 0.")
    private int userId;
    @Size(max = 50, message = "Customer username must be fewer than 50 characters.")
    @NotBlank(message = "Customer username is required.")
    @NotNull(message = "Customer username is required.")
    private String username;
    @Size(max = 100, message = "Customer email must be fewer than 100 characters.")
    @NotBlank(message = "Customer email is required.")
    @NotNull(message = "Customer email is required.")
    @Email(message = "Customer email must be a valid email address.")
    private String email;
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    @NotBlank(message = "Customer password is required.")
    @NotNull(message = "Customer password is required.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$", message = "Password must contain at least one uppercase letter, one lowercase letter, one special character, and one number.")
    private String password;

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(int userId, String username, String email, String password) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getUserId() == user.getUserId() && Objects.equals(getUsername(), user.getUsername()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getPassword(), user.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getUsername(), getEmail(), getPassword());
    }
}
