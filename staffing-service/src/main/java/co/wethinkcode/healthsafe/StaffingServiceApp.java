package co.wethinkcode.healthsafe;

import io.javalin.Javalin;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class StaffingServiceApp {

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7033);

        app.get("/health", ctx -> ctx.result("OK"));

		app.get("/records", ctx -> {

			HttpClient client = HttpClient.newHttpClient();

			HttpRequest wardsRequest = HttpRequest.newBuilder()
					.uri(URI.create("http://localhost:7032/records"))
					.GET()
					.build();

			HttpResponse<String> wardsResponse = client.send(
					wardsRequest,
					HttpResponse.BodyHandlers.ofString()
			);

			HttpRequest emergencyRequest = HttpRequest.newBuilder()
					.uri(URI.create("http://localhost:7032/records"))
					.GET()
					.build();

			HttpResponse<String> emergencyResponse = client.send(
					emergencyRequest,
					HttpResponse.BodyHandlers.ofString()
			);

			ctx.result("Wards: \n" + wardsResponse.body() + "\nEmergency status: \n" + emergencyResponse.body());
		});

        // TODO (Provides on-call schedules for doctors based on ward and status.)
        // Add domain endpoints for staffing-service here.
    }
}

// MQ TODO: publishes to ActiveMQ topic MqConfig.TOPIC at MqConfig.BROKER_URL (see co.wethinkcode.healthsafe.mq.MqConfig)
