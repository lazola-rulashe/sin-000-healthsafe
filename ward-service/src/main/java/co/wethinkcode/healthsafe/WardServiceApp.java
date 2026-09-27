package co.wethinkcode.healthsafe;

import io.javalin.Javalin;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WardServiceApp {

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

        // TODO (Provides lists of wards and departments.)
        // Add domain endpoints for ward-service here.
    }
}

// MQ TODO: subscribes to ActiveMQ topic MqConfig.TOPIC at MqConfig.BROKER_URL (see co.wethinkcode.healthsafe.mq.MqConfig)
// MQ TODO: publishes to ActiveMQ queue MqConfig.QUEUE when it detects an equipment failure on one of its wards.
