package learn.data_transfer_objects;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import learn.models.TripType;

import java.time.LocalDate;
import java.util.Objects;

public class IncomingTemplate {

@Size(min = 1, max = 50, message = "Template name must be between 1 and 50 characters.")
@NotBlank(message = "Template name is required.")
private String templateName;

    @NotBlank(message = "Template description is required.")
    private String templateDescription;

    private int templateTripTypeId;

    @FutureOrPresent(message = "Template start date must be today or in the future.")
    private LocalDate templateStartDate;

    @FutureOrPresent(message = "Template start date must be today or in the future.")
    private LocalDate templateEndDate;

    private String templateLocation;

    private IncomingWeatherQuery incomingWeatherQuery;

    public IncomingTemplate() {

    }

    public IncomingTemplate(String templateName, String templateDescription, int templateTripTypeId, LocalDate templateStartDate, LocalDate templateEndDate, String templateLocation) {
        this.templateName = templateName;
        this.templateDescription = templateDescription;
        this.templateTripTypeId = templateTripTypeId;
        this.templateStartDate = templateStartDate;
        this.templateEndDate = templateEndDate;
        this.templateLocation = templateLocation;
        this.incomingWeatherQuery = new IncomingWeatherQuery(templateLocation, templateStartDate, templateEndDate);
    }

    public IncomingTemplate(String templateName, String templateDescription, int templateTripTypeId) {
        this.templateName = templateName;
        this.templateDescription = templateDescription;
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

    public int getTemplateTripTypeId() {
        return templateTripTypeId;
    }

    public void setTemplateTripTypeId(int templateTripTypeId) {
        this.templateTripTypeId = templateTripTypeId;
    }

    public LocalDate getTemplateStartDate() {
        return templateStartDate;
    }

    public void setTemplateStartDate(LocalDate templateStartDate) {
        this.templateStartDate = templateStartDate;
    }

    public LocalDate getTemplateEndDate() {
        return templateEndDate;
    }

    public void setTemplateEndDate(LocalDate templateEndDate) {
        this.templateEndDate = templateEndDate;
    }

    public String getTemplateLocation() {
        return templateLocation;
    }

    public void setTemplateLocation(String templateLocation) {
        this.templateLocation = templateLocation;
    }

    public IncomingWeatherQuery getIncomingWeatherQuery() {
        return incomingWeatherQuery;
    }

    public void setIncomingWeatherQuery(IncomingWeatherQuery incomingWeatherQuery) {
        this.incomingWeatherQuery = incomingWeatherQuery;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        IncomingTemplate that = (IncomingTemplate) object;
        return getTemplateTripTypeId() == that.getTemplateTripTypeId() && Objects.equals(getTemplateName(), that.getTemplateName()) && Objects.equals(getTemplateDescription(), that.getTemplateDescription()) && Objects.equals(getTemplateStartDate(), that.getTemplateStartDate()) && Objects.equals(getTemplateEndDate(), that.getTemplateEndDate()) && Objects.equals(getTemplateLocation(), that.getTemplateLocation()) && Objects.equals(getIncomingWeatherQuery(), that.getIncomingWeatherQuery());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTemplateName(), getTemplateDescription(), getTemplateTripTypeId(), getTemplateStartDate(), getTemplateEndDate(), getTemplateLocation(), getIncomingWeatherQuery());
    }
}
