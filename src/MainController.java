import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.LoadException;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.beans.property.ReadOnlyStringWrapper;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class MainController {
    private static final String STATUS_CONFIRMED = "Confirmed";
    private static final String STATUS_WAITLISTED = "Waitlisted";
    private static final String STATUS_CANCELLED = "Cancelled";
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    private static CSVParser parser;

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
    public static ObservableList<User> user_list;
    // Event management nodes
    @FXML private TableView<Event> event_table_view;
    @FXML private TableColumn<Event, String> event_id_column;
    @FXML private TableColumn<Event, String> event_title_column;
    @FXML private TableColumn<Event, String> event_date_column;
    @FXML private TableColumn<Event, String> event_location_column;
    @FXML private TableColumn<Event, Integer> event_capacity_column;

    @FXML private TextField event_id_input;
    @FXML private TextField event_title_input;
    @FXML private DatePicker event_date_picker;
    @FXML private TextField event_location_input;
    @FXML private TextField event_capacity_input;
    @FXML private ChoiceBox<Event.EventType> event_type_choicebox;

    private ObservableList<Event> event_list;

    // Search and Filter Nodes
    @FXML private TextField search_event_input;
    @FXML private ChoiceBox<String> filter_event_choicebox;

    // Booking and Waitlist fields
    private ObservableList<Booking> booking_data = FXCollections.observableArrayList();
    private ObservableList<Booking> waitlist_data = FXCollections.observableArrayList();

    @FXML private TableView<Booking> booking_table_view;
    @FXML private TableColumn<Booking, String> booking_id_column;
    @FXML private TableColumn<Booking, String> booking_user_column;
    @FXML private TableColumn<Booking, String> booking_event_column;
    @FXML private TableColumn<Booking, String> booking_time_column;
    @FXML private TableColumn<Booking, String> booking_status_column;
    @FXML private TextField booking_id_input;
    @FXML private TextField booking_user_input;
    @FXML private TextField booking_event_input;
    @FXML private Label booking_status_message;

    @FXML private TableView<Booking> waitlist_table_view;
    @FXML private TableColumn<Booking, String> waitlist_booking_id_column;
    @FXML private TableColumn<Booking, String> waitlist_user_column;
    @FXML private TableColumn<Booking, String> waitlist_event_column;
    @FXML private TableColumn<Booking, String> waitlist_time_column;
    @FXML private TableColumn<Booking, String> waitlist_status_column;
    @FXML private Label waitlist_status_message;


    @FXML
    public void initialize() {
        parser = new CSVParser();
        setup_user_table();
        setup_user_form();

        setup_event_table();
        setup_event_form();
        setup_booking_table();
        setup_waitlist_table();
        // TODO: remove this.
        //seed_demo_booking_data();

        Runtime.getRuntime().addShutdownHook(new Thread(this::save));

    }

    public void save() {
        save_user_table();
        save_event_table();
        save_booking_table();
    }

    private void setup_user_table() {
        user_id_column.setCellValueFactory(new PropertyValueFactory<>("userId"));
        user_name_column.setCellValueFactory(new PropertyValueFactory<>("name"));
        user_email_column.setCellValueFactory(new PropertyValueFactory<>("email"));
        user_type_column.setCellValueFactory(new PropertyValueFactory<>("type"));

        ArrayList<User> new_users = parser.load_csv("users.csv", User.class);
        user_list = FXCollections.observableArrayList(
                new_users
        );

        user_table_view.setItems(user_list);
        user_table_view.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void save_user_table() {
        parser.save_csv(new ArrayList<>(user_list), "users.csv");
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
        try {
            User.check_dup_user_id(new_id, user_list);
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
        catch(IllegalArgumentException e){
            System.out.println("ID: " + new_id + " already in use");
        }
    }

    // Event management
    private void setup_event_table() {
        event_id_column.setCellValueFactory(new PropertyValueFactory<Event, String>("eventId"));
        event_title_column.setCellValueFactory(new PropertyValueFactory<Event, String>("eventTitle"));
        event_date_column.setCellValueFactory(new PropertyValueFactory<Event, String>("eventDate"));
        event_location_column.setCellValueFactory(new PropertyValueFactory<Event, String>("eventLocation"));
        event_capacity_column.setCellValueFactory(new PropertyValueFactory<Event, Integer>("eventCapacity"));

        event_type_column.setCellValueFactory(cellData -> {
            Event e = cellData.getValue();
            if (e.getWorkshopTopic() != null && !e.getWorkshopTopic().isEmpty()) return new SimpleStringProperty("Workshop");
            if (e.getSeminarSpeakerName() != null && !e.getSeminarSpeakerName().isEmpty()) return new SimpleStringProperty("Seminar");
            if (e.getConcertAgeRestriction() > 0) return new SimpleStringProperty("Concert");
            return new SimpleStringProperty("Standard");
        });

        event_list = FXCollections.observableArrayList(parser.load_csv("events.csv", Event.class));

        event_table_view.setItems(event_list);
        event_table_view.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void save_event_table() {
        parser.save_csv(new ArrayList<>(event_list), "events.csv");
    }

    private void setup_event_form() {
        event_type_choicebox.getItems().addAll(Event.EventType.values());

        // Populating the filter dropdown. Added "All" as default.
        // Sourced from: JavaFX ChoiceBox tutorial (getItems().addAll)
        filter_event_choicebox.getItems().addAll("All", "Workshop", "Seminar", "Concert");
        filter_event_choicebox.setValue("All");
    }

    @FXML
    public void handle_add_event(ActionEvent event) {
        try {
            String new_id = event_id_input.getText();
            String new_title = event_title_input.getText();
            String new_location = event_location_input.getText();
            int new_capacity = Integer.parseInt(event_capacity_input.getText());
            Event.EventType new_type = event_type_choicebox.getValue();

            LocalDate local_date = event_date_picker.getValue();

            if (new_id != null && !new_id.isEmpty() && local_date != null && new_type != null && new_capacity > 0) {

                // Check for duplicate Event IDs before creation
                for (Event existing_event : master_event_list) {
                    if (existing_event.getEventId().equals(new_id)) {
                        System.out.println("Validation failed: Event ID " + new_id + " is already in use.");
                        return; // Exit the method immediately if a duplicate is found
                    }
                }

                int year = local_date.getYear() - 1900;
                int month = local_date.getMonthValue() - 1;
                int day = local_date.getDayOfMonth();
                Date parsed_date = new Date(year, month, day);

                Event new_event = null;

                switch (new_type) {
                    case Workshop:
                        new_event = new Event(new_id, new_title, parsed_date, new_location, new_capacity, "", "", "TBD");
                        break;
                    case Seminar:
                        new_event = new Event(new_id, new_title, parsed_date, new_location, new_capacity, "", "", "TBD Speaker");
                        break;
                    case Concert:
                        new_event = new Event(new_id, new_title, parsed_date, new_location, new_capacity, "", "", 18);
                        break;
                    default:
                        new_event = new Event(new_id, new_title, parsed_date, new_location, new_capacity, "", "");
                        break;
                }

                event_list.add(new_event);
                master_event_list.add(new_event);

                event_id_input.clear();
                event_title_input.clear();
                event_location_input.clear();
                event_capacity_input.clear();
                event_date_picker.setValue(null);
                event_type_choicebox.setValue(null);
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Capacity must be a number.");
        }
    }

    @FXML
    public void handle_delete_user(ActionEvent event) {
        User selected_user = user_table_view.getSelectionModel().getSelectedItem();
        if (selected_user != null) {
            user_list.remove(selected_user);
        }
    }

    @FXML
    public void handle_cancel_event(ActionEvent event) {
        Event selected_event = event_table_view.getSelectionModel().getSelectedItem();
        if (selected_event != null) {
            event_list.remove(selected_event);
            master_event_list.remove(selected_event);
        }
    }

    @FXML
    public void handle_event_search(ActionEvent event) {
        String search_text = search_event_input.getText();
        if (search_text != null) {
            search_text = search_text.toLowerCase();
        }
        String filter_type = filter_event_choicebox.getValue();

        event_list.clear();

        for (Event e : master_event_list) {
            boolean matches_search = true;
            boolean matches_type = true;

            if (search_text != null && !search_text.isEmpty()) {
                if (!e.getEventTitle().toLowerCase().contains(search_text)) {
                    matches_search = false;
                }
            }

            if (filter_type != null && !filter_type.equals("All")) {
                if (filter_type.equals("Workshop") && (e.getWorkshopTopic() == null || e.getWorkshopTopic().isEmpty())) {
                    matches_type = false;
                } else if (filter_type.equals("Seminar") && (e.getSeminarSpeakerName() == null || e.getSeminarSpeakerName().isEmpty())) {
                    matches_type = false;
                } else if (filter_type.equals("Concert") && e.getConcertAgeRestriction() == 0) {
                    matches_type = false;
                }
            }

            if (matches_search && matches_type) {
                event_list.add(e);
            }
        }
    }

    // Booking management
    private void setup_booking_table() {
        booking_id_column.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().get_book_id()));
        booking_user_column.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().get_user_id()));
        booking_event_column.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().get_event_id()));
        booking_time_column.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().get_event_time()));
        booking_status_column.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().get_book_status()));

        booking_data = FXCollections.observableArrayList(parser.load_csv("bookings.csv", Booking.class));
        booking_table_view.setItems(booking_data);
        booking_table_view.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void save_booking_table() {
        parser.save_csv(new ArrayList<>(booking_data), "bookings.csv");
    }

    @FXML
    public void handle_add_booking(ActionEvent event) {
        String new_booking_id = normalize(booking_id_input.getText());
        String user_id = normalize(booking_user_input.getText());
        String event_id = normalize(booking_event_input.getText());

        if (new_booking_id.isEmpty() || user_id.isEmpty() || event_id.isEmpty()) {
            set_booking_message("All fields are required.", true);
            return;
        }

        for (Booking booking : booking_data) {
            if (booking.get_book_id().equals(new_booking_id)) {
                set_booking_message("Duplicate booking ID.", true);
                return;
            }
            if (booking.get_user_id().equals(user_id)
                    && booking.get_event_id().equals(event_id)
                    && !STATUS_CANCELLED.equals(booking.get_book_status())) {
                set_booking_message("User already has an active booking for this event.", true);
                return;
            }
        }
        User dummyUser = null;

        for (User user:user_list){
            if (user.getUserId().equals(user_id)){
                dummyUser = user;
            }
            if (dummyUser == null){
                set_booking_message("User with this id does not exist", true);
            }
                if (user.getBookAmount() == user.getBookLimit()) { // fails if reached maximum amount of bookings
                    set_booking_message("User has maximum amount of bookings", true);
                    return;
                }



        }

        // Find associated event and its capacity before adding new attendee to use waitlist properly
        for (Event e: event_list) {
            if (e.getEventId().equals(event_id)) {
                assert dummyUser != null;
                dummyUser.changeBookAmount(1);
                // Enough space to fit new bookings given size of this events current bookings list
                if (e.getEventCapacity() > e.getBookingIds().size()) {
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
                } else {
                    // Not enough space, so put on waitlist
                    Booking new_booking = new Booking(
                            new_booking_id,
                            user_id,
                            event_id,
                            LocalDateTime.now().format(TIME_FORMAT),
                            STATUS_WAITLISTED
                    );
                    booking_data.add(new_booking);
                    sync_waitlist_view();
                    set_booking_message("Capacity is full, so waitlisted booking was created.", false);
                }
            }
        }

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
            String user_id = selected_booking.get_book_id();
            for (User user : user_list) {
                if (user.getUserId().equals(user_id)) {
                    user.changeBookAmount(-1);
                }
                set_booking_message("Booking cancelled.", false);
            }
        }
    }

    // Waitlist management
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
        Booking selected_booking = waitlist_table_view.getSelectionModel().getSelectedItem();

        if (selected_booking == null) {
            set_waitlist_message("Select a waitlisted booking.", true);
            return;
        }

        if (!STATUS_WAITLISTED.equals(selected_booking.get_book_status())) {
            set_waitlist_message("Only waitlisted bookings can be removed.", true);
            return;
        }

        selected_booking.set_book_status(STATUS_CANCELLED);
        booking_table_view.refresh();
        sync_waitlist_view();
        waitlist_table_view.refresh();
        set_waitlist_message("Removed from waitlist.", false);
    }

    @FXML private TableColumn<Event, String> event_type_column;
    // We need a master list in memory to search against so we don't read the CSV every time
    private ArrayList<Event> master_event_list = new ArrayList<>();

    private void seed_demo_booking_data() {
        if (!booking_data.isEmpty()) {
            sync_waitlist_view();
            return;
        }
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
