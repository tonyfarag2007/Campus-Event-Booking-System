public class User {
    public enum UserType{Student, Staff, Guest}
    private UserType type;
    private String name;
    private String user_id;
    private String email;
    public User(String name, String user_id, String email, UserType type){
        setName(name);
        this.user_id = user_id;
        setEmail(email);
        this.type = type;
    }
    // Getters and setters
    public UserType getType(){
        return this.type;
    }
    public void setType(UserType type){
        this.type = type;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getUserId(){
     return user_id;
    }
    // setUserId should only be used when updating ID due to event queue changes
    public void setUserId(String user_id) {this.user_id = user_id;}
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
}
