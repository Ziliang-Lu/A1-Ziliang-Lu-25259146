import java.util.ArrayList;
import java.util.Collections;

/**
 * Part 5: manages every appointment booked for the day.
 *
 * Fields: appointments.
 * Methods: addAppointment(), getAllAppointments(),
 *          filterByHealthProfessional(), filterByPatientPhoneNumber(),
 *          sortByTime(), cancelByHealthProfessionalAndTime(),
 *          cancelByPatientPhoneNumber(), getAppointmentCount(),
 *          countAppointmentsFor().
 *
 * An ArrayList is used because the day has to be displayed in time order and a
 * booking has to be removed from the middle of the list.
 */
public class AppointmentManager {

    private static final String NOT_BOOKED = "Cannot book: ";           // refusal prefix
    private static final String NOT_CANCELLED = "Cannot cancel: ";      // cancellation prefix

    private ArrayList<Appointment> appointments;   // only this class changes it

    /**
     * Creates an empty manager with no appointments booked.
     */
    public AppointmentManager() {
        appointments = new ArrayList<Appointment>();
    }

    /**
     * Books an appointment after checking the details and the double booking.
     *
     * @param patient the patient who is booking, must not be null
     * @param healthProfessional the professional to see, must not be null
     * @param timeSlot the requested time, must not be null
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

        if (candidate == null) {
            return NOT_BOOKED + "the appointment is incomplete.";
        }

        // contains() uses Appointment.equals(), which compares the professional
        // and the time slot, so this is the double booking check
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
     * @return a new list holding every appointment in time order
     */
    public ArrayList<Appointment> getAllAppointments() {
        ArrayList<Appointment> copy = new ArrayList<Appointment>(appointments);
        sortByTime(copy);
        return copy;
    }

    /**
     * Returns the appointments booked with one professional.
     *
     * @param identifier the professional's identifier
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
     * @param phoneNumber the patient's mobile number, digits only
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
     * @param list the appointments to sort, the list is sorted in place
     */
    public void sortByTime(ArrayList<Appointment> list) {
        Collections.sort(list);
    }

    /**
     * Counts the appointments booked with one professional.
     *
     * @param identifier the professional's identifier
     * @return how many appointments that professional has today
     */
    public int countAppointmentsFor(String identifier) {
        int count = 0;

        for (int index = 0; index < appointments.size(); index++) {
            Appointment appointment = appointments.get(index);
            if (appointment.getHealthProfessional().getIdentifier().equals(identifier)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Cancels the appointment for one professional at one time.
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
     * @param phoneNumber the patient's mobile number, digits only
     * @return a message describing the cancellation or explaining the refusal
     */
    public String cancelByPatientPhoneNumber(String phoneNumber) {
        int cancelled = 0;

        // walks backwards so removing an element never skips the next one
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
