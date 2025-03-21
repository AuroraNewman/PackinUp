package learn.models;

import jakarta.validation.constraints.*;

import java.util.Objects;

public class Template {
    @PositiveOrZero(message = "Template ID must be greater than 0.")
    private int templateId;

    @Size (min = 1, max = 50, message = "Template name must be between 1 and 50 characters.")
    @NotBlank(message = "Template name is required.")
    private String templateName;

    @NotBlank(message = "Template description is required.")
    private String templateDescription;

    private TripType templateTripType;

    @NotNull(message = "User is required.")
    private User templateUser;

    public Template() {
    }

    public Template(String templateName, String templateDescription, TripType templateTripType, User templateUser) {
        this.templateName = templateName;
        this.templateDescription = templateDescription;
        this.templateTripType = templateTripType;
        this.templateUser = templateUser;
    }

    public Template(int templateId, String templateName, String templateDescription, TripType templateTripType, User templateUser) {
        this.templateId = templateId;
        this.templateName = templateName;
        this.templateDescription = templateDescription;
        this.templateTripType = templateTripType;
        this.templateUser = templateUser;
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

    public User getTemplateUser() {
        return templateUser;
    }

    public void setTemplateUser(User templateUser) {
        this.templateUser = templateUser;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Template template = (Template) object;
        return getTemplateId() == template.getTemplateId() && Objects.equals(getTemplateName(), template.getTemplateName()) && Objects.equals(getTemplateDescription(), template.getTemplateDescription()) && Objects.equals(getTemplateTripType(), template.getTemplateTripType()) && Objects.equals(getTemplateUser(), template.getTemplateUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTemplateId(), getTemplateName(), getTemplateDescription(), getTemplateTripType(), getTemplateUser());
    }
}
