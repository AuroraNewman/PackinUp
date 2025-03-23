package learn.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class Item {
    @PositiveOrZero(message = "Item ID must be greater than 0.")
    private int itemId;

    @Size(min = 1, max = 50, message = "Item name must be between 1 and 50 characters.")
    @NotBlank(message = "Item name is required.")
    private String itemName;

    @NotNull(message = "User is required.")
    private User user;

    @NotNull(message = "Category is required.")
    private Category category;

    public Item() {
    }

    public Item(String itemName, User user, Category category) {
        this.itemName = itemName;
        this.user = user;
        this.category = category;
    }

    public Item(int itemId, String itemName, User user, Category category) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.user = user;
        this.category = category;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Item item = (Item) object;
        return getItemId() == item.getItemId() && Objects.equals(getItemName(), item.getItemName()) && Objects.equals(getUser(), item.getUser()) && Objects.equals(getCategory(), item.getCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItemId(), getItemName(), getUser(), getCategory());
    }
}
