package co.wethinkcode.healthsafe;

import io.javalin.Javalin;
import org.json.JSONObject;


public class AlertLevelServiceApp {

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7032);
        app.get("/health", ctx -> ctx.result("OK"));

		app.get("/records", ctx ->{

			JSONObject white = new JSONObject();
			white.put("level", 0);
			white.put("code", "white");
			white.put("situation", "all clear");

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

			JSONObject red = new JSONObject();
			red.put("level", 7);
			red.put("code", "red");
			red.put("situation", "ire or smoke detected within the building");

			JSONObject blue = new JSONObject();
			blue.put("level", 8);
			blue.put("code", "blue");
			blue.put("situation", "Medical emergency requiring immediate resuscitation");

			JSONObject emergencyCodes = new JSONObject();

			emergencyCodes.put("Level 0", white);
			emergencyCodes.put("Level 1", green);
			emergencyCodes.put("Level 2", silver);
			emergencyCodes.put("Level 3", yellow);
			emergencyCodes.put("Level 4", orange);
			emergencyCodes.put("Level 5", pink);
			emergencyCodes.put("Level 6", black);
			emergencyCodes.put("Level 7", red);
			emergencyCodes.put("Level 8", blue);

			ctx.contentType("json");
			ctx.result(emergencyCodes.toString());
		});







        // TODO (Tracks the hospital Emergency Status (0-8, 8 = full Code Blue).)
        // Add domain endpoints for alert-level-service here.
    }
}
