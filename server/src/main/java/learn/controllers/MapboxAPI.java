package learn.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/packinup/location")
@RestController
public class MapboxAPI {
    private final HttpClient client = HttpClient.newHttpClient();

    private final String MAPBOX_API_TOKEN =System.getenv("MAPBOX");

    @GetMapping("/{address}")
    public CompletableFuture<String> getCoordinates(@PathVariable String address) {
        String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
        System.out.println(encodedAddress);
        System.out.println(MAPBOX_API_TOKEN);
        String uriBase = String.format("https://api.mapbox.com/geocoding/v5/mapbox.places/%s.json?limit=1&access_token=%s", encodedAddress, MAPBOX_API_TOKEN);
//        String uriBase = "https://api.mapbox.com/geocoding/v5/mapbox.places/tokyo.json?limit=1&access_token=pk.eyJ1IjoiYXVyb3Jhcm4iLCJhIjoiY204bjFtenM1MGkzNzJqcGphM2JzbG1qdyJ9.0l4qbvUL-J8BUv8vP5cO6Q";

//        String uriBase = "https://api.mapbox.com/search/geocode/v6/forward?q="+encodedAddress+"&limit=1&access_token=%"+MAPBOX_API_TOKEN;
// following uribase will suggest ones nearby and not necessarily have to exact match
//                String uriBase = String.format("https://api.mapbox.com/search/searchbox/v1/suggest?q=%s&access_token=%s", encodedAddress, MAPBOX_API_TOKEN);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uriBase))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();
        /*
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body);

         */
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode root = mapper.readTree(response.body());
                        JsonNode coordinates = root.path("features").get(0).path("center");
                        double longitude = coordinates.get(0).asDouble();
                        double latitude = coordinates.get(1).asDouble();
                        return String.format("Longitude: %f, Latitude: %f", longitude, latitude);
                    } catch (Exception e) {
                        return "Error parsing coordinates: " + e.getMessage();
                    }
                });
    }
    /*
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(response -> {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode root = mapper.readTree(response.body());
                    JsonNode coordinates = root.path("features").get(0).path("center");
                    double longitude = coordinates.get(0).asDouble();
                    double latitude = coordinates.get(1).asDouble();
                    return String.format("Longitude: %f, Latitude: %f", longitude, latitude);
                } catch (Exception e) {
                    return "Error parsing coordinates: " + e.getMessage();
                }
            });
}
     */

}
