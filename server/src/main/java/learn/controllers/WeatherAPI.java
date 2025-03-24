package learn.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import learn.data_transfer_objects.WeatherRecommendations;
import learn.models.Item;
import learn.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/packinup/weather")
@RestController
public class WeatherAPI {
    private final HttpClient client = HttpClient.newHttpClient();
    private final ItemService itemService;

    public WeatherAPI(ItemService itemService) {
        this.itemService = itemService;
    }

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

    @GetMapping("/{searchQuery}")
    public WeatherRecommendations suggestItemsForWeather(@PathVariable String searchQuery) throws IOException, InterruptedException {
        String uriBase = generateUrl(searchQuery);
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

        return suggestItems(forecastMap);
    }
    private String generateUrl(String searchQuery){
        String url = String.format("http://api.weatherapi.com/v1/forecast.json?key=%s&q=%s&days=14", WEATHER_API_TOKEN, searchQuery);
        return url;
    }
    private WeatherRecommendations suggestItems(HashMap<LocalDate, List<String>> forecastMap){
        WeatherRecommendations recommendations = new WeatherRecommendations();
        Set<Item> items = new HashSet<>();
        recommendations.setItemsToPack(items);
        Double tripMaxTemp = WORLD_RECORD_MIN_TEMP;
        Double tripMinTemp = WORLD_RECORD_MAX_TEMP;
        int rainyDays = 0;
        int snowyDays = 0;
        int sunnyDays = 0;
        for (Map.Entry<LocalDate, List<String>> entry : forecastMap.entrySet()) {
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
                recommendations.addItemToPack(itemService.findById(SUMMER_CLOTHES_RECORD_NUMBER));
            } else if (Double.parseDouble(maxTemp) > 60) {
                recommendations.addItemToPack(itemService.findById(SPRING_FALL_CLOTHES_RECORD_NUMBER));
            } else {
                recommendations.addItemToPack(itemService.findById(WINTER_CLOTHES_RECORD_NUMBER));
            }

            if (Double.parseDouble(precip) > 0.1) {
                recommendations.addItemToPack(itemService.findById(RAIN_GEAR_RECORD_NUMBER));
            }

            if (Integer.parseInt(willRain) == 1) {
                recommendations.addItemToPack(itemService.findById(RAIN_GEAR_RECORD_NUMBER));
                rainyDays++;
            }

            if (Integer.parseInt(willSnow) == 1) {
                recommendations.addItemToPack(itemService.findById(SNOW_GEAR_RECORD_NUMBER));
                snowyDays++;
            }

            if (condition.equals("Sunny")) {
                recommendations.addItemToPack(itemService.findById(SUNGLASSES_RECORD_NUMBER));
                sunnyDays++;
            }

            if (Double.parseDouble(uv) > 3) {
                recommendations.addItemToPack(itemService.findById(SUNSCREEN_RECORD_NUMBER));
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
