package co.wethinkcode.healthsafe;

import io.javalin.Javalin;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaffingServiceApp {

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7033);

        app.get("/health", ctx -> ctx.result("OK"));

		app.get("/schedule/{wardId}", ctx ->{
			String wardId = ctx.pathParam("wardId");
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest wardRequest = HttpRequest.newBuilder()
					.uri(URI.create("http://localhost:7031/wards/" + wardId))
					.GET()
					.build();

			HttpResponse<String> wardResponse = client.send(
					wardRequest,
					HttpResponse.BodyHandlers.ofString()
			);
			if (wardResponse.statusCode() == 404) {
				ctx.status(404).result("Ward not found");
				return;
			}

			HttpRequest alertRequest = HttpRequest.newBuilder()
					.uri(URI.create("http://localhost:7032/alert-level"))
					.GET()
					.build();

			HttpResponse<String> alertResponse = client.send(
					alertRequest,
					HttpResponse.BodyHandlers.ofString()
			);

			// Parse the emergency level into an integer
			JSONObject alertJson = new JSONObject(alertResponse.body());
			int emergencyLevel = alertJson.getInt("level");

			// Higher emergency level = more doctors on call
			Map<String, Object> schedule = new HashMap<>();
			schedule.put("wardId", wardId);
			schedule.put("emergencyLevel", emergencyLevel);

			// Add doctors based on emergency level
			List<String> doctorsOnCall = new ArrayList<>();
			doctorsOnCall.add("Dr. Smith"); // Always at least one

			if (emergencyLevel >= 3) {
				doctorsOnCall.add("Dr. Johnson");
			}
			if (emergencyLevel >= 5) {
				doctorsOnCall.add("Dr. Williams");
			}
			if (emergencyLevel >= 7) {
				doctorsOnCall.add("Dr. Brown");
				doctorsOnCall.add("Dr. Davis");
			}

			schedule.put("doctorsOnCall", doctorsOnCall);
			schedule.put("totalDoctors", doctorsOnCall.size());

			// Return the schedule as JSON
			ctx.json(schedule);

		});

        // TODO (Provides on-call schedules for doctors based on ward and status.)
        // Add domain endpoints for staffing-service here.
    }
}

// MQ TODO: publishes to ActiveMQ topic MqConfig.TOPIC at MqConfig.BROKER_URL (see co.wethinkcode.healthsafe.mq.MqConfig)
