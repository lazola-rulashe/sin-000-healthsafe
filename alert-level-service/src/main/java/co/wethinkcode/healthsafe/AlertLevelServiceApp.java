package co.wethinkcode.healthsafe;

import io.javalin.Javalin;
import org.json.JSONObject;


public class AlertLevelServiceApp {

    public static void main(String[] args) {
//        Javalin app = Javalin.create().start(7032);
//
//        app.get("/health", ctx -> ctx.result("OK"));


		JSONObject green = new JSONObject();
		green.put("level", 1);
		green.put("code", "green");
		green.put("situation", "Emergency evacuation protocol");

		JSONObject silver = new JSONObject();
		silver.put("level", 2);
		silver.put("code", "silver");
		silver.put("situation", "Active shooter or an armed threat");

		JSONObject yellow = new JSONObject();
		yellow.put("level", 3);
		yellow.put("code", "yellow");
		yellow.put("situation", "Disaster preparedness or mass casualty incident");

		JSONObject orange = new JSONObject();
		orange.put("level", 4);
		orange.put("code", "orange");
		orange.put("situation", "Hazardous material spill or release");

		JSONObject pink = new JSONObject();
		pink.put("level", 5);
		pink.put("code", "pink");
		pink.put("situation", "Infant or child abduction");

		JSONObject black = new JSONObject();
		black.put("level", 6);
		black.put("code", "black");
		black.put("situation", "Bomb threat");






        // TODO (Tracks the hospital Emergency Status (0-8, 8 = full Code Blue).)
        // Add domain endpoints for alert-level-service here.
    }
}
