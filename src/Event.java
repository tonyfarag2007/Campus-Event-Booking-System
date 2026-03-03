import java.util.Date;
import java.util.ArrayList;

public class Event {
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
    public enum EventType { Workshop, Seminar, Concert };
    public enum EventStatus { Active, Cancalled };

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
    // Note that this is not initialized in the constructor. The Booking class will manipulate it accordingly.
    private ArrayList<String> booking_ids;

    // EVENT SPECIFIC ATTRIBUTES, These may be implemented with inheritance if need be down the line!
    private String workshop_topic;
    private String seminar_speaker_name;
    // Note that age restriction is just for display and nothing more.
    private int concert_age_restriction;

    // Base constructor
    public Event(String event_id, String event_title, Date event_date,
                 String event_location, int event_capacity, String confirmed_users,
                 String waitlisted_users) {
        this.event_id = event_id;
        this.event_title = event_title;
        this.event_date = event_date;
        this.event_location = event_location;
        this.event_capacity = event_capacity;
        this.confirmed_users = confirmed_users;
        this.waitlisted_users = waitlisted_users;
        this.booking_ids = new ArrayList<String>();
    }

    // Workshop constructor
    public Event(String event_id, String event_title, Date event_date,
                 String event_location, int event_capacity, String confirmed_users,
                 String waitlisted_users, String workshop_topic) {
        this.event_id = event_id;
        this.event_title = event_title;
        this.event_date = event_date;
        this.event_location = event_location;
        this.event_capacity = event_capacity;
        this.confirmed_users = confirmed_users;
        this.waitlisted_users = waitlisted_users;
        this.workshop_topic = workshop_topic;
        this.booking_ids = new ArrayList<String>();
    }

    // Concert constructor
    public Event(String event_id, String event_title, Date event_date,
                 String event_location, int event_capacity, String confirmed_users,
                 String waitlisted_users, int concert_age_restriction) {
        this.event_id = event_id;
        this.event_title = event_title;
        this.event_date = event_date;
        this.event_location = event_location;
        this.event_capacity = event_capacity;
        this.confirmed_users = confirmed_users;
        this.waitlisted_users = waitlisted_users;
        this.concert_age_restriction = concert_age_restriction;
        this.booking_ids = new ArrayList<String>();
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
    public int getConcertAgeRestriction() { return this.concert_age_restriction; }

    // setters
    public void set_event_title(String new_event_title) { this.event_title = new_event_title; }
    public void set_event_date(Date new_event_date) { this.event_date = new_event_date; }
    public void set_event_location(String new_event_location) { this.event_location = new_event_location; }
    public void set_event_capacity(int new_event_capacity) { this.event_capacity = new_event_capacity; }
    public void set_confirmed_users(String new_confirmed_users) { this.confirmed_users = new_confirmed_users; }
    public void set_waitlisted_users(String new_waitlisted_users) { this.waitlisted_users = new_waitlisted_users; }
    public void set_booking_ids(ArrayList<String> new_booking_ids) { this.booking_ids = new_booking_ids; }
    public void set_workshop_topic(String new_workshop_topic) { this.workshop_topic = new_workshop_topic; }
    public void set_seminar_speaker_name(String new_seminar_speaker_name) { this.seminar_speaker_name = new_seminar_speaker_name; }
    public void set_concert_age_restriction(int new_concert_age_restriction) { this.concert_age_restriction = new_concert_age_restriction; }
}