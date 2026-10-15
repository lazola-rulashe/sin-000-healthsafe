package co.wethinkcode.healthsafe;

import io.javalin.Javalin;
import org.json.JSONObject;


public class AlertLevelServiceApp {

	private static int currentLevel = 0;

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7032);
        app.get("/health", ctx -> ctx.result("OK"));


		app.get("/alert-level", ctx -> {
				JSONObject response = new JSONObject();
				response.put("level", currentLevel);
				ctx.result(response.toString());
				});


        // TODO (Tracks the hospital Emergency Status (0-8, 8 = full Code Blue).)
        // Add domain endpoints for alert-level-service here.
    }
}
