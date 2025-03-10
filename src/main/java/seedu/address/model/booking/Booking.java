package seedu.address.model.booking;

import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class Booking {
    public enum Status{
        UPCOMING,
        CANCELLED,
        ONGOING,
        COMPLETED
    }

    private static int bookingIdCounter = 0;
    private int bookingId;
    private LocalDateTime bookingDate;
    private LocalDateTime bookingMadeDate;
    private Person bookingPerson;
    Set<Tag> tags;
    private Status status;
    private String remarks;
    private int pax;

    public static void setBookingIdCounter(int bookingIdCounter) {
        Booking.bookingIdCounter = bookingIdCounter;
    }

    public Booking(Person bookingPerson, LocalDateTime bookingDate, Set<Tag> tags, String remarks, int pax) {
        requireAllNonNull(bookingDate, bookingPerson, tags);
        this.bookingPerson = bookingPerson;
        this.bookingDate = bookingDate;
        this.bookingMadeDate = LocalDateTime.now();
        this.tags = tags;
        this.status = Status.UPCOMING;
        this.remarks = remarks;
        this.pax = pax;
        this.bookingId = bookingIdCounter;
        bookingIdCounter++;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public LocalDateTime getBookingMadeDate() {
        return bookingMadeDate;
    }

    public Person getBookingPerson() {
        return bookingPerson;
    }

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public String getRemarks() {
        return remarks;
    }

    public int getPax() {
        return pax;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        String tagsString = tags.toString();
        if (tags.isEmpty()) {
            tagsString = "No Remarks";
        }

        // duplicate between here and ParserUtil
        // TODO: fix the duplication
        String formatter = "yyyy-MM-dd h:mm a";
        String bookingDate = this.bookingDate.format(java.time.format.DateTimeFormatter.ofPattern(formatter));
        String bookingMadeDate = this.bookingMadeDate.format(java.time.format.DateTimeFormatter.ofPattern(formatter));

        builder.append("Booking ID: ")
                .append(bookingId)
                .append("Booking Date: ")
                .append(bookingDate)
                .append(" Booked On: ")
                .append(bookingMadeDate)
                .append(" Booked By: ")
                .append(getBookingPerson().toString())
                .append(" Tags: ")
                .append(tagsString)
                .append(" Status: ")
                .append(getStatus())
                .append(" Remarks: ")
                .append(getRemarks())
                .append(" Pax: ")
                .append(getPax());

        return builder.toString();
    }
}
