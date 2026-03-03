import java.util.ArrayList;

public class Booking extends CSVSerializable {

    private String b_id;
    private String u_id;
    private String e_id;
    private String e_time;
    private String b_status;

    // Fixed by Qasim: capitalized constructor to match class name so it compiles
    public Booking(String booking_id, String user_id, String event_id, String event_time, String booking_status){
        this.b_id = booking_id;
        this.u_id = user_id;
        this.e_id = event_id;
        this.e_time = event_time;
        this.b_status = booking_status;
    }

    // Getters
    public String get_book_id(){
        return this.b_id;
    }
    public String get_user_id(){
        return this.u_id;
    }
    public String get_event_id(){
        return this.e_id;
    }
    public String get_event_time(){
        return this.e_time;
    }
    public String get_book_status(){
        return this.b_status;
    }

    // Setters
    public void set_book_id(String booking_id){
        this.b_id = booking_id;
    }
    public void set_user_id(String user_id){
        this.u_id = user_id;
    }
    public void set_event_id(String event_id){
        this.e_id = event_id;
    }
    public void set_event_time(String event_time){
        this.e_time = event_time;
    }
    public void set_book_status(String booking_status){
        this.b_status = booking_status;
    }

    // CSV Parsing / Serialization
    // TODO: WHEN YOU CHANGE VARS HERE, CHANGE THIS TOO
    @Override
    public String serialize() { return String.join(",", this.b_id, this.u_id, this.e_id, this.e_time, this.b_status); }

    @Override
    public String header() { return "booking_id,user_id,event_id,event_time,booking_status"; }

    @Override
    public Booking deserialize(ArrayList<String> csv_values) {
        if (csv_values.size() != 5)
            throw new IllegalArgumentException("Expected 5 values for Booking deserialization, got " + csv_values.size());

        return new Booking(csv_values.get(0), csv_values.get(1), csv_values.get(2), csv_values.get(3), csv_values.get(4));
    }
}