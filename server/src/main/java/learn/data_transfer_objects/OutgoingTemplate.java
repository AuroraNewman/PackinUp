package learn.data_transfer_objects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import learn.models.Item;
import learn.models.Template;
import learn.models.TemplateItem;
import learn.models.TripType;

import java.util.ArrayList;
import java.util.List;

public class OutgoingTemplate {
    @PositiveOrZero(message = "Template ID must be greater than 0.")
    private int templateId;

    @Size(min = 1, max = 50, message = "Template name must be between 1 and 50 characters.")
    @NotBlank(message = "Template name is required.")
    private String templateName;

    @NotBlank(message = "Template description is required.")
    private String templateDescription;

    private TripType templateTripType;

    private List<TemplateItem> items;

    public OutgoingTemplate() {
    }

    public OutgoingTemplate(Template template) {
        this.templateId = template.getTemplateId();
        this.templateName = template.getTemplateName();
        this.templateDescription = template.getTemplateDescription();
        this.templateTripType = template.getTemplateTripType();
        this.items = template.getItems();
    }

}
