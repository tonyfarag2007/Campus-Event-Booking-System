import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class CSVParserTest {

    @Test
    @DisplayName("Affirm proper loading of CSVs for their appropriate classes.")
    void load_csv() {
        CSVParser csvParser = new CSVParser();
        ArrayList<User> users = csvParser.load_csv("assets/users.csv", User.class);
        // Ensure all users are loaded and the parsing works normally
        assertEquals(25, users.size());
        assertEquals("U001,Alice Smith,alice.smith@uoguelph.ca,Student", users.getFirst().serialize());
    }
}