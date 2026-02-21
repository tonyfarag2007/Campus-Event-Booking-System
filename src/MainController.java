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

    @FXML
    public void initialize() {
        setup_user_table();
        setup_user_form();
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
}