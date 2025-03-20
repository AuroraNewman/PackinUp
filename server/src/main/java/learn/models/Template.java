package learn.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class Template {
    @Min(value = 0, message = "Template ID must be greater than 0.")
    private int templateId;

    @Size (min = 1, max = 50, message = "Template name must be between 1 and 50 characters.")
    @NotBlank(message = "Template name is required.")
    @NotNull(message = "Template name is required.")
    private String templateName;

    @NotBlank(message = "Template description is required.")
    @NotNull(message = "Template description is required.")
    private String templateDescription;

    private TripType tripType;

    public Template() {
    }

    public Template(String templateName, String templateDescription, TripType tripType) {
        this.templateName = templateName;
        this.templateDescription = templateDescription;
        this.tripType = tripType;
    }

    public Template(int templateId, String templateName, String templateDescription, TripType tripType) {
        this.templateId = templateId;
        this.templateName = templateName;
        this.templateDescription = templateDescription;
        this.tripType = tripType;
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

    public TripType getTripType() {
        return tripType;
    }

    public void setTripType(TripType tripType) {
        this.tripType = tripType;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Template template = (Template) object;
        return getTemplateId() == template.getTemplateId() && Objects.equals(getTemplateName(), template.getTemplateName()) && Objects.equals(getTemplateDescription(), template.getTemplateDescription()) && Objects.equals(getTripType(), template.getTripType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTemplateId(), getTemplateName(), getTemplateDescription(), getTripType());
    }
}
