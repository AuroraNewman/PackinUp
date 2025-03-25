package learn.data_transfer_objects;

import jakarta.validation.constraints.FutureOrPresent;

import java.time.LocalDate;
import java.util.Objects;

public class IncomingWeatherQuery {
    private String query;
    @FutureOrPresent
    private LocalDate startDate;
    @FutureOrPresent
    private LocalDate endDate;

    public IncomingWeatherQuery() {
    }

    public IncomingWeatherQuery(String query, LocalDate startDate, LocalDate endDate) {
        this.query = query;
        this.startDate = startDate;
        this.endDate = endDate;
        if (!checkDates()) {
            throw new IllegalArgumentException("Invalid date range");
        }
    }

    private boolean checkDates() {
        return startDate != null && endDate != null && (!endDate.isBefore(startDate));

    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        IncomingWeatherQuery that = (IncomingWeatherQuery) object;
        return Objects.equals(getQuery(), that.getQuery()) && Objects.equals(getStartDate(), that.getStartDate()) && Objects.equals(getEndDate(), that.getEndDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQuery(), getStartDate(), getEndDate());
    }
}
