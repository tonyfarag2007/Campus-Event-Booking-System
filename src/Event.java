import java.util.Arrays;
import java.util.Date;
import java.util.ArrayList;

public class Event extends CSVSerializable {
    /*
NOTE: Checking for proper input to the constructor should be done by the "client"
(i.e. it is not managed by the constructor, though this may change during development)

TODO: Ask if inheritance is needed here. May be ok to implement.
TODO: Implement listing events (dump everything?)
TODO: Search events (search by title w/ partial/case-insensitive match), filter by event type as well)
TODO: Sort based on levenshtein distance: https://en.wikipedia.org/wiki/Levenshtein_distance
    - ArrayList of string containing title + int score, subject to change
TODO: Should be able to dump confirmed/waitlisted users
    - Either write the entire waitlist/confirmed user strings or we end up writing all users from an ArrayList, up to parsing implementation.
     */

    // Qasim: IntelliJ gave a "modifier final not allowed here" error on these, so I just let the IDE remove the 'final' keyword.
    // Aleks: Whenever this is implemented, should either add it to CSV serialization or tell me to do it, left it out for now because unimplemented
    public enum EventType {Workshop, Seminar, Concert}

    ;

    public enum EventStatus {Active, Cancelled}

    ;

    // NOTE: event_id will need a concrete type sometime once we figure out if it can be just an int or string.
    // like "E0001", for now it is a string. It is also final since ID should never change.
    private final String event_id;
    private String event_title;
    private Date event_date;
    private String event_location;
    // Note that capacity MUST be > 0 when deserializing or serializing data (i.e. when loading from CSV or getting user input)
    private int event_capacity;
    private String confirmed_users;
    private String waitlisted_users;
    // Note that this may or may not be initialized in the constructor. The Booking class will manipulate it accordingly. It will only be present if it is existing.
    private ArrayList<String> booking_ids;

    // EVENT SPECIFIC ATTRIBUTES, These may be implemented with inheritance if need be down the line!
    // For each of these we will have a header, and it will just be blank if unused.
    private String workshop_topic;
    private String seminar_speaker_name;
    // Note that age restriction is just for display and nothing more.
    private int concert_age_restriction;

    // Base constructor for Deserialize / total initialization.
    public Event(String event_id, String event_title, Date event_date,
                 String event_location, int event_capacity, String confirmed_users,
                 String waitlisted_users, ArrayList<String> booking_ids, String workshop_topic, String seminar_speaker_name, int concert_age_restriction) {
        this.event_id = event_id;
        this.event_title = event_title;
        this.event_date = event_date;
        this.event_location = event_location;
        this.event_capacity = event_capacity;
        this.confirmed_users = confirmed_users;
        this.waitlisted_users = waitlisted_users;
        this.booking_ids = booking_ids;
        this.workshop_topic = workshop_topic;
        this.seminar_speaker_name = seminar_speaker_name;
        this.concert_age_restriction = concert_age_restriction;
    }

    // Base constructor without booking_ids, to maintain legacy functionality.
    public Event(String event_id, String event_title, Date event_date,
                 String event_location, int event_capacity, String confirmed_users,
                 String waitlisted_users) {
        this(event_id, event_title, event_date, event_location, event_capacity, confirmed_users, waitlisted_users, new ArrayList<>(), "", "", 0);
    }

    // Workshop constructor
    public Event(String event_id, String event_title, Date event_date,
                 String event_location, int event_capacity, String confirmed_users,
                 String waitlisted_users, String workshop_topic) {
        this(event_id, event_title, event_date, event_location, event_capacity, confirmed_users, waitlisted_users);

        this.workshop_topic = workshop_topic;
    }

    // Concert constructor
    public Event(String event_id, String event_title, Date event_date,
                 String event_location, int event_capacity, String confirmed_users,
                 String waitlisted_users, int concert_age_restriction) {
        this(event_id, event_title, event_date, event_location, event_capacity, confirmed_users, waitlisted_users);

        this.concert_age_restriction = concert_age_restriction;
    }

    // getters
    public String getEventId() {
        return this.event_id;
    }

    public String getEventTitle() {
        return this.event_title;
    }

    public Date getEventDate() {
        return this.event_date;
    }

    public String getEventLocation() {
        return this.event_location;
    }

    public int getEventCapacity() {
        return this.event_capacity;
    }

    public String getConfirmedUsers() {
        return this.confirmed_users;
    }

    public String getWaitlistedUsers() {
        return this.waitlisted_users;
    }

    public ArrayList<String> getBookingIds() {
        return this.booking_ids;
    }

    public String getWorkshopTopic() {
        return this.workshop_topic;
    }

    public String getSeminarSpeakerName() {
        return this.seminar_speaker_name;
    }

    public int getConcertAgeRestriction() {
        return this.concert_age_restriction;
    }

    // setters
    public void set_event_title(String new_event_title) {
        this.event_title = new_event_title;
    }

    public void set_event_date(Date new_event_date) {
        this.event_date = new_event_date;
    }

    public void set_event_location(String new_event_location) {
        this.event_location = new_event_location;
    }

    public void set_event_capacity(int new_event_capacity) {
        this.event_capacity = new_event_capacity;
    }

    public void set_confirmed_users(String new_confirmed_users) {
        this.confirmed_users = new_confirmed_users;
    }

    public void set_waitlisted_users(String new_waitlisted_users) {
        this.waitlisted_users = new_waitlisted_users;
    }

    public void set_booking_ids(ArrayList<String> new_booking_ids) {
        this.booking_ids = new_booking_ids;
    }

    public void set_workshop_topic(String new_workshop_topic) {
        this.workshop_topic = new_workshop_topic;
    }

    public void set_seminar_speaker_name(String new_seminar_speaker_name) {
        this.seminar_speaker_name = new_seminar_speaker_name;
    }

    public void set_concert_age_restriction(int new_concert_age_restriction) {
        this.concert_age_restriction = new_concert_age_restriction;
    }

    // CSV Parsing / Serialization
    // TODO: WHEN YOU CHANGE VARS HERE, CHANGE THIS TOO
    @Override
    public String serialize() {
        String booking_ids_str = String.join(";", this.booking_ids);
        return String.join(",",
                this.event_id,
                this.event_title,
                Long.toString(this.event_date.getTime() / 1000L),
                this.event_location,
                Integer.toString(this.event_capacity),
                this.confirmed_users,
                this.waitlisted_users,
                booking_ids_str,
                this.workshop_topic,
                this.seminar_speaker_name,
                Integer.toString(this.concert_age_restriction));
    }

    @Override
    public String header() {
        return "event_id,event_title,event_date,event_location,event_capacity,confirmed_users,waitlisted_users,booking_ids,workshop_topic,seminar_speaker_name,concert_age_restriction";
    }

    @Override
    public Event deserialize(ArrayList<String> csv_values) {
        // TODO: actually sanitize the values.
        String event_id = csv_values.get(0);
        String event_title = csv_values.get(1);
        var event_date = new Date(Long.parseLong(csv_values.get(2)) * 1000L);
        String event_location = csv_values.get(3);
        int event_capacity = Integer.parseInt(csv_values.get(4));
        String confirmed_users = csv_values.get(5);
        String waitlisted_users = csv_values.get(6);

        ArrayList<String> booking_ids = new ArrayList<>();
        if (!csv_values.get(7).isEmpty()) {
            String[] booking_id_array = csv_values.get(7).split(";");
            booking_ids.addAll(Arrays.asList(booking_id_array));
        }

        String workshop_topic = csv_values.get(8);
        String seminar_speaker_name = csv_values.get(9);
        int concert_age_restriction = Integer.parseInt(csv_values.get(10));

        return new Event(event_id, event_title, event_date, event_location, event_capacity, confirmed_users, waitlisted_users, booking_ids, workshop_topic, seminar_speaker_name, concert_age_restriction);
    }
}