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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import java.time.LocalDate;
import java.util.Date;

public class MainController {

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
    private ObservableList<User> user_list;
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


    @FXML
    public void initialize() {
        setup_user_table();
        setup_user_form();

        setup_event_table();
        setup_event_form();
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

    // Event management
    private void setup_event_table() {
        event_id_column.setCellValueFactory(new PropertyValueFactory<Event, String>("eventId"));
        event_title_column.setCellValueFactory(new PropertyValueFactory<Event, String>("eventTitle"));
        event_date_column.setCellValueFactory(new PropertyValueFactory<Event, String>("eventDate"));
        event_location_column.setCellValueFactory(new PropertyValueFactory<Event, String>("eventLocation"));
        event_capacity_column.setCellValueFactory(new PropertyValueFactory<Event, Integer>("eventCapacity"));

        // Creating dummy data "on the fly" exactly as mentioned in the TableView transcript
        event_list = FXCollections.observableArrayList();

        // Manual date creation (basic Java, brute-forcing the LocalDate into java.util.Date)
        Date dummy_date_1 = new Date(126, 1, 12);
        Date dummy_date_2 = new Date(126, 2, 1);

        event_list.add(new Event("E101", "Intro to Git", dummy_date_1, "Library 101", 40, "", ""));
        event_list.add(new Event("E205", "AI Safety Talk", dummy_date_2, "MACN 113", 120, "", ""));

        event_table_view.setItems(event_list);
        event_table_view.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void setup_event_form() {
        event_type_choicebox.getItems().addAll(Event.EventType.values());
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

                int year = local_date.getYear() - 1900;
                int month = local_date.getMonthValue() - 1;
                int day = local_date.getDayOfMonth();
                Date parsed_date = new Date(year, month, day);

                Event new_event = new Event(new_id, new_title, parsed_date, new_location, new_capacity, "", "");
                event_list.add(new_event);


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
}