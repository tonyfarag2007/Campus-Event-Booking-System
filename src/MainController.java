import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.beans.property.ReadOnlyStringWrapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
// This is constants, that will show up on the "Booking Management" tab and the "Waitlist Management" tab.
public class MainController {
    private static final String STATUS_CONFIRMED = "Confirmed";
    private static final String STATUS_WAITLISTED = "Waitlisted";
    private static final String STATUS_CANCELLED = "Cancelled";
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    @FXML
    private TabPane main_tab_pane;

    // Table elements
    @FXML
    private TableView<User> user_table_view;
    @FXML
    private TableColumn<User, String> user_id_column;
    @FXML
    private TableColumn<User, String> user_name_column;
    @FXML
    private TableColumn<User, String> user_email_column;
    @FXML
    private TableColumn<User, User.UserType> user_type_column;

    // Separate list used to display waitlisted bookings in the waitlist tab.
    private ObservableList<Booking> waitlist_data = FXCollections.observableArrayList();
    @FXML private TableView<Booking> waitlist_table_view;
    @FXML private TableColumn<Booking, String> waitlist_booking_id_column;
    @FXML private TableColumn<Booking, String> waitlist_user_column;
    @FXML private TableColumn<Booking, String> waitlist_event_column;
    @FXML private TableColumn<Booking, String> waitlist_time_column;
    @FXML private TableColumn<Booking, String> waitlist_status_column;

    @FXML private javafx.scene.control.Label waitlist_status_message;

    // Booking Table Elements
    @FXML private TableView<Booking> booking_table_view;
    @FXML private TableColumn<Booking, String> booking_id_column;
    @FXML private TableColumn<Booking, String> booking_user_column;
    @FXML private TableColumn<Booking, String> booking_event_column;
    @FXML private TableColumn<Booking, String> booking_time_column;
    @FXML private TableColumn<Booking, String> booking_status_column;

    // Booking Form Inputs
    @FXML private TextField booking_id_input;
    @FXML private TextField booking_user_input;
    @FXML private TextField booking_event_input;

    @FXML private javafx.scene.control.Label booking_status_message;

    // Single source of truth for booking records
    private ObservableList<Booking> booking_data = FXCollections.observableArrayList();

    // Form inputs (snake_case)
    @FXML
    private TextField user_id_input;
    @FXML
    private TextField user_name_input;
    @FXML
    private TextField user_email_input;
    @FXML
    private ChoiceBox<User.UserType> user_type_choicebox;

    // Made this a class-level variable so the add user button can access and modify it
    private ObservableList<User> user_list;

    @FXML
    public void initialize() {
        setup_user_table();
        setup_user_form();
        setup_booking_table();
        setup_waitlist_table();
        seed_demo_booking_data();
    }

    private void setup_user_table() {
        user_id_column.setCellValueFactory(new PropertyValueFactory<>("userId"));
        user_name_column.setCellValueFactory(new PropertyValueFactory<>("name"));
        user_email_column.setCellValueFactory(new PropertyValueFactory<>("email"));
        user_type_column.setCellValueFactory(new PropertyValueFactory<>("type"));

        user_list = FXCollections.observableArrayList(
                new User("Alice Smith", "U001", "alice@uoguelph.ca", User.UserType.Student),
                new User("Bob Jones", "U002", "bob@uoguelph.ca", User.UserType.Staff)
        );
        user_table_view.setItems(user_list);
        // Note: CONSTRAINED_RESIZE_POLICY was deprecated in modern JavaFX.
        // Replaced with the updated FLEX_COLUMNS policy to clear IDE build warnings.
        user_table_view.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    // Populates the ChoiceBox dropdown menu
    // Sourced from: JavaFX ChoiceBox tutorial (getItems().addAll)
    private void setup_user_form() {
        user_type_choicebox.getItems().addAll(User.UserType.values());
    }

    // Triggered when "Add User" button is clicked
    // Sourced from: JavaFX TextField & Event Handling tutorials
    @FXML
    public void handle_add_user(ActionEvent event) {
        String new_id = user_id_input.getText();
        String new_name = user_name_input.getText();
        String new_email = user_email_input.getText();
        User.UserType new_type = user_type_choicebox.getValue();

        // Basic validation: ensure no fields are empty before adding
        if (new_id != null && !new_id.isEmpty() &&
                new_name != null && !new_name.isEmpty() &&
                new_type != null) {

            User new_user = new User(new_name, new_id, new_email, new_type);
            user_list.add(new_user); // This automatically updates the TableView

            // Clear the form for the next entry
            user_id_input.clear();
            user_name_input.clear();
            user_email_input.clear();
            user_type_choicebox.setValue(null);
        } else {
            System.out.println("Validation failed: Please fill out all required fields.");
        }
    }

    private void setup_booking_table() {
        booking_id_column.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().get_book_id()));
        booking_user_column.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().get_user_id()));
        booking_event_column.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().get_event_id()));
        booking_time_column.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().get_event_time()));
        booking_status_column.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().get_book_status()));

        booking_table_view.setItems(booking_data);

        // Maintain consistent resizing behavior across all tables
        booking_table_view.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    @FXML
    public void handle_add_booking(ActionEvent event) {

        String new_booking_id = normalize(booking_id_input.getText());
        String user_id = normalize(booking_user_input.getText());
        String event_id = normalize(booking_event_input.getText());

        // Basic validation to ensure required fields are not empty
        if (new_booking_id.isEmpty() || user_id.isEmpty() || event_id.isEmpty()) {
            set_booking_message("All fields are required.", true);
            return;
        }

        // Prevent duplicate booking IDs
        for (Booking booking : booking_data) {
            if (booking.get_book_id().equals(new_booking_id)) {
                set_booking_message("Duplicate booking ID.", true);
                return;
            }
            // Keep rule simple and beginner-friendly: one active booking per user/event pair.
            if (booking.get_user_id().equals(user_id)
                    && booking.get_event_id().equals(event_id)
                    && !STATUS_CANCELLED.equals(booking.get_book_status())) {
                set_booking_message("User already has an active booking for this event.", true);
                return;
            }
        }

        Booking new_booking = new Booking(
                new_booking_id,
                user_id,
                event_id,
                LocalDateTime.now().format(TIME_FORMAT),
                STATUS_CONFIRMED
        );

        booking_data.add(new_booking);
        sync_waitlist_view();

        set_booking_message("Booking created.", false);

        // Clear form inputs after successful creation
        booking_id_input.clear();
        booking_user_input.clear();
        booking_event_input.clear();
    }
    @FXML
    public void handle_cancel_booking(ActionEvent event) {

        Booking selected_booking = booking_table_view.getSelectionModel().getSelectedItem();

        if (selected_booking == null) {
            set_booking_message("Select a booking.", true);
            return;
        }

        if (STATUS_CANCELLED.equals(selected_booking.get_book_status())) {
            set_booking_message("Booking is already cancelled.", true);
            return;
        }

        boolean was_confirmed = STATUS_CONFIRMED.equals(selected_booking.get_book_status());
        selected_booking.set_book_status(STATUS_CANCELLED);

        Booking promoted = null;
        if (was_confirmed) {
            promoted = promote_first_waitlisted_booking(selected_booking.get_event_id());
        }

        booking_table_view.refresh();
        sync_waitlist_view();
        waitlist_table_view.refresh();

        if (promoted != null) {
            set_booking_message(
                    "Booking cancelled. Promoted " + promoted.get_user_id() + " (" + promoted.get_book_id() + ").",
                    false
            );
            set_waitlist_message("Auto-promotion occurred for event " + promoted.get_event_id() + ".", false);
        } else {
            set_booking_message("Booking cancelled.", false);
        }
    }

    private void setup_waitlist_table() {
        waitlist_booking_id_column.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().get_book_id()));
        waitlist_user_column.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().get_user_id()));
        waitlist_event_column.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().get_event_id()));
        waitlist_time_column.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().get_event_time()));
        waitlist_status_column.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().get_book_status()));

        waitlist_table_view.setItems(waitlist_data);
        waitlist_table_view.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    @FXML
    public void handle_refresh_waitlist(ActionEvent event) {
        sync_waitlist_view();
        waitlist_table_view.refresh();

        set_waitlist_message("Waitlist refreshed.", false);
    }
    @FXML
    public void handle_remove_waitlist(ActionEvent event) {

        // Retrieve currently selected booking from waitlist table
        Booking selected_booking = waitlist_table_view.getSelectionModel().getSelectedItem();

        // If no selection exists, prevent operation and notify user
        if (selected_booking == null) {
            set_waitlist_message("Select a waitlisted booking.", true);
            return;
        }

        // Enforce rule: only Waitlisted bookings can be removed
        if (!STATUS_WAITLISTED.equals(selected_booking.get_book_status())) {
            set_waitlist_message("Only waitlisted bookings can be removed.", true);
            return;
        }
        selected_booking.set_book_status(STATUS_CANCELLED);

        // Rebuild list after status change.
        booking_table_view.refresh();
        sync_waitlist_view();
        waitlist_table_view.refresh();

        set_waitlist_message("Removed from waitlist.", false);
    }

    private void seed_demo_booking_data() {
        if (!booking_data.isEmpty()) {
            return;
        }
        // Sample records to make both Booking and Waitlist tabs easy to test.
        booking_data.add(new Booking("B9000", "U001", "E101", "2026-02-01T09:00", STATUS_CONFIRMED));
        booking_data.add(new Booking("B9001", "U001", "E102", "2026-02-01T09:15", STATUS_WAITLISTED));
        booking_data.add(new Booking("B9002", "U002", "E102", "2026-02-01T09:30", STATUS_WAITLISTED));
        sync_waitlist_view();
    }

    private Booking promote_first_waitlisted_booking(String event_id) {
        Booking first_waitlisted = null;

        for (Booking booking : booking_data) {
            if (!event_id.equals(booking.get_event_id())) {
                continue;
            }
            if (!STATUS_WAITLISTED.equals(booking.get_book_status())) {
                continue;
            }

            if (first_waitlisted == null || is_earlier(booking.get_event_time(), first_waitlisted.get_event_time())) {
                first_waitlisted = booking;
            }
        }

        if (first_waitlisted != null) {
            first_waitlisted.set_book_status(STATUS_CONFIRMED);
        }
        return first_waitlisted;
    }

    private boolean is_earlier(String candidate_time, String reference_time) {
        try {
            LocalDateTime candidate = LocalDateTime.parse(candidate_time);
            LocalDateTime reference = LocalDateTime.parse(reference_time);
            return candidate.isBefore(reference);
        } catch (Exception ignored) {
            // Fallback if date formats vary in demo data.
            return candidate_time.compareTo(reference_time) < 0;
        }
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim();
    }

    private void sync_waitlist_view() {
        waitlist_data.clear();
        for (Booking booking : booking_data) {
            if (STATUS_WAITLISTED.equals(booking.get_book_status())) {
                waitlist_data.add(booking);
            }
        }
    }

    private void set_booking_message(String text, boolean is_error) {
        booking_status_message.setText(text);
        booking_status_message.setStyle(is_error ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
    }

    private void set_waitlist_message(String text, boolean is_error) {
        waitlist_status_message.setText(text);
        waitlist_status_message.setStyle(is_error ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
    }
}
