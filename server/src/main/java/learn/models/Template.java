package learn.models;

import jakarta.validation.constraints.*;
import learn.data_transfer_objects.IncomingTemplateItem;
import learn.data_transfer_objects.OutgoingItem;

import java.util.ArrayList;
import java.util.List;
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

    private List<TemplateItem> items = new ArrayList<>();

    public Template() {
    }

    public Template(String templateName, String templateDescription, TripType templateTripType, User templateUser, List<TemplateItem> items) {
        this.templateName = templateName;
        this.templateDescription = templateDescription;
        this.templateTripType = templateTripType;
        this.templateUser = templateUser;
        this.items = items;
    }

    public Template(int templateId, String templateName, String templateDescription, TripType templateTripType, User templateUser, List<TemplateItem> items) {
        this.templateId = templateId;
        this.templateName = templateName;
        this.templateDescription = templateDescription;
        this.templateTripType = templateTripType;
        this.templateUser = templateUser;
        this.items = items;
    }

    public Template(int templateId, String templateName, String templateDescription, TripType templateTripType, User templateUser) {
        this.templateId = templateId;
        this.templateName = templateName;
        this.templateDescription = templateDescription;
        this.templateTripType = templateTripType;
        this.templateUser = templateUser;
        ArrayList<TemplateItem> items = new ArrayList<>();
        this.items = items;
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

    public List<TemplateItem> getItems() {
        return items;
    }

    public void setItems(List<TemplateItem> items) {
        this.items = items;
    }
    public void addItemToPack(TemplateItem item) {
        items.add(item);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Template template = (Template) object;
        return getTemplateId() == template.getTemplateId() && Objects.equals(getTemplateName(), template.getTemplateName()) && Objects.equals(getTemplateDescription(), template.getTemplateDescription()) && Objects.equals(getTemplateTripType(), template.getTemplateTripType()) && Objects.equals(getTemplateUser(), template.getTemplateUser()) && Objects.equals(getItems(), template.getItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTemplateId(), getTemplateName(), getTemplateDescription(), getTemplateTripType(), getTemplateUser(), getItems());
    }
}
