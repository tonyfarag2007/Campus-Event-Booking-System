// User class for GUI testing and data structure
public class User {
    public enum UserType { Student, Staff, Guest }

    // Variables use snake_case as per README
    private String user_id;
    private String user_name;
    private String user_email;
    private UserType user_type;

    public User(String user_name, String user_id, String user_email, UserType user_type) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_type = user_type;
    }

    // Getters use camelCase for JavaFX PropertyValueFactory
    public String getUserId() {
        return this.user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return this.user_name;
    }

    public void setName(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return this.user_email;
    }

    public void setEmail(String user_email) {
        this.user_email = user_email;
    }

    public UserType getType() {
        return this.user_type;
    }

    public void setType(UserType user_type) {
        this.user_type = user_type;
    }
}