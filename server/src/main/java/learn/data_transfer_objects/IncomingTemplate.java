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

    private TripType templateTripType;

    public IncomingTemplate() {
    }

    public IncomingTemplate(String templateName, String templateDescription, TripType templateTripType) {
        this.templateName = templateName;
        this.templateDescription = templateDescription;
        this.templateTripType = templateTripType;
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

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        IncomingTemplate that = (IncomingTemplate) object;
        return Objects.equals(getTemplateName(), that.getTemplateName()) && Objects.equals(getTemplateDescription(), that.getTemplateDescription()) && Objects.equals(getTemplateTripType(), that.getTemplateTripType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTemplateName(), getTemplateDescription(), getTemplateTripType());
    }
}
