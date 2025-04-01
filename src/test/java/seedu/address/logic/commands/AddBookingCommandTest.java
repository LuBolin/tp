package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.booking.Booking;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

public class AddBookingCommandTest {

    @Test
    public void constructor_nullArgs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new AddBookingCommand(null, LocalDateTime.now(), "Dinner", 2));
    }

    @Test
    public void execute_validBooking_success() throws Exception {
        final Person person = new PersonBuilder().build();
        final ModelStubAcceptingBooking modelStub = new ModelStubAcceptingBooking(person);
        final LocalDateTime bookingDate = LocalDateTime.of(2025, 3, 30, 18, 0);

        // Adjust bookingDate for testing both past and future cases
        final LocalDateTime futureBookingDate = LocalDateTime.now().plusDays(1);
        final LocalDateTime pastBookingDate = LocalDateTime.now().minusDays(1);

        final String remark = "Team Dinner";
        final int pax = 5;

        // Test Case 1: Future Booking (No Warning)
        final AddBookingCommand futureCommand = new AddBookingCommand(person.getPhone(), futureBookingDate,
                remark, pax);
        final CommandResult futureResult = futureCommand.execute(modelStub);

        final Booking addedFutureBooking = modelStub.getAddressBook().getBookingList().get(0);

        final Person updatedPerson = modelStub.getAddressBook().getPersonList().stream()
                .filter(p -> p.getPhone().equals(person.getPhone()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Person not found in AddressBook"));

        String expectedFutureMessage = String.format(AddBookingCommand.MESSAGE_SUCCESS,
                Messages.format(addedFutureBooking));
        assertEquals(expectedFutureMessage, futureResult.getFeedbackToUser());
        assertTrue(updatedPerson.getBookingIDs().contains(addedFutureBooking.getBookingId()));

        // Test Case 2: Past Booking (With Warning)
        final AddBookingCommand pastCommand = new AddBookingCommand(person.getPhone(), pastBookingDate,
                remark, pax);
        final CommandResult pastResult = pastCommand.execute(modelStub);

        final Booking addedPastBooking = modelStub.getAddressBook().getBookingList().get(1);

        String expectedPastMessage = AddBookingCommand.MESSAGE_PAST_BOOKING_WARNING + "\n"
                + String.format(AddBookingCommand.MESSAGE_SUCCESS, Messages.format(addedPastBooking));

        assertEquals(expectedPastMessage.trim(), pastResult.getFeedbackToUser().trim());
        assertTrue(updatedPerson.getBookingIDs().contains(addedPastBooking.getBookingId()));
    }

    @Test
    public void execute_personNotFound_throwsCommandException() {
        final ModelStubAcceptingBooking modelStub =
                new ModelStubAcceptingBooking(new PersonBuilder().build());
        final LocalDateTime now = LocalDateTime.now();
        final Phone nonexistentPhone = new Phone("99999999");

        final AddBookingCommand command = new AddBookingCommand(nonexistentPhone, now,
                "Dinner", 4);

        assertThrows(CommandException.class,
                AddBookingCommand.MESSAGE_INVALID_PERSON, () -> command.execute(modelStub));
    }

    @Test
    public void equals() {
        final Phone phone1 = new Phone("12345678");
        final Phone phone2 = new Phone("87654321");
        final LocalDateTime time = LocalDateTime.of(2025, 3, 30, 19, 0);

        final AddBookingCommand cmd1 = new AddBookingCommand(phone1, time, "Dinner", 4);
        final AddBookingCommand cmd2 = new AddBookingCommand(phone1, time, "Dinner", 4);
        final AddBookingCommand cmd3 = new AddBookingCommand(phone2, time, "Dinner", 4);

        assertTrue(cmd1.equals(cmd1)); // same object
        assertTrue(cmd1.equals(cmd2)); // same values
        assertFalse(cmd1.equals(null)); // null
        assertFalse(cmd1.equals("Some string")); // different type
        assertFalse(cmd1.equals(cmd3)); // different phone
    }

    @Test
    public void toStringMethod() {
        final LocalDateTime bookingDate = LocalDateTime.of(2025, 3, 30, 20, 0);
        final AddBookingCommand command = new AddBookingCommand(TypicalPersons.ALICE.getPhone(), bookingDate,
                 "Birthday", 3);
        final String expected = AddBookingCommand.class.getCanonicalName()
                + "{phone=" + TypicalPersons.ALICE.getPhone()
                + ", bookingDate=" + bookingDate + "}";
        assertEquals(expected, command.toString());
    }

    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {

        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {

        }

        @Override
        public void addBooking(Booking booking) {

        }

        @Override
        public ObservableList<Booking> getFilteredBookingList() {
            return null;
        }

        @Override
        public void updateFilteredBookingList(Predicate<Booking> predicate) {

        }

        @Override
        public Predicate<Person> getCurrentPersonPredicate() {
            return null;
        }

        @Override
        public Predicate<Booking> getCurrentBookingPredicate() {
            return null;
        }


        @Override
        public boolean isBookingListFiltered() {
            return false;
        }
    }

    private class ModelStubAcceptingBooking extends ModelStub {
        private final AddressBook addressBook = new AddressBook();

        ModelStubAcceptingBooking(Person person) {
            addressBook.addPerson(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return addressBook;
        }

        @Override
        public void addBooking(Booking booking) {
            addressBook.addBooking(booking);
        }
    }
}
