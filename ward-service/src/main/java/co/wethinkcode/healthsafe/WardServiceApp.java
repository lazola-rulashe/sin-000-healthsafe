package co.wethinkcode.healthsafe;

import io.javalin.Javalin;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;

public class WardServiceApp {

    private static List<Map<String, String>> allWards = new ArrayList<>();

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7031);

        app.get("/health", ctx -> ctx.result("OK"));

        app.get("/records", ctx -> {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:7030/records"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );
            ctx.result(response.body());
        });

        app.get("/wards", ctx -> {
            Wards();
            ctx.json(allWards);
        });

        app.get("/wards/{id}", ctx -> {
            Wards();
            String wardId = ctx.pathParam("id");
            Optional<Map<String, String>> ward = allWards.stream()
                    .filter(w -> w.get("Ward_id").equalsIgnoreCase(wardId))
                    .findFirst();
            if (ward.isPresent()) {
                ctx.json(ward.get());
            } else {
                ctx.status(404).result("Ward not found");
            }
        });

        app.get("/departments", ctx -> {
            Wards();
            Set<String> departments = allWards.stream()
                    .map(w -> w.get("Department"))
                    .filter(d -> d != null && !d.equals("TBD"))
                    .collect(Collectors.toSet());
            ctx.json(new ArrayList<>(departments));
        });
    }

    private static void Wards() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:7030/wards"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            String[] lines = response.body().split("\n");
            allWards = new ArrayList<>();

            for (int i = 1; i < lines.length; i++) {
                String line = lines[i].trim();
                if (line.isEmpty()) continue;

                String[] values = line.split(",");
                if (values.length >= 4) {
                    Map<String, String> ward = new HashMap<>();
                    ward.put("Ward_id", values[0].trim());
                    ward.put("Wing", values[1].trim());
                    ward.put("Department", values[2].trim());
                    ward.put("Beds_available", values[3].trim());
                    allWards.add(ward);
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to fetch wards: " + e.getMessage());
        }
    }
}

// MQ TODO: subscribes to ActiveMQ topic MqConfig.TOPIC at MqConfig.BROKER_URL (see co.wethinkcode.healthsafe.mq.MqConfig)
// MQ TODO: publishes to ActiveMQ queue MqConfig.QUEUE when it detects an equipment failure on one of its wards.
