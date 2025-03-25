package learn.data_transfer_objects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import learn.models.Item;

import java.util.Objects;

public class OutgoingItem {
    @PositiveOrZero(message = "Item ID must be greater than 0.")
    private int itemId;

    @Size(min = 1, max = 50, message = "Item name must be between 1 and 50 characters.")
    @NotBlank(message = "Item name is required.")
    private String itemName;

    @PositiveOrZero(message = "Category ID must be greater than 0.")
    private int categoryId;

    @Size(min = 1, max = 50, message = "Category name must be between 1 and 50 characters.")
    @NotBlank(message = "Category name is required.")
    private String categoryName;

    private String color;

    public OutgoingItem() {
    }

    public OutgoingItem(Item item) {
        this.itemId = item.getItemId();
        this.itemName = item.getItemName();
        this.categoryId = item.getCategory().getCategoryId();
        this.categoryName = item.getCategory().getCategoryName();
        this.color = item.getCategory().getColor();
    }

    public OutgoingItem(int itemId, String itemName, int categoryId, String categoryName, String color) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.color = color;
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
        OutgoingItem that = (OutgoingItem) object;
        return getItemId() == that.getItemId() && getCategoryId() == that.getCategoryId() && Objects.equals(getItemName(), that.getItemName()) && Objects.equals(getCategoryName(), that.getCategoryName()) && Objects.equals(getColor(), that.getColor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItemId(), getItemName(), getCategoryId(), getCategoryName(), getColor());
    }
}
