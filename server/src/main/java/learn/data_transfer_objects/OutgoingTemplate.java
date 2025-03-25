package learn.data_transfer_objects;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import learn.models.Item;
import learn.models.Template;
import learn.models.TemplateItem;
import learn.models.TripType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OutgoingTemplate {
    @PositiveOrZero(message = "Template ID must be greater than 0.")
    private int templateId;

    @Size(min = 1, max = 50, message = "Template name must be between 1 and 50 characters.")
    @NotBlank(message = "Template name is required.")
    private String templateName;

    @NotBlank(message = "Template description is required.")
    private String templateDescription;

    private TripType templateTripType;

    @JsonManagedReference
    private List<OutgoingTemplateItem> items;

    public OutgoingTemplate() {
    }

    public OutgoingTemplate(Template template) {
        this.templateId = template.getTemplateId();
        this.templateName = template.getTemplateName();
        this.templateDescription = template.getTemplateDescription();
        this.templateTripType = template.getTemplateTripType();
        this.items = template.getItems().stream()
                .map(OutgoingTemplateItem::new)
                .collect(Collectors.toList());
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateDescription() {
        return templateDescription;
    }

    public void setTemplateDescription(String templateDescription) {
        this.templateDescription = templateDescription;
    }

    public TripType getTemplateTripType() {
        return templateTripType;
    }

    public void setTemplateTripType(TripType templateTripType) {
        this.templateTripType = templateTripType;
    }

    public List<OutgoingTemplateItem> getItems() {
        return items;
    }

    public void setItems(List<OutgoingTemplateItem> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        OutgoingTemplate that = (OutgoingTemplate) object;
        return getTemplateId() == that.getTemplateId() && Objects.equals(getTemplateName(), that.getTemplateName()) && Objects.equals(getTemplateDescription(), that.getTemplateDescription()) && Objects.equals(getTemplateTripType(), that.getTemplateTripType()) && Objects.equals(getItems(), that.getItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTemplateId(), getTemplateName(), getTemplateDescription(), getTemplateTripType(), getItems());
    }
}
