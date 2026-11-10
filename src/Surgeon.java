import java.util.ArrayList;

/**
 * A surgeon, who operates in a theatre as well as seeing patients.
 *
 * <p>A surgeon adds the operating theatre they work in, the ability to
 * prescribe medication and the ability to perform an operation to the details
 * shared by every health professional.</p>
 *
 * @author Ziliang Lu (25259146)
 * @version 1.0
 */
public class Surgeon extends HealthProfessional implements Prescribable {

    /** The theatre used when a caller supplies nothing usable. */
    private static final String DEFAULT_THEATRE = "Unassigned theatre";

    private String theatre;

    /**
     * Creates a surgeon who works the default weekdays.
     *
     * @param id      the professional's identifier, digits only
     * @param name    the professional's name
     * @param theatre the operating theatre, for example "Theatre 2"
     */
    public Surgeon(String id, String name, String theatre) {
        super(id, name);
        this.theatre = cleanText(theatre, DEFAULT_THEATRE);
    }

    /**
     * Creates a surgeon who works the given weekdays.
     *
     * @param id          the professional's identifier, digits only
     * @param name        the professional's name
     * @param workingDays the weekdays this professional works
     * @param theatre     the operating theatre, for example "Theatre 2"
     */
    public Surgeon(String id, String name, ArrayList<String> workingDays,
                   String theatre) {
        super(id, name, workingDays);
        this.theatre = cleanText(theatre, DEFAULT_THEATRE);
    }

    /**
     * Returns the operating theatre this surgeon works in.
     *
     * @return the theatre
     */
    public String getTheatre() {
        return theatre;
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     *
     * @param medication  the name of the medication
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
     * @param procedure   the name of the procedure
     * @param patientName the name of the patient
     * @return a message describing the operation
     */
    public String performOperation(String procedure, String patientName) {
        return "Dr " + getName() + " performed " + procedure
             + " on " + patientName + " in " + theatre + ".";
    }
}
