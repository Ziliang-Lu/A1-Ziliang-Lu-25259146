import java.util.ArrayList;

/**
 * A surgeon, who operates in a theatre as well as seeing patients.
 *
 * <p>A surgeon adds the operating theatre they work in, the ability to
 * prescribe medication, and the ability to perform an operation to the details
 * that every health professional already has.</p>
 *
 * <p><b>Why the operation is a method rather than a field.</b> Recording the
 * operations in a list would need the history to be kept, sorted and reported,
 * and the assessment does not ask for any of that. A method that answers the
 * question "what did this surgeon do" is enough to show behaviour that no other
 * type has, and it can be called from the demonstration without storing
 * anything.</p>
 *
 * @author Ziliang Lu (25259146)
 * @version 1.3
 */
public class Surgeon extends HealthProfessional implements Prescribable {

    /** The theatre used when a caller supplies nothing usable. */
    private static final String DEFAULT_THEATRE = "Unassigned theatre";

    /**
     * The operating theatre this surgeon works in.
     */
    private String theatre;

    /**
     * Creates a surgeon who works the default weekdays.
     *
     * @param identifier the professional's identifier; only the digits of this
     *        text are kept
     * @param name the professional's name; blank text is replaced by a
     *        placeholder so that the object is never nameless
     * @param theatre the operating theatre, for example "Theatre 2"; blank text
     *        is replaced by a placeholder
     */
    public Surgeon(String identifier, String name, String theatre) {
        // The parent cleans and stores the shared values; the theatre is this
        // class's own data and is stored afterwards.
        super(identifier, name);
        this.theatre = cleanText(theatre, DEFAULT_THEATRE);
    }

    /**
     * Creates a surgeon who works the given weekdays.
     *
     * @param identifier the professional's identifier; only the digits of this
     *        text are kept
     * @param name the professional's name; blank text is replaced by a
     *        placeholder so that the object is never nameless
     * @param workingDays the weekdays this professional works; unknown names
     *        and duplicates are dropped, and an empty result falls back to the
     *        default schedule
     * @param theatre the operating theatre, for example "Theatre 2"; blank text
     *        is replaced by a placeholder
     */
    public Surgeon(String identifier, String name, ArrayList<String> workingDays,
                   String theatre) {
        super(identifier, name, workingDays);
        this.theatre = cleanText(theatre, DEFAULT_THEATRE);
    }

    /**
     * Returns the operating theatre this surgeon works in.
     *
     * @return the theatre, never empty
     */
    public String getTheatre() {
        return theatre;
    }

    /**
     * Returns the kind of health professional this object is.
     *
     * @return "Surgeon"
     */
    @Override
    public String getRole() {
        return "Surgeon";
    }

    /**
     * Returns the shared details plus the operating theatre.
     *
     * @return a multi-line description of this surgeon
     */
    @Override
    public String toString() {
        return super.toString() + "\n"
             + "Theatre:      " + theatre;
    }

    /**
     * Returns a one-paragraph introduction for a patient to read.
     *
     * @return a sentence describing this surgeon and their theatre
     */
    @Override
    public String getIntroduction() {
        return "Dr " + getName() + " is a surgeon operating in " + theatre
             + ", and is available on " + describeWorkingDays() + ".";
    }

    /**
     * Prescribes a medication to a patient.
     *
     * @param medication the name of the medication
     * @param patientName the name of the patient receiving it
     * @return a message describing the prescription
     */
    @Override
    public String prescribe(String medication, String patientName) {
        return "Dr " + getName() + " (Surgeon) prescribed "
             + medication + " for " + patientName + ".";
    }

    /**
     * Performs an operation on a patient.
     *
     * <p>This is the behaviour no other type has, which is why the four types
     * are not just four copies of the same class: each one answers a different
     * question that the clinic needs to ask.</p>
     *
     * @param procedure the name of the procedure
     * @param patientName the name of the patient
     * @return a message describing the operation, including the theatre
     */
    public String performOperation(String procedure, String patientName) {
        return "Dr " + getName() + " performed " + procedure
             + " on " + patientName + " in " + theatre + ".";
    }
}
