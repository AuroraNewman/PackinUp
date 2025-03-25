package learn.data_transfer_objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import learn.models.Template;
import learn.models.TemplateItem;

import java.util.Objects;

public class OutgoingTemplateItem {
    @PositiveOrZero(message = "Template Item ID must be greater than 0.")
    private int templateItemId;
    @Positive(message = "Quantity must be greater than 0.")
    private int quantity;
    private boolean isChecked;
    @JsonBackReference
    private OutgoingTemplate outgoingTemplate;
    private OutgoingItem outgoingItem;

    public OutgoingTemplateItem(TemplateItem templateItem) {
        this.templateItemId = templateItem.getTemplateItemId();
        this.quantity = templateItem.getQuantity();
        this.isChecked = templateItem.isChecked();
        this.outgoingTemplate = new OutgoingTemplate(templateItem.getTemplate());
        this.outgoingItem = templateItem.getItem();
    }

    public OutgoingTemplateItem() {
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

    public OutgoingTemplate getOutgoingTemplate() {
        return outgoingTemplate;
    }

    public void setOutgoingTemplate(OutgoingTemplate outgoingTemplate) {
        this.outgoingTemplate = outgoingTemplate;
    }

    public OutgoingItem getOutgoingItem() {
        return outgoingItem;
    }

    public void setOutgoingItem(OutgoingItem outgoingItem) {
        this.outgoingItem = outgoingItem;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        OutgoingTemplateItem that = (OutgoingTemplateItem) object;
        return getTemplateItemId() == that.getTemplateItemId() && getQuantity() == that.getQuantity() && isChecked() == that.isChecked() && Objects.equals(getOutgoingTemplate(), that.getOutgoingTemplate()) && Objects.equals(getOutgoingItem(), that.getOutgoingItem());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTemplateItemId(), getQuantity(), isChecked(), getOutgoingTemplate(), getOutgoingItem());
    }
}
