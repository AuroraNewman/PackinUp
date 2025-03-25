package learn.data_transfer_objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("isChecked")
    private boolean isChecked;
    @JsonBackReference
    private OutgoingTemplate outgoingTemplate;
    @JsonProperty("outgoingItem")
    private OutgoingItem item;

    public OutgoingTemplateItem(TemplateItem templateItem) {
        this.templateItemId = templateItem.getTemplateItemId();
        this.quantity = templateItem.getQuantity();
        this.isChecked = templateItem.isChecked();
        this.outgoingTemplate = new OutgoingTemplate(templateItem.getTemplate());
        this.item = templateItem.getItem();
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

    @JsonProperty("checked")
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

    @JsonProperty("item")
    public OutgoingItem getOutgoingItem() {
        return item;
    }

    public void setOutgoingItem(OutgoingItem item) {
        this.item = item;
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
