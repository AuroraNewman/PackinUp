package learn.data_transfer_objects;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Objects;

public class IncomingTemplateItem {
    private String templateItemItemName;
    @PositiveOrZero(message = "Template ID must be 0 or greater.")
    private int templateItemTemplateId;
    @Positive(message = "Quantity must be greater than 0.")
    private int templateItemQuantity;

    private boolean templateItemIsChecked;

    private String categoryName;

    public IncomingTemplateItem() {
    }

    public IncomingTemplateItem(String templateItemItemName, int templateItemTemplateId, int templateItemQuantity, boolean templateItemIsChecked, String categoryName) {
        this.templateItemItemName = templateItemItemName;
        this.templateItemTemplateId = templateItemTemplateId;
        this.templateItemQuantity = templateItemQuantity;
        this.templateItemIsChecked = templateItemIsChecked;
        this.categoryName = categoryName;
    }

    public String getTemplateItemItemName() {
        return templateItemItemName;
    }

    public void setTemplateItemItemName(String templateItemItemName) {
        this.templateItemItemName = templateItemItemName;
    }

    public int getTemplateItemTemplateId() {
        return templateItemTemplateId;
    }

    public void setTemplateItemTemplateId(int templateItemTemplateId) {
        this.templateItemTemplateId = templateItemTemplateId;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        IncomingTemplateItem that = (IncomingTemplateItem) object;
        return getTemplateItemTemplateId() == that.getTemplateItemTemplateId() && getTemplateItemQuantity() == that.getTemplateItemQuantity() && isTemplateItemIsChecked() == that.isTemplateItemIsChecked() && Objects.equals(getTemplateItemItemName(), that.getTemplateItemItemName()) && Objects.equals(getCategoryName(), that.getCategoryName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTemplateItemItemName(), getTemplateItemTemplateId(), getTemplateItemQuantity(), isTemplateItemIsChecked(), getCategoryName());
    }
}

