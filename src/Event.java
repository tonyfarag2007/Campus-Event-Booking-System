import java.util.Date;

public class Event {
    /*
    NOTE: Checking for proper input to the constructor should be done by the "client"
    (i.e. it is not managed by the constructor, though this may change during development)

    TO-DO: Ask if inheritance is needed here. May be ok to implement.
    TO-DO: Implement listing events (dump everything?)
    TO-DO: Search events (search by title w/ partial/case-insensitive match), filter by event type as well)
    TO-DO: Sort based on levenshtein distance: https://en.wikipedia.org/wiki/Levenshtein_distance
        - ArrayList of string containing title + int score, subject to change
    TO-DO: Should be able to dump confirmed/waitlisted users
        - Either write the entire waitlist/confirmed user strings or we end up writing all users from an ArrayList, up to parsing implementation.
     */

    // REMOVED 'final' modifier: Enums in Java are implicitly final.
    // Including 'final' here causes a compiler error in Java 25.
    public enum EventType { Workshop, Seminar, Concert };
    public enum EventStatus { Active, Cancelled };

    private final String event_id;
    private String event_title;
    private Date event_date;
    private String event_location;
    private int event_capacity;
    private String confirmed_users;
    private String waitlisted_users;

    private String workshop_topic;
    private String seminar_speaker_name;
    private int convert_age_restriction;

    // Constructor 1: Base
    Event(String event_id, String event_title, Date event_date,
          String event_location, int event_capacity, String confirmed_users,
          String waitlisted_users) {
        this.event_id = event_id;
        this.event_title = event_title;
        this.event_date = event_date;
        this.event_location = event_location;
        this.event_capacity = event_capacity;
        this.confirmed_users = confirmed_users;
        this.waitlisted_users = waitlisted_users;
    }

    // Constructor 2: Workshop
    Event(String event_id, String event_title, Date event_date,
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
    }

    /*
     * COMMENTED OUT BY QASIM:
     * This constructor conflicts with the Workshop constructor above.
     * Both take (String, String, Date, String, int, String, String, String).
     * Java cannot distinguish them based on variable names alone.
     * We should move to a single 'Master' constructor or use Inheritance in Phase 2.
     *
    Event(String event_id, String event_title, Date event_date,
          String event_location, int event_capacity, String confirmed_users,
          String waitlisted_users, String seminar_speaker_name) {
        this.event_id = event_id;
        this.event_title = event_title;
        this.event_date = event_date;
        this.event_location = event_location;
        this.event_capacity = event_capacity;
        this.confirmed_users = confirmed_users;
        this.waitlisted_users = waitlisted_users;
        this.seminar_speaker_name = seminar_speaker_name;
    }
    */

    // Constructor 4: Concert (This one is fine because the last param is an int)
    Event(String event_id, String event_title, Date event_date,
          String event_location, int event_capacity, String confirmed_users,
          String waitlisted_users, int convert_age_restriction) {
        this.event_id = event_id;
        this.event_title = event_title;
        this.event_date = event_date;
        this.event_location = event_location;
        this.event_capacity = event_capacity;
        this.confirmed_users = confirmed_users;
        this.waitlisted_users = waitlisted_users;
        this.convert_age_restriction = convert_age_restriction;
    }
}