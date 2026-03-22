import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Event validation logic.
 */
public class EventValidationTest {

    @Test
    @DisplayName("Verify duplicate Event IDs are caught correctly")
    void test_duplicate_event_id() {
        // 1. Setup (The "Arrange" phase)
        ArrayList<Event> mock_list = new ArrayList<>();
        Date dummy_date = new Date(126, 1, 1);

        // Add an event to our mock list
        Event existing_event = new Event("E999", "Test Event", dummy_date, "Room A", 50, "", "");
        mock_list.add(existing_event);

        // 2. Execution & Assertion (The "Act & Assert" phase)
        // Test 1: Trying an ID that already exists should return true (duplicate found)
        boolean is_duplicate = Event.check_dup_event_id("E999", mock_list);
        assertTrue(is_duplicate, "The system should flag E999 as a duplicate.");

        // Test 2: Trying a brand new ID should return false (safe to use)
        boolean is_safe = Event.check_dup_event_id("E100", mock_list);
        assertFalse(is_safe, "The system should allow E100 as it is unique.");
    }
}