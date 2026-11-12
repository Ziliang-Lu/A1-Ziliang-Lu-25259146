/**
 * One appointment: a patient, a health professional, and a time slot.
 *
 * <p><b>What it holds and why.</b> The appointment itself stores nothing about
 * the patient except a reference to a {@link Patient}, and nothing about the
 * professional except a reference to a {@link HealthProfessional}. That is the
 * has-a relationship from Module 2: an appointment has a patient, has a
 * professional and has a time slot, and it does not repeat the details of any
 * of them. Changing a professional's room therefore changes it everywhere at
 * once, because there is only one copy.</p>
 *
 * <p><b>Why the time is a {@link TimeSlot} and not a String.</b> An appointment
 * has to be ordered against other appointments. A slot knows its own position
 * in the day and can compare itself, so the appointment only has to pass the
 * question on.</p>
 *
 * <p><b>Why the constructor is private.</b> The assessment asks that an
 * appointment can never exist in an incomplete state. An appointment without a
 * patient, without a professional or without a time is meaningless, so the only
 * way to obtain one is {@link #book(Patient, HealthProfessional, TimeSlot)},
 * which refuses to build the object when something is missing. The compiler
 * cannot make an exception, because exceptions are not part of Modules 1 to 3,
 * so the object is protected by a factory method that returns {@code null}
 * instead.</p>
 *
 * @author Ziliang Lu (25259146)
 * @version 1.1
 */
public class Appointment implements Comparable<Appointment> {

    /**
     * The patient who booked the appointment.
     */
    private Patient patient;

    /**
     * The health professional the appointment is with. It is typed as the
     * parent class, so any of the four types can be booked.
     */
    private HealthProfessional healthProfessional;

    /**
     * The slot the appointment occupies.
     */
    private TimeSlot timeSlot;

    /**
     * Creates an appointment.
     *
     * <p>It is private on purpose: see the class comment. Everything inside the
     * class can rely on the three fields being set because no other code can
     * call this constructor.</p>
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
     * Books an appointment, or refuses when any piece of information is
     * missing.
     *
     * <p>Returning {@code null} is the refusal. The caller checks for it and
     * reports a warning, which is the pattern the assessment asks for: a failed
     * operation gives the user a message rather than stopping the program.</p>
     *
     * @param patient the patient who booked; must not be null
     * @param healthProfessional the professional the patient will see; must not
     *        be null
     * @param timeSlot the slot the appointment occupies; must not be null
     * @return the new appointment, or {@code null} when something is missing
     */
    public static Appointment book(Patient patient, HealthProfessional healthProfessional,
                                   TimeSlot timeSlot) {
        if (patient == null || healthProfessional == null || timeSlot == null) {
            return null;
        }
        return new Appointment(patient, healthProfessional, timeSlot);
    }

    /**
     * Returns the patient who booked the appointment.
     *
     * @return the patient
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Returns the professional the appointment is with.
     *
     * <p>The return type is the parent class, which is what lets the manager
     * treat appointments with a general practitioner and appointments with a
     * nurse in the same way.</p>
     *
     * @return the health professional
     */
    public HealthProfessional getHealthProfessional() {
        return healthProfessional;
    }

    /**
     * Returns the slot the appointment occupies.
     *
     * @return the time slot
     */
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    /**
     * Orders two appointments from earliest to latest.
     *
     * <p>The appointment delegates to its slot instead of comparing times
     * itself, because the slot already owns that knowledge. Two appointments at
     * the same time are treated as equal for ordering purposes, which is why
     * the assessment notes that they may appear in either order.</p>
     *
     * @param other the appointment to compare against
     * @return a negative number when this appointment is earlier, zero when
     *         both are at the same time, and a positive number when this one is
     *         later
     */
    @Override
    public int compareTo(Appointment other) {
        return timeSlot.compareTo(other.timeSlot);
    }

    /**
     * Tells whether another object is the same booking slot.
     *
     * <p>Two appointments are considered equal when they are for the same
     * health professional at the same time, because that pair is exactly what
     * the clinic forbids: one professional can only be in one place at one time.
     * The patient is deliberately left out of the test, which is what lets the
     * manager detect a double booking with a single {@code contains(...)}
     * call.</p>
     *
     * @param other the object to compare with
     * @return {@code true} when both appointments occupy the same professional's
     *         slot at the same time
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
     * Returns a hash code consistent with {@link #equals(Object)}.
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
     * <p>The professional is not reformatted here. Its own {@code toString()}
     * is called and the result is indented, so a professional prints exactly
     * the same way whether it is shown on its own or inside an appointment.
     * That is the same composition idea as an account printing its customer.</p>
     *
     * @return a multi-line description of the appointment
     */
    @Override
    public String toString() {
        // Only the indentation is added: every line of the professional's own
        // description is shifted right so the block reads as one section of the
        // appointment rather than as loose text.
        String professionalDetails =
                healthProfessional.toString().replace("\n", "\n  ");

        return "Patient:      " + patient + "\n"
             + "Time:         " + timeSlot.getLabel() + "\n"
             + "Health professional:\n"
             + "  " + professionalDetails;
    }
}
