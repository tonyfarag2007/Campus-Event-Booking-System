# GUI Development Plan (Phase 1)
###### Proposed by Qasim - Group 18

> [!IMPORTANT]
> All UI logic and FXML IDs will strictly follow the project's `snake_case` convention (e.g., `user_table`, `handle_add_event`) to ensure integration with core classes.

# 1. Component Architecture (Grounded in Lab/Transcripts)
## 1.1 Layout Structure
- **Root Node:** `TabPane` - This allows modular navigation between the four required sections: User Management, Event Management, Booking, and Waitlist.
- **Form Layouts:** We will use `GridPane` for the input forms (Add User / Create Event) to ensure alignment, as demonstrated in the JavaFX lab.

## 1.2 Data Integration
- **Direct Interaction:** The GUI will interface directly with the object-oriented classes (`User`, `Event`, `Booking`).
- **Observable Lists:** I will implement `ObservableList` wrappers for the HashMaps provided by the CSV parser to allow real-time `TableView` updates.
- **Style Note:** For Phase 1, the UI will use the default JavaFX "Modena" theme to focus entirely on core functionality and data persistence.

# 2. Module Breakdown
## 2.1 User & Event Views
- **Tables:** `TableView` nodes will display the `users.csv` and `events.csv` state.
- **Inputs:** `TextField` and `ChoiceBox` for data entry.
- **Date Picking:** `DatePicker` will be utilized for all `event_date` fields.

## 2.2 Functional Logic
- **onAction Handling:** Every button (e.g., "Confirm Booking") will be linked to a method in the Controller class via Scene Builder.
- **Validation:** Basic null-checks on text fields to prevent empty objects from being passed to the constructors.

# Citations
- **Lab Materials:** JavaFX Plugin setup and FXML/Scene Builder integration.
- **YouTube Transcript:** ChoiceBox and dropdown selection logic.
- **YouTube Transcript:** DatePicker and LocalDate-to-Date conversion.
- **YouTube Transcript:** TableView population and ObservableList management.