package learn.models;

import jakarta.validation.constraints.*;

import java.util.Objects;

public class TemplateItem {

    @PositiveOrZero(message = "Template Item ID must be greater than 0.")
    private int templateItemId;

    @Positive(message = "Quantity must be greater than 0.")
    private int quantity;

    private boolean isChecked;

    private int templateId;
    private int itemId;

    public TemplateItem() {
    }

    public TemplateItem(int quantity, boolean isChecked, int templateId, int itemId) {
        this.quantity = quantity;
        this.isChecked = isChecked;
        this.templateId = templateId;
        this.itemId = itemId;
    }

    public TemplateItem(int templateItemId, int quantity, boolean isChecked, int templateId, int itemId) {
        this.templateItemId = templateItemId;
        this.quantity = quantity;
        this.isChecked = isChecked;
        this.templateId = templateId;
        this.itemId = itemId;
    }

    public int getTemplateItemId() {
        return templateItemId;
    }

    public void setTemplateItemId(int templateItemId) {
        this.templateItemId = templateItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        TemplateItem that = (TemplateItem) object;
        return getTemplateItemId() == that.getTemplateItemId() && getQuantity() == that.getQuantity() && isChecked() == that.isChecked() && getTemplateId() == that.getTemplateId() && getItemId() == that.getItemId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTemplateItemId(), getQuantity(), isChecked(), getTemplateId(), getItemId());
    }
}
