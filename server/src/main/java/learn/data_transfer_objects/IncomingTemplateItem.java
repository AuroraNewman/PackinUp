package learn.data_transfer_objects;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Objects;

public class IncomingTemplateItem {
    @PositiveOrZero(message = "Template ID must be 0 or greater.")
    private int templateItemTemplateId;
    @PositiveOrZero(message = "Item ID must be 0 or greater.")
    private int templateItemItemId;
    @Positive(message = "Quantity must be greater than 0.")
    private int templateItemQuantity;

    private boolean templateItemIsChecked;

    public IncomingTemplateItem(int templateItemTemplateId, int templateItemItemId, int templateItemQuantity, boolean templateItemIsChecked) {
        this.templateItemTemplateId = templateItemTemplateId;
        this.templateItemItemId = templateItemItemId;
        this.templateItemQuantity = templateItemQuantity;
        this.templateItemIsChecked = templateItemIsChecked;
    }

    public IncomingTemplateItem() {
    }

    public int getTemplateItemTemplateId() {
        return templateItemTemplateId;
    }

    public void setTemplateItemTemplateId(int templateItemTemplateId) {
        this.templateItemTemplateId = templateItemTemplateId;
    }

    public int getTemplateItemItemId() {
        return templateItemItemId;
    }

    public void setTemplateItemItemId(int templateItemItemId) {
        this.templateItemItemId = templateItemItemId;
    }

    public int getTemplateItemQuantity() {
        return templateItemQuantity;
    }

    public void setTemplateItemQuantity(int templateItemQuantity) {
        this.templateItemQuantity = templateItemQuantity;
    }

    public boolean isTemplateItemIsChecked() {
        return templateItemIsChecked;
    }

    public void setTemplateItemIsChecked(boolean templateItemIsChecked) {
        this.templateItemIsChecked = templateItemIsChecked;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        IncomingTemplateItem that = (IncomingTemplateItem) object;
        return getTemplateItemTemplateId() == that.getTemplateItemTemplateId() && getTemplateItemItemId() == that.getTemplateItemItemId() && getTemplateItemQuantity() == that.getTemplateItemQuantity() && isTemplateItemIsChecked() == that.isTemplateItemIsChecked();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTemplateItemTemplateId(), getTemplateItemItemId(), getTemplateItemQuantity(), isTemplateItemIsChecked());
    }
}
