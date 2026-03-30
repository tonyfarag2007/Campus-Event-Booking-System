Campus Event Booking System – Complete Starter Dataset

This package contains a carefully prepared dataset for the Campus Event Booking System project.

Included files
--------------
1. users.csv
2. events.csv
3. bookings.csv

File details
------------
users.csv
Header:
userId,name,email,userType

Rules:
- userId is unique
- userType is one of: Student, Staff, Guest

Summary:
- Total users: 24
- Students: 12
- Staff: 7
- Guests: 5

events.csv
Header:
eventId,title,dateTime,location,capacity,status,eventType,topic,speakerName,ageRestriction

Rules:
- eventId is unique
- capacity > 0
- status is one of: Active, Cancelled
- eventType is one of: Workshop, Seminar, Concert
- If eventType = Workshop, only topic is filled
- If eventType = Seminar, only speakerName is filled
- If eventType = Concert, only ageRestriction is filled

Summary:
- Total events: 12
- Active events: 9
- Cancelled events: 3
- Workshops: 4
- Seminars: 4
- Concerts: 4

bookings.csv
Header:
bookingId,userId,eventId,createdAt,bookingStatus

Rules:
- bookingId is unique
- createdAt uses format yyyy-MM-dd'T'HH:mm
- bookingStatus is one of: Confirmed, Waitlisted, Cancelled
- Waitlist order must be reconstructed by createdAt for bookings with status Waitlisted
- For cancelled events, all bookings are Cancelled

Summary:
- Total bookings: 42
- Confirmed: 24
- Waitlisted: 9
- Cancelled: 9

Built-in validation scenarios
-----------------------------
1. Full event with waitlist:
   - E101, E102, E201, E202, E301, E302, E303

2. Multi-person waitlist:
   - E102 has two waitlisted bookings
   - E201 has two waitlisted bookings
   - E302 has two waitlisted bookings

3. Cancelled events:
   - E401, E402, E403
   - All related bookings are already marked Cancelled

4. Cancelled booking on active event:
   - E103 includes one cancelled booking
   - E203 includes one cancelled booking

Important note
--------------
For events.csv, some type-specific cells are intentionally blank.
This is correct and required by the project specification:
- Workshop -> topic filled; speakerName and ageRestriction blank
- Seminar -> speakerName filled; topic and ageRestriction blank
- Concert -> ageRestriction filled; topic and speakerName blank