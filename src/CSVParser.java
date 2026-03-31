import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

public class CSVParser {
    boolean save_csv(ArrayList<? extends CSVSerializable> serializable_object, String file_path) {
        try (var bw = new java.io.BufferedWriter(new java.io.FileWriter(file_path))) {
            if (serializable_object.isEmpty()) {
                throw new RuntimeException("There is nothing to write to the CSV file, we will continue later.");
            }

            bw.write(serializable_object.getFirst().header());
            bw.newLine();

            for (var obj : serializable_object) {
                bw.write(obj.serialize());
                bw.newLine();
            }
        } catch (Exception e) { // make it somebody else's problem
            System.out.println("Error saving CSV file: " + e.getMessage());
            return false;
        }

        return true;
    }

    public <T extends CSVSerializable> ArrayList<T> load_csv(String file_path, Class<T> klass) {
        ArrayList<T> ret = new ArrayList<>();
        try {
            var br = new BufferedReader(new FileReader(file_path));
            String line = br.readLine();
            if (line == null)
                throw new RuntimeException("The CSV file (" + file_path + ") is empty");

            // creating a new object here shouldn't be the end of the world, why have a garbage collector if you're not going to use it!
            final var header = klass.getConstructor().newInstance().header();
            if (!line.equals(header)) {
                throw new RuntimeException("The CSV file's header does not match the expected format for " + klass.getName() + ". Please manually fix the file.");
            }
            final int expectedColumns = header.split(",", -1).length;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", -1);
                if (values.length == 0 || values.length != expectedColumns) // we will ignore malformed entries, rather than throw an exception. TODO: ask questions then fix later.
                    continue;

                ArrayList<String> csv_values = new ArrayList<>(Arrays.asList(values));

                // don't worry this will always work, and if it doesn't we have bigger problems than it not working.
                var obj = (T)klass.getConstructor().newInstance().deserialize(csv_values);
                if (obj != null) {
                    ret.add(obj);
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading CSV file: " + e.getMessage());
        }

        return ret;
    }
}