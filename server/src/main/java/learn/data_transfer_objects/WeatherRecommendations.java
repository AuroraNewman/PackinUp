package learn.data_transfer_objects;

import learn.models.Item;

import java.util.Objects;
import java.util.Set;

public class WeatherRecommendations {
    private String forecast;
    private Set<OutgoingItem> itemsToPack;

    public WeatherRecommendations() {
    }

    public WeatherRecommendations(String forecast, Set<OutgoingItem> itemsToPack) {
        this.forecast = forecast;
        this.itemsToPack = itemsToPack;
    }

    public void addItemToPack(Item item) {
        itemsToPack.add(new OutgoingItem(item));
    }

    public String getForecast() {
        return forecast;
    }

    public void setForecast(String forecast) {
        this.forecast = forecast;
    }

    public Set<OutgoingItem> getItemsToPack() {
        return itemsToPack;
    }

    public void setItemsToPack(Set<OutgoingItem> itemsToPack) {
        this.itemsToPack = itemsToPack;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        WeatherRecommendations that = (WeatherRecommendations) object;
        return Objects.equals(getForecast(), that.getForecast()) && Objects.equals(getItemsToPack(), that.getItemsToPack());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getForecast(), getItemsToPack());
    }

    @Override
    public String toString() {
        return "WeatherRecommendations{" +
                "forecast='" + forecast + '\'' +
                ", itemsToPack=" + itemsToPack +
                '}';
    }
}
