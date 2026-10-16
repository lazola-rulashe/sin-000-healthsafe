package co.wethinkcode.healthsafe;
import io.javalin.Javalin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;


public class IngestionServiceApp {

    private static String cleanedCsvData = "";

    public static void main(String[] args) {
        cleanedCsvData = cleanWardsData();

        Javalin app = Javalin.create().start(7030);
		app.get("/health", ctx -> ctx.result("OK"));


        app.get("/records", ctx -> {
            ctx.result(cleanedCsvData);
        });
    }

    private static String cleanWardsData() {
        Set<String> previousRows = new HashSet<>();
        StringBuilder result = new StringBuilder();
        result.append("Ward_id,Wing,Department,Beds_available\n");

        try (InputStream is = IngestionServiceApp.class
                    .getResourceAsStream("/wards-outdated.csv");
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(is, StandardCharsets.UTF_8))) {

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
                if (beds_available.equals("0")){
                    beds_available = "full";
                }

                String cleanedRow = ward_id + "," + wing.replace("  ", " ") + "," + department.replace("Icu", "ICU") + "," + beds_available.replace("-", "").replace("five", "5");

                if (previousRows.contains(cleanedRow)) {
                    continue;
                }

                previousRows.add(cleanedRow);
                result.append(cleanedRow).append("\n");
            }

        } catch (IOException e) {
            System.out.println("Could not read the file.");
        } catch (NullPointerException e) {
            System.out.println("File not found on classpath");
        }

        return result.toString();
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
