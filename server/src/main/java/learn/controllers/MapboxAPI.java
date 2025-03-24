package learn.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import learn.models.Coordinates;
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

    /*
    @GetMapping("/{address}")
    public CompletableFuture<String> getCoordinates(@PathVariable String address) {
        String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
        String uriBase = String.format("https://api.mapbox.com/geocoding/v5/mapbox.places/%s.json?limit=1&access_token=%s", encodedAddress, MAPBOX_API_TOKEN);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uriBase))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();
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

    @GetMapping("/{address}")
    public Coordinates getCoordinates(@PathVariable String address){
        String uriBase = generateUrl(address);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uriBase))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode root = mapper.readTree(response.body());
                        JsonNode coordinates = root.path("features").get(0).path("center");
                        double longitude = coordinates.get(0).asDouble();
                        double latitude = coordinates.get(1).asDouble();
                        return new Coordinates(longitude, latitude);
                    } catch (Exception e) {
                        return null;
                    }
                });
        return null;
    }
    private String generateUrl(String address){
        String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
        return String.format("https://api.mapbox.com/geocoding/v5/mapbox.places/%s.json?limit=1&access_token=%s", encodedAddress, MAPBOX_API_TOKEN);
    }
}
