package learn.models;

import jakarta.validation.constraints.*;

import java.util.Objects;

public class PackingList {
    @PositiveOrZero(message = "Template ID must be greater than 0.")
    private int templateId;

    @Size (min = 1, max = 50, message = "Template name must be between 1 and 50 characters.")
    @NotBlank(message = "Template name is required.")
    private String templateName;

    @NotBlank(message = "Template description is required.")
    private String templateDescription;

    boolean reusable;

    private TripType templateTripType;

    @NotNull(message = "User is required.")
    private User templateUser;

    public PackingList() {
    }

    public PackingList(String templateName, String templateDescription, boolean reusable, TripType templateTripType, User templateUser) {
        this.templateName = templateName;
        this.templateDescription = templateDescription;
        this.reusable = reusable;
        this.templateTripType = templateTripType;
        this.templateUser = templateUser;
    }

    public PackingList(int templateId, String templateName, String templateDescription, boolean reusable, TripType templateTripType, User templateUser) {
        this.templateId = templateId;
        this.templateName = templateName;
        this.templateDescription = templateDescription;
        this.reusable = reusable;
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

    public boolean isReusable() {
        return reusable;
    }

    public void setReusable(boolean reusable) {
        this.reusable = reusable;
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
        PackingList packingList = (PackingList) object;
        return getTemplateId() == packingList.getTemplateId() && isReusable() == packingList.isReusable() && Objects.equals(getTemplateName(), packingList.getTemplateName()) && Objects.equals(getTemplateDescription(), packingList.getTemplateDescription()) && Objects.equals(getTemplateTripType(), packingList.getTemplateTripType()) && Objects.equals(getTemplateUser(), packingList.getTemplateUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTemplateId(), getTemplateName(), getTemplateDescription(), isReusable(), getTemplateTripType(), getTemplateUser());
    }
}
