package co.wethinkcode.healthsafe;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class IngestionServiceApp {

    public static void main(String[] args) {
//        Javalin app = Javalin.create().start(7070);
//        app.get("/", ctx -> ctx.result("hello, world!"));

		try (InputStream is = IngestionServiceApp.class
					.getResourceAsStream("/wards-outdated.csv");
			 BufferedReader reader = new BufferedReader(
					 new InputStreamReader(is, StandardCharsets.UTF_8));

			 BufferedWriter writer = new BufferedWriter(
					 new FileWriter("ingestion-service/src/main/java/co/wethinkcode/healthsafe/wards-cleaned.csv"))

			 )
			{
			writer.write("Ward_id,Wing,Department,Beds_available");
			writer.newLine();

			reader.readLine();

			String line;

			while ((line = reader.readLine()) != null) {

				String[] values = line.split(",");
				if (values.length < 4) {
					continue;
				}

				String ward_id = values[0].trim().toUpperCase();
				String wing = toTitleCase(values[1].trim());
				String department = toTitleCase(values[2].trim());
				String beds_available = values[3].trim();

				System.out.println("Ward_id: " + ward_id);
				System.out.println("Wing: " + wing);
				System.out.println("Department: " + department);
				System.out.println("Beds available: " + beds_available);
				System.out.println("\n");

				writer.write(ward_id + "," + wing + "," + department + "," + beds_available);
				writer.newLine();
				writer.flush();

			}

		} catch (IOException e) {
			System.out.println("Could not read or write the file.");

		} catch (NullPointerException e) {
			System.out.println("File not found on classpath");
		}

        // TODO: read and clean src/main/resources/wards-outdated.csv (wards, wings, specialist departments data —
        // trim whitespace, fix casing, normalize dates/booleans) and expose the
        // cleaned records here for the other services to consume.
    }
	public static String toTitleCase(String text) {
		if (text == null || text.isEmpty()) {
			return text;
		}

		StringBuilder converted = new StringBuilder();

		boolean convertNext = true;
		for (char ch : text.toCharArray()) {
			if (Character.isSpaceChar(ch)) {
				convertNext = true;
			} else if (convertNext) {
				ch = Character.toTitleCase(ch);
				convertNext = false;
			} else {
				ch = Character.toLowerCase(ch);
			}
			converted.append(ch);
		}

		return converted.toString();
	}
}
