package learn.data_transfer_objects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import learn.models.TripType;

import java.util.Objects;

public class IncomingTemplate {
//    "templateName": "TestString",
//    "templateDescription": "TestString",
//    "templateTripTypeId": 1
    @Size(min = 1, max = 50, message = "Template name must be between 1 and 50 characters.")
    @NotBlank(message = "Template name is required.")
    private String templateName;

    @NotBlank(message = "Template description is required.")
    private String templateDescription;

    private boolean reusable;

    private int templateTripTypeId;

    public IncomingTemplate() {
    }

    public IncomingTemplate(String templateName, String templateDescription, boolean reusable, int templateTripTypeId) {
        this.templateName = templateName;
        this.templateDescription = templateDescription;
        this.reusable = reusable;
        this.templateTripTypeId = templateTripTypeId;
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

    public int getTemplateTripTypeId() {
        return templateTripTypeId;
    }

    public void setTemplateTripTypeId(int templateTripTypeId) {
        this.templateTripTypeId = templateTripTypeId;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        IncomingTemplate that = (IncomingTemplate) object;
        return isReusable() == that.isReusable() && getTemplateTripTypeId() == that.getTemplateTripTypeId() && Objects.equals(getTemplateName(), that.getTemplateName()) && Objects.equals(getTemplateDescription(), that.getTemplateDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTemplateName(), getTemplateDescription(), isReusable(), getTemplateTripTypeId());
    }
}
