import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Event extends CSVSerializable {
    /*
NOTE: Checking for proper input to the constructor should be done by the "client"
(i.e. it is not managed by the constructor)

TODO: Search events (search by title w/ partial/case-insensitive match), filter by event type as well)
TODO: Sort based on levenshtein distance: https://en.wikipedia.org/wiki/Levenshtein_distance
    - ArrayList of string containing title + int score, subject to change
TODO: Should be able to dump confirmed/waitlisted users
    - Either write the entire waitlist/confirmed user strings or we end up writing all users from an ArrayList, up to parsing implementation.
     */

    // Qasim: IntelliJ gave a "modifier final not allowed here" error on these, so I just let the IDE remove the 'final' keyword.
    // Aleks: Whenever this is implemented, should either add it to CSV serialization or tell me to do it, left it out for now because unimplemented
    public enum EventType {Workshop, Seminar, Concert};

    // NOTE: Commented out as this was unused
    //public enum EventStatus {Active, Cancelled}

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
    // fixed from int to string
    private String concert_age_restriction;

    // never use this, its only for temp init.
    public Event() {
        this.event_id = "";
    }

    // Base constructor for Deserialize / total initialization.
    public Event(String event_id, String event_title, Date event_date,
                 String event_location, int event_capacity, String confirmed_users,
                 String waitlisted_users, ArrayList<String> booking_ids, String workshop_topic, String seminar_speaker_name, String concert_age_restriction) {
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
        this(event_id, event_title, event_date, event_location, event_capacity, confirmed_users, waitlisted_users, new ArrayList<>(), "", "", "");
    }

    // Concert constructor
    public Event(String event_id, String event_title, Date event_date,
                 String event_location, int event_capacity, String confirmed_users,
                 String waitlisted_users, String concert_age_restriction) {
        this(event_id, event_title, event_date, event_location, event_capacity, confirmed_users, waitlisted_users);

        this.concert_age_restriction = concert_age_restriction;
    }

    // getters
    public String getEventId() { return this.event_id; }
    public String getEventTitle() { return this.event_title; }
    public Date getEventDate() { return this.event_date; }
    public String getEventLocation() { return this.event_location; }
    public int getEventCapacity() { return this.event_capacity; }
    public String getConfirmedUsers() { return this.confirmed_users; }
    public String getWaitlistedUsers() { return this.waitlisted_users; }
    public ArrayList<String> getBookingIds() { return this.booking_ids; }
    public String getWorkshopTopic() { return this.workshop_topic; }
    public String getSeminarSpeakerName() { return this.seminar_speaker_name; }
    public String getConcertAgeRestriction() { return this.concert_age_restriction; }

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

    public void set_concert_age_restriction(String new_concert_age_restriction) {
        this.concert_age_restriction = new_concert_age_restriction;
    }

    // Validation method added by Qasim to support Unit Testing requirements
    public static boolean check_dup_event_id(String new_id, ArrayList<Event> existing_events) {
        for (Event e : existing_events) {
            if (e.getEventId().equals(new_id)) {
                return true; // Duplicate found
            }
        }
        return false; // Safe to add
    }

    // CSV Parsing / Serialization
    // TODO: WHEN YOU CHANGE VARS HERE, CHANGE THIS TOO
    @Override
    public String serialize() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        String eventType = "Standard";
        if (this.workshop_topic != null && !this.workshop_topic.isEmpty()) {
            eventType = "Workshop";
        } else if (this.seminar_speaker_name != null && !this.seminar_speaker_name.isEmpty()) {
            eventType = "Seminar";
        } else if (this.concert_age_restriction != null && !this.concert_age_restriction.isEmpty()) {
            eventType = "Concert";
        }

        return String.join(",",
                this.event_id,
                this.event_title,
                sdf.format(this.event_date),
                this.event_location,
                Integer.toString(this.event_capacity),
                "Active",
                eventType,
                this.workshop_topic == null ? "" : this.workshop_topic,
                this.seminar_speaker_name == null ? "" : this.seminar_speaker_name,
                this.concert_age_restriction == null ? "" : this.concert_age_restriction);
    }

    @Override
    public String header() {
        return "eventId,title,dateTime,location,capacity,status,eventType,topic,speakerName,ageRestriction";
    }

    @Override
    public Event deserialize(ArrayList<String> csv_values) {
        String event_id = csv_values.get(0);
        String event_title = csv_values.get(1);

        // Fix: Converting "2026-09-10T14:30" to "2026/09/10 14:30" so the legacy Date constructor accepts it.
        // Logic based on DatePicker string manipulation.
        String raw_date = csv_values.get(2).replace("-", "/").replace("T", " ");
        var event_date = new Date(raw_date);

        String event_location = csv_values.get(3);
        int event_capacity = Integer.parseInt(csv_values.get(4));

        // Professor's CSV has 'status' and 'eventType' at indices 5 and 6.
        // We skip those and use empty strings for the attendee lists which aren't in the file.
        String workshop_topic = csv_values.get(7);
        String seminar_speaker_name = csv_values.get(8);
        String age_limit = csv_values.get(9);

        return new Event(event_id, event_title, event_date, event_location, event_capacity, "", "", new ArrayList<>(), workshop_topic, seminar_speaker_name, age_limit);
    }
}