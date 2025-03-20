package learn.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class TripType {
    @Min(value = 0, message = "Trip Type ID must be greater than or equal to 0.")
    private int tripTypeId;

    @Size(min = 1, max = 50, message = "Trip Type Name must be between 1 and 50 characters.")
    @NotBlank(message = "Trip Type Name is required.")
    @NotNull(message = "Trip Type Name is required.")
    private String tripTypeName;

    @NotBlank(message = "Trip Type Description is required.")
    @NotNull(message = "Trip Type Description is required.")
    private String tripTypeDescription;

    public TripType() {
    }

    public TripType(String tripTypeName, String tripTypeDescription) {
        this.tripTypeName = tripTypeName;
        this.tripTypeDescription = tripTypeDescription;
    }

    public TripType(int tripTypeId, String tripTypeName, String tripTypeDescription) {
        this.tripTypeId = tripTypeId;
        this.tripTypeName = tripTypeName;
        this.tripTypeDescription = tripTypeDescription;
    }

    public int getTripTypeId() {
        return tripTypeId;
    }

    public void setTripTypeId(int tripTypeId) {
        this.tripTypeId = tripTypeId;
    }

    public String getTripTypeName() {
        return tripTypeName;
    }

    public void setTripTypeName(String tripTypeName) {
        this.tripTypeName = tripTypeName;
    }

    public String getTripTypeDescription() {
        return tripTypeDescription;
    }

    public void setTripTypeDescription(String tripTypeDescription) {
        this.tripTypeDescription = tripTypeDescription;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        TripType tripType = (TripType) object;
        return getTripTypeId() == tripType.getTripTypeId() && Objects.equals(getTripTypeName(), tripType.getTripTypeName()) && Objects.equals(getTripTypeDescription(), tripType.getTripTypeDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTripTypeId(), getTripTypeName(), getTripTypeDescription());
    }
}
