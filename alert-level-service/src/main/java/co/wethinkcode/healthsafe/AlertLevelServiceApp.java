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

		app.post("/alert-level", ctx ->{
			int newLevel = ctx.bodyAsClass(Integer.class);
			if(newLevel >=0 && newLevel <=8){
				currentLevel = newLevel;
				ctx.status(200);
			}else{
				ctx.result("This level is unknown");
				ctx.status(400);
			}
		});


        // TODO (Tracks the hospital Emergency Status (0-8, 8 = full Code Blue).)
        // Add domain endpoints for alert-level-service here.
    }
}
