package learn.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import learn.data.ItemRepository;
import learn.data_transfer_objects.IncomingWeatherQuery;
import learn.data_transfer_objects.OutgoingItem;
import learn.data_transfer_objects.WeatherRecommendations;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;


import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
public class WeatherAPI {
    private final HttpClient client = HttpClient.newHttpClient();

    private final String WEATHER_API_TOKEN =System.getenv("WEATHER");
    private final int SUMMER_CLOTHES_RECORD_NUMBER = 1;
    private final int SPRING_FALL_CLOTHES_RECORD_NUMBER = 2;
    private final int WINTER_CLOTHES_RECORD_NUMBER = 3;
    private final int RAIN_GEAR_RECORD_NUMBER = 4;
    private final int SNOW_GEAR_RECORD_NUMBER = 5;
    private final int SUNGLASSES_RECORD_NUMBER = 6;
    private final int SUNSCREEN_RECORD_NUMBER = 7;
    private final Double WORLD_RECORD_MAX_TEMP = 134.1;
    private final Double WORLD_RECORD_MIN_TEMP = -128.6;

    private final ItemRepository repository;

    public WeatherAPI(ItemRepository repository) {
        this.repository = repository;
    }

    public WeatherRecommendations suggestItemsForWeather(IncomingWeatherQuery incomingWeatherQuery) throws IOException, InterruptedException {
        String uriBase = generateUrl(incomingWeatherQuery.getQuery());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uriBase))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();
        HashMap<LocalDate, List<String>> forecastMap = new HashMap<>();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(body);
        JsonNode forecastDays = root.path("forecast").path("forecastday");

        for (JsonNode dayNode : forecastDays) {
            OutgoingItem item = new OutgoingItem();
            LocalDate date = LocalDate.parse(dayNode.path("date").asText());
            JsonNode dayInfo = dayNode.path("day");

            String maxTemp = String.valueOf(dayInfo.path("maxtemp_f").asDouble());
            String minTemp = String.valueOf(dayInfo.path("mintemp_f").asDouble());
            String precip = String.valueOf(dayInfo.path("totalprecip_in").asDouble());
            String willRain = String.valueOf(dayInfo.path("daily_will_it_rain").asInt());
            String willSnow = String.valueOf(dayInfo.path("daily_will_it_snow").asInt());
            String condition = dayInfo.path("condition").path("text").asText();
            String uv = String.valueOf(dayInfo.path("uv").asDouble());

            List<String> dayData = Arrays.asList(maxTemp, minTemp, precip, willRain, willSnow, condition, uv);
            forecastMap.put(date, dayData);
        }

        return suggestItems(forecastMap, incomingWeatherQuery);
    }
    private String generateUrl(String searchQuery){
        String encodedSearchQuery = URLEncoder.encode(searchQuery.trim(), StandardCharsets.UTF_8);
        String url = String.format("http://api.weatherapi.com/v1/forecast.json?key=%s&q=%s&days=14", WEATHER_API_TOKEN, searchQuery);
        return url;
    }
    private WeatherRecommendations suggestItems(HashMap<LocalDate, List<String>> forecastMap, IncomingWeatherQuery incomingWeatherQuery) {
        WeatherRecommendations recommendations = new WeatherRecommendations();
        Set<OutgoingItem> items = new HashSet<>();
        recommendations.setItemsToPack(items);
        Double tripMaxTemp = WORLD_RECORD_MIN_TEMP;
        Double tripMinTemp = WORLD_RECORD_MAX_TEMP;
        int rainyDays = 0;
        int snowyDays = 0;
        int sunnyDays = 0;
        for (Map.Entry<LocalDate, List<String>> entry : forecastMap.entrySet()) {
            if (entry.getKey().isBefore(incomingWeatherQuery.getStartDate()) || entry.getKey().isAfter(incomingWeatherQuery.getEndDate())) {
                continue;
            }
            LocalDate date = entry.getKey();
            List<String> dayData = entry.getValue();
            String maxTemp = dayData.get(0);
            String minTemp = dayData.get(1);
            String precip = dayData.get(2);
            String willRain = dayData.get(3);
            String willSnow = dayData.get(4);
            String condition = dayData.get(5);
            String uv = dayData.get(6);

            if (Double.parseDouble(maxTemp) > 80) {
                recommendations.addItemToPack(repository.findById(SUMMER_CLOTHES_RECORD_NUMBER));
            } else if (Double.parseDouble(maxTemp) > 60) {
                recommendations.addItemToPack(repository.findById(SPRING_FALL_CLOTHES_RECORD_NUMBER));
            } else {
                recommendations.addItemToPack(repository.findById(WINTER_CLOTHES_RECORD_NUMBER));
            }

            if (Double.parseDouble(precip) > 0.1) {
                recommendations.addItemToPack(repository.findById(RAIN_GEAR_RECORD_NUMBER));
            }

            if (Integer.parseInt(willRain) == 1) {
                recommendations.addItemToPack(repository.findById(RAIN_GEAR_RECORD_NUMBER));
                rainyDays++;
            }

            if (Integer.parseInt(willSnow) == 1) {
                recommendations.addItemToPack(repository.findById(SNOW_GEAR_RECORD_NUMBER));
                snowyDays++;
            }

            if (condition.equals("Sunny")) {
                recommendations.addItemToPack(repository.findById(SUNGLASSES_RECORD_NUMBER));
                sunnyDays++;
            }

            if (Double.parseDouble(uv) > 3) {
                recommendations.addItemToPack(repository.findById(SUNSCREEN_RECORD_NUMBER));
            }
            if (tripMaxTemp == null || Double.parseDouble(maxTemp) > tripMaxTemp) {
                tripMaxTemp = Double.parseDouble(maxTemp);
            }
            if (tripMinTemp == null || Double.parseDouble(minTemp) < tripMinTemp) {
                tripMinTemp = Double.parseDouble(minTemp);
            }
        }
        recommendations.setForecast(constructWeatherNote(tripMaxTemp, tripMinTemp, rainyDays, snowyDays, sunnyDays));
        return recommendations;
    }

    private String constructWeatherNote(Double tripMaxTemp, Double tripMinTemp, int rainyDays, int snowDays, int sunnyDays){
        String recommendations = "Weather Recommendations:\n";
        recommendations += "Expected High: " + tripMaxTemp + "\n";
        recommendations += "Expected Low: " + tripMinTemp + "\n";
        recommendations += "Rainy Days: " + rainyDays + "\n";
        recommendations += "Snowy Days: " + snowDays + "\n";
        recommendations += "Sunny Days: " + sunnyDays + "\n";
        return recommendations;
    }
}
