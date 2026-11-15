/**
 * Part 4: one appointment.
 *
 * Fields: patient, healthProfessional, timeSlot.
 * Methods: book(), getPatient(), getHealthProfessional(), getTimeSlot(),
 *          compareTo(), equals(), hashCode(), toString().
 *
 * The appointment holds references to the patient, the professional and the
 * slot instead of copying their details, so a change to a doctor's room is
 * visible everywhere at once.
 */
public class Appointment implements Comparable<Appointment> {

    private Patient patient;                         // the patient who booked
    private HealthProfessional healthProfessional;   // any of the four types
    private TimeSlot timeSlot;                       // the booked time slot

    /**
     * Creates an appointment.
     *
     * The constructor is private, so an appointment can only be created through
     * book(), which refuses to build one when a value is missing.
     *
     * @param patient the patient who booked
     * @param healthProfessional the professional the patient will see
     * @param timeSlot the slot the appointment occupies
     */
    private Appointment(Patient patient, HealthProfessional healthProfessional,
                        TimeSlot timeSlot) {
        this.patient = patient;
        this.healthProfessional = healthProfessional;
        this.timeSlot = timeSlot;
    }

    /**
     * Books an appointment, or returns null when something is missing.
     *
     * @param patient the patient who booked, must not be null
     * @param healthProfessional the professional the patient will see, must
     *        not be null
     * @param timeSlot the slot the appointment occupies, must not be null
     * @return the new appointment, or null when a value is missing
     */
    public static Appointment book(Patient patient, HealthProfessional healthProfessional,
                                   TimeSlot timeSlot) {
        if (patient == null || healthProfessional == null || timeSlot == null) {
            return null;
        }
        return new Appointment(patient, healthProfessional, timeSlot);
    }

    /**
     * Returns the patient who booked.
     *
     * @return the patient
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Returns the professional the appointment is with.
     *
     * @return the health professional
     */
    public HealthProfessional getHealthProfessional() {
        return healthProfessional;
    }

    /**
     * Returns the time slot of the appointment.
     *
     * @return the time slot
     */
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    /**
     * Orders two appointments from earliest to latest.
     *
     * @param other the appointment to compare against
     * @return a negative number when this appointment is earlier, zero when both
     *         are at the same time, and a positive number when this one is later
     */
    @Override
    public int compareTo(Appointment other) {
        // the time slot already knows how to compare itself
        return timeSlot.compareTo(other.timeSlot);
    }

    /**
     * Tells whether two appointments are the same booking slot.
     *
     * @param other the object to compare with
     * @return true when both appointments are for the same professional at the
     *         same time
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Appointment)) {
            return false;
        }
        Appointment that = (Appointment) other;
        return healthProfessional.equals(that.healthProfessional)
            && timeSlot.equals(that.timeSlot);
    }

    /**
     * Returns a hash code that agrees with equals().
     *
     * @return a hash code built from the professional and the time slot
     */
    @Override
    public int hashCode() {
        return healthProfessional.hashCode() * 31 + timeSlot.hashCode();
    }

    /**
     * Returns the appointment details, including the professional's details.
     *
     * @return a multi-line description of the appointment
     */
    @Override
    public String toString() {
        // shifts every line of the professional's own description to the right,
        // so the block reads as one section of the appointment
        String professionalDetails =
                healthProfessional.toString().replace("\n", "\n  ");

        return "Patient:      " + patient + "\n"
             + "Time:         " + timeSlot.getLabel() + "\n"
             + "Health professional:\n"
             + "  " + professionalDetails;
    }
}
