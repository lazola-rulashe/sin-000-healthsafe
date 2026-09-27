package co.wethinkcode.healthsafe;


import java.io.*;
import java.nio.charset.StandardCharsets;

public class IngestionServiceApp {

    public static void main(String[] args) {
//        Javalin app = Javalin.create().start(7070);
//
//        app.get("/", ctx -> ctx.result("hello, world!"));

		try (InputStream is = IngestionServiceApp.class
					.getResourceAsStream("/wards-outdated.csv");
			 BufferedReader reader = new BufferedReader(
					 new InputStreamReader(is, StandardCharsets.UTF_8))) {

			String line;

			while ((line = reader.readLine()) != null) {
//				System.out.println(line);
				String[] values = line.split(",");

				String ward_id = values[0];
				String wing = values[1];
				String department = values[2];
				String beds_available = values[3];

				System.out.println("Ward_id: " + ward_id);
				System.out.println("Wing: " + wing);
				System.out.println("Department: " + department);
				System.out.println("Beds available: " + beds_available);
				System.out.println("\n");
			}


		} catch (IOException e) {
			System.out.println("Could not read the file.");

		}catch (NullPointerException e) {
			System.out.println("File not found on classpath.");
		}

        // TODO: read and clean src/main/resources/wards-outdated.csv (wards, wings, specialist departments data —
        // trim whitespace, fix casing, normalize dates/booleans) and expose the
        // cleaned records here for the other services to consume.
    }
}
