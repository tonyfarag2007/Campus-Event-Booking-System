import java.util.ArrayList;
import java.util.List;
import java.util.InputMismatchException;

// User class for GUI testing and data structure
public class User extends CSVSerializable {
    public enum UserType { Student, Staff, Guest }

    // Variables use snake_case as per README
    private String user_id;
    private String user_name;
    private String user_email;
    private UserType user_type;
    private int user_book_limit;
    private int user_book_count;

    // for deserialize.
    public User() {}
    public User(String user_id){
        this.user_id = user_id;
    }

    public User(String user_name, String user_id, String user_email, UserType user_type) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_type = user_type;
        if (user_type == UserType.Guest) this.user_book_limit = 1;
        if (user_type == UserType.Student) this.user_book_limit = 3;
        if (user_type == UserType.Staff) this.user_book_limit = 5;
        this.user_book_count = 0;

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

    public int getBookAmount(){
        return this.user_book_count;
    }

    public void changeBookAmount(int num){
        this.user_book_count+=num;
    }

    public int getBookLimit(){
        return this.user_book_limit;
    }


    // Check for any duplicate IDs to make sure no ID can register more than once
    public static void check_dup_user_id(String new_id, List<User> list){
        for(User u : list){
            if(new_id.equalsIgnoreCase(u.getUserId())){
                throw new DuplicateIdException(new_id);
            }
        }
    }
    public void validate_email(User user){
        if(!user.getEmail().endsWith("@uoguelph.ca")){
            throw new InvalidEmailException(user.getEmail());
        }
    }
    public void validate_name(User user){
        if(user.getName().length() < 3){
            throw new InvalidNameException(user.getName());
        }
    }

    // CSV Parsing / Serialization
    // TODO: WHEN YOU CHANGE VARS HERE, CHANGE THIS TOO
    @Override
    public String serialize() {
        return String.join(",", this.user_id, this.user_name, this.user_email, this.user_type.toString());
    }

    @Override
    public String header() { return "userId,name,email,userType"; }

    @Override
    public User deserialize(ArrayList<String> csv_values) {
       return new User(csv_values.get(1), csv_values.get(0), csv_values.get(2), UserType.valueOf(csv_values.get(3)));
    }

}