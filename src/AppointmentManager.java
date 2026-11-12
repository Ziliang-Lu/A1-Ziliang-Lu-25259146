import java.util.ArrayList;
import java.util.Collections;

/**
 * Manages every appointment the clinic has booked for the day.
 *
 * <p><b>Why an {@code ArrayList} and not another structure.</b> Module 2 offers
 * several collections, and each was considered before choosing this one:</p>
 * <ul>
 *   <li>a {@code HashSet} would reject duplicates by itself, but it does not
 *       keep order, and the clinic has to display a day in time order;</li>
 *   <li>a {@code PriorityQueue} keeps the earliest appointment on top, but it
 *       has no way to walk through the bookings or to remove one that is not at
 *       the top, which is exactly what cancelling needs;</li>
 *   <li>an {@code ArrayList} keeps insertion order, allows a booking to be
 *       removed from the middle, and can be sorted on demand with
 *       {@code Collections.sort(...)}, so it supports every operation the
 *       assessment asks for.</li>
 * </ul>
 *
 * <p><b>Why every operation returns a message.</b> The assessment asks for a
 * warning when an action cannot be performed, so each method returns the text
 * that describes what happened. Nothing is printed inside this class, which
 * keeps the manager usable from anywhere and keeps the demonstration in charge
 * of the layout.</p>
 *
 * @author Ziliang Lu (25259146)
 * @version 1.0
 */
public class AppointmentManager {

    /** The wording used when a booking has to be refused. */
    private static final String NOT_BOOKED = "Cannot book: ";

    /** The wording used when a cancellation has to be refused. */
    private static final String NOT_CANCELLED = "Cannot cancel: ";

    /**
     * The appointments booked for the day, in the order they were added.
     *
     * <p>The list is only ever changed through this class, which is what makes
     * the "no double booking" rule trustworthy: no other code can put an
     * appointment into the list without passing the check.</p>
     */
    private ArrayList<Appointment> appointments;

    /**
     * Creates an empty manager with no appointments booked.
     */
    public AppointmentManager() {
        appointments = new ArrayList<Appointment>();
    }

    /**
     * Books an appointment after checking that everything needed is present and
     * that the health professional is free at that time.
     *
     * <p>The three values are checked one at a time so the warning says which
     * piece of information is missing. The double-booking check then asks the
     * list whether it already holds an appointment for the same professional at
     * the same time, which works because {@link Appointment#equals(Object)} is
     * defined as exactly that pair.</p>
     *
     * @param patient the patient who is booking; must not be null
     * @param healthProfessional the professional to see; must not be null
     * @param timeSlot the requested time; must not be null
     * @return a message describing the booking or explaining the refusal
     */
    public String addAppointment(Patient patient, HealthProfessional healthProfessional,
                                 TimeSlot timeSlot) {
        if (patient == null) {
            return NOT_BOOKED + "the patient's details are missing.";
        }
        if (healthProfessional == null) {
            return NOT_BOOKED + "no health professional was chosen.";
        }
        if (timeSlot == null) {
            return NOT_BOOKED + "no time slot was chosen.";
        }

        Appointment candidate = Appointment.book(patient, healthProfessional, timeSlot);

        // book(...) only returns null when one of the three values is null, and
        // all three are checked above, so this branch is defensive rather than
        // expected. It keeps the compiler's promise that candidate is never null
        // from being taken on trust.
        if (candidate == null) {
            return NOT_BOOKED + "the appointment is incomplete.";
        }

        if (appointments.contains(candidate)) {
            return NOT_BOOKED + healthProfessional.getName() + " is already booked at "
                 + timeSlot.getLabel() + ".";
        }

        appointments.add(candidate);
        return "Booked: " + patient.getName() + " with " + healthProfessional.getName()
             + " at " + timeSlot.getLabel() + ".";
    }

    /**
     * Returns every appointment, earliest first.
     *
     * <p>A copy is sorted and returned, so the manager keeps the bookings in
     * the order they were made and the caller cannot reorder the manager's own
     * list by accident.</p>
     *
     * @return a new list holding every appointment in time order
     */
    public ArrayList<Appointment> getAllAppointments() {
        ArrayList<Appointment> copy = new ArrayList<Appointment>(appointments);
        sortByTime(copy);
        return copy;
    }

    /**
     * Returns the appointments booked with one health professional.
     *
     * <p>The professional is identified by the identifier rather than by the
     * object, because the caller normally has the identifier (from a booking
     * form, for example) and not the object itself.</p>
     *
     * @param identifier the professional's identifier; may be null, in which
     *        case no appointments match
     * @return a new list holding the matching appointments in time order
     */
    public ArrayList<Appointment> filterByHealthProfessional(String identifier) {
        ArrayList<Appointment> matches = new ArrayList<Appointment>();

        for (int index = 0; index < appointments.size(); index++) {
            Appointment appointment = appointments.get(index);
            if (appointment.getHealthProfessional().getIdentifier().equals(identifier)) {
                matches.add(appointment);
            }
        }

        sortByTime(matches);
        return matches;
    }

    /**
     * Returns the appointments booked by one patient.
     *
     * <p>The patient is identified by the phone number, which is the detail a
     * patient is asked for at the desk and the only one the clinic can match
     * reliably when two patients share a name.</p>
     *
     * @param phoneNumber the patient's mobile number, digits only; may be null,
     *        in which case no appointments match
     * @return a new list holding the matching appointments in time order
     */
    public ArrayList<Appointment> filterByPatientPhoneNumber(String phoneNumber) {
        ArrayList<Appointment> matches = new ArrayList<Appointment>();

        for (int index = 0; index < appointments.size(); index++) {
            Appointment appointment = appointments.get(index);
            if (appointment.getPatient().getPhoneNumber().equals(phoneNumber)) {
                matches.add(appointment);
            }
        }

        sortByTime(matches);
        return matches;
    }

    /**
     * Sorts a list of appointments from earliest to latest.
     *
     * <p>It is public and takes the list as a parameter rather than sorting only
     * the manager's own bookings, because the assessment asks for sorting to
     * work on a filtered subset as well as on everything: the caller passes
     * whichever list it holds and gets the same order back.</p>
     *
     * @param list the appointments to sort; the list is sorted in place
     */
    public void sortByTime(ArrayList<Appointment> list) {
        Collections.sort(list);
    }

    /**
     * Cancels the appointment for one professional at one time.
     *
     * <p>Both values are needed together because a professional has several
     * appointments in a day, so neither the identifier nor the time alone
     * identifies one booking.</p>
     *
     * @param identifier the professional's identifier
     * @param timeSlot the time of the appointment to cancel
     * @return a message describing the cancellation or explaining the refusal
     */
    public String cancelByHealthProfessionalAndTime(String identifier, TimeSlot timeSlot) {
        if (timeSlot == null) {
            return NOT_CANCELLED + "no time slot was given.";
        }

        for (int index = 0; index < appointments.size(); index++) {
            Appointment appointment = appointments.get(index);

            boolean sameProfessional =
                    appointment.getHealthProfessional().getIdentifier().equals(identifier);
            boolean sameTime = appointment.getTimeSlot().equals(timeSlot);

            if (sameProfessional && sameTime) {
                appointments.remove(index);
                return "Cancelled: the appointment with professional " + identifier
                     + " at " + timeSlot.getLabel() + ".";
            }
        }

        return NOT_CANCELLED + "no appointment at " + timeSlot.getLabel()
             + " for professional " + identifier + ".";
    }

    /**
     * Cancels every appointment booked by one patient.
     *
     * <p>A phone number does not identify a single booking, so cancelling by
     * phone number cancels all of that patient's appointments. The count is
     * reported so the patient knows how many bookings were removed.</p>
     *
     * @param phoneNumber the patient's mobile number, digits only
     * @return a message describing the cancellation or explaining the refusal
     */
    public String cancelByPatientPhoneNumber(String phoneNumber) {
        int cancelled = 0;

        // The list is walked backwards so that removing an element never skips
        // the one that moves into its place.
        for (int index = appointments.size() - 1; index >= 0; index--) {
            Appointment appointment = appointments.get(index);
            if (appointment.getPatient().getPhoneNumber().equals(phoneNumber)) {
                appointments.remove(index);
                cancelled++;
            }
        }

        if (cancelled == 0) {
            return NOT_CANCELLED + "no appointments found for " + phoneNumber + ".";
        }
        return "Cancelled " + cancelled + " appointment(s) for " + phoneNumber + ".";
    }

    /**
     * Returns how many appointments are currently booked.
     *
     * @return the number of appointments in the manager
     */
    public int getAppointmentCount() {
        return appointments.size();
    }
}
