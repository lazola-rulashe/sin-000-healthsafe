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

		JSONObject yellow = new JSONObject();
		yellow.put("level", 2);
		yellow.put("code", "silver");
		yellow.put("situation", "Active shooter or an armed threat");




        // TODO (Tracks the hospital Emergency Status (0-8, 8 = full Code Blue).)
        // Add domain endpoints for alert-level-service here.
    }
}
