package co.wethinkcode.healthsafe;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class IngestionServiceApp {

    public static void main(String[] args) {
//        Javalin app = Javalin.create().start(7030);
//        app.get("/", ctx -> ctx.result("hello, world!"));

		Set<String> previousRows = new HashSet<>();
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
				if (wing == null || wing.trim().isEmpty() || wing.equals("N/A") || wing.equals("unknown")) {
					wing = "TBD";
				}
				String department = toTitleCase(values[2].trim());
				if (department == null || department.trim().isEmpty() || department.equals("N/A") || department.equals("unknown")) {
					department = "TBD";
				}

				String beds_available = values[3].trim();
				if (beds_available.equals("N/A") || beds_available.equals("unknown")) {
					beds_available = "TBD";
				}
				if (beds_available.equals("full")){
					beds_available = "2023";
				}

				String cleanedRow = ward_id + "," + wing + "," + department.replace("Icu", "ICU") + "," + beds_available.replace("-", "").replace("five", "5");

				if (previousRows.contains(cleanedRow)) {
					continue;
				}

				previousRows.add(cleanedRow);

				writer.write(cleanedRow);
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
