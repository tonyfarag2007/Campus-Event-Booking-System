public class booking{

    private String b_id;
    private String u_id;
    private String e_id;
    private String e_time;
    private String b_status;

    public booking(String booking_id, String user_id, String event_id, String event_time, String booking_status){
        this.b_id = booking_id;
        this.u_id = user_id;
        this.e_id = event_id;
        this.e_time = event_time;
        this.b_status = booking_status;
    }
    // Getters
    public String getBId(){
        return this.b_id;
    }
    public String getUId(){
        return this.u_id;
    }
    public String getEId(){
        return this.e_id;
    }
    public String getET(){
        return this.e_time;
    }
    public String getBS(){
        return this.b_status;
    }

    // Setters
    public void setBId(String booking_id){
        this.b_id = booking_id;
    }
    public void setUId(String user_id){
        this.b_id = user_id;
    }
    public void setEId(String event_id){
        this.b_id = event_id;
    }
    public void setET(String event_time){
        this.b_time = e_time;
    }
    public void setBS(String booking_status){
        this.b_status = booking_status;
    }
    
}