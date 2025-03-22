package learn.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class Category {

    @PositiveOrZero(message = "Category ID must be greater than 0.")
    private int categoryId;

    @Size(min = 1, max = 50, message = "Category name must be between 1 and 50 characters.")
    @NotBlank(message = "Category name is required.")
    private String categoryName;

    private String color;

    public Category() {
    }

    public Category(String categoryName, String color) {
        this.categoryName = categoryName;
        this.color = color;
    }

    public Category(int categoryId, String categoryName, String color) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.color = color;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Category category = (Category) object;
        return getCategoryId() == category.getCategoryId() && Objects.equals(getCategoryName(), category.getCategoryName()) && Objects.equals(getColor(), category.getColor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCategoryId(), getCategoryName(), getColor());
    }
}
