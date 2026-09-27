package co.wethinkcode.healthsafe;

import io.javalin.Javalin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IngestionServiceApp {

    public static void main(String[] args) {
//        Javalin app = Javalin.create().start(7070);
//
//        app.get("/", ctx -> ctx.result("hello, world!"));


		try {
			BufferedReader reader = new BufferedReader(
					new FileReader("wards-outdated.csv")
			);

			String line;

			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}

			reader.close();

		} catch (IOException e) {
			System.out.println("Could not read the file");
		}

        // TODO: read and clean src/main/resources/wards-outdated.csv (wards, wings, specialist departments data —
        // trim whitespace, fix casing, normalize dates/booleans) and expose the
        // cleaned records here for the other services to consume.
    }
}
