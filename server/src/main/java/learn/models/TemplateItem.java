package learn.models;

import jakarta.validation.constraints.*;

import java.util.Objects;

public class TemplateItem {

    @PositiveOrZero(message = "Template Item ID must be greater than 0.")
    private int templateItemId;

    @Positive(message = "Quantity must be greater than 0.")
    private int quantity;

    private boolean isChecked;

    @NotNull(message = "Template is required.")
    private Template template;
    @NotNull(message = "Item is required.")
    private Item item;

    public TemplateItem() {
    }

    public TemplateItem(int quantity, boolean isChecked, Template template, Item item) {
        this.quantity = quantity;
        this.isChecked = isChecked;
        this.template = template;
        this.item = item;
    }

    public TemplateItem(int templateItemId, int quantity, boolean isChecked, Template template, Item item) {
        this.templateItemId = templateItemId;
        this.quantity = quantity;
        this.isChecked = isChecked;
        this.template = template;
        this.item = item;
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

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        TemplateItem that = (TemplateItem) object;
        return getTemplateItemId() == that.getTemplateItemId() && getQuantity() == that.getQuantity() && isChecked() == that.isChecked() && Objects.equals(getTemplate(), that.getTemplate()) && Objects.equals(getItem(), that.getItem());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTemplateItemId(), getQuantity(), isChecked(), getTemplate(), getItem());
    }
}
