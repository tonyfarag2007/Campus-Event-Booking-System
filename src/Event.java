import java.util.Date;

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
    // Enums are public so they can be used "anywhere", not strictly class attributes
    public static final enum EventType { Workshop, Seminar, Concert };
    public static final enum EventStatus { Active, Cancalled };
    // NOTE: eventID will need a concrete type sometime once we figure out if it can be just an int or string.
    // like "E0001", for now it is a string. It is also final since ID should never change.
    private final String eventID;
    private String eventTitle;
    private Date eventDate;
    private String eventLocation;
    // Note that capacity MUST be > 0 when deserializing or serializing data (i.e. when loading from CSV or getting user input)
    private int eventCapacity;
    private String confirmedUsers;
    private String waitlistedUsers;

    // EVENT SPECIFIC ATTRIBUTES, These may be implemented with inheritance if need be down the line!
    private String workshopTopic;
    private String seminarSpeakerName;
    // Note that age restriction is just for display and nothing more.
    private int concertAgeRestriction;

    Event(String eventID, String eventTitle, Date eventDate,
          String eventLocation, int eventCapacity, String confirmedusers,
          String waitlistedUsers) {
        this.eventID = eventID;
        this.eventTitle = eventTitle;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
        this.eventCapacity = eventCapacity;
        this.confirmedUsers = confirmedusers;
        this.waitlistedUsers = waitlistedUsers;
    }
    Event(String eventID, String eventTitle, Date eventDate,
          String eventLocation, int eventCapacity, String confirmedusers,
          String waitlistedUsers, String workshopTopic) {
        this.eventID = eventID;
        this.eventTitle = eventTitle;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
        this.eventCapacity = eventCapacity;
        this.confirmedUsers = confirmedusers;
        this.waitlistedUsers = waitlistedUsers;
        this.workshopTopic = workshopTopic;
    }
    Event(String eventID, String eventTitle, Date eventDate,
          String eventLocation, int eventCapacity, String confirmedusers,
          String waitlistedUsers, String seminarSpeakerName) {
        this.eventID = eventID;
        this.eventTitle = eventTitle;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
        this.eventCapacity = eventCapacity;
        this.confirmedUsers = confirmedusers;
        this.waitlistedUsers = waitlistedUsers;
        this.seminarSpeakerName = seminarSpeakerName;
    }
    Event(String eventID, String eventTitle, Date eventDate,
          String eventLocation, int eventCapacity, String confirmedusers,
          String waitlistedUsers, int concertAgeRestriction) {
        this.eventID = eventID;
        this.eventTitle = eventTitle;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
        this.eventCapacity = eventCapacity;
        this.confirmedUsers = confirmedusers;
        this.waitlistedUsers = waitlistedUsers;
        this.concertAgeRestriction = concertAgeRestriction;
    }
}