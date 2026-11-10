import java.util.ArrayList;

/**
 * A specialist doctor, for example a paediatrician, radiologist or
 * cardiologist.
 *
 * <p>A specialist adds the area of medicine they specialise in, and the
 * ability to prescribe medication, to the details shared by every health
 * professional.</p>
 *
 * @author Ziliang Lu (25259146)
 * @version 1.0
 */
public class Specialist extends HealthProfessional implements Prescribable {

    /** The specialty used when a caller supplies nothing usable. */
    private static final String DEFAULT_SPECIALTY = "General medicine";

    private String specialty;

    /**
     * Creates a specialist who works the default weekdays.
     *
     * @param id        the professional's identifier, digits only
     * @param name      the professional's name
     * @param specialty the area of medicine, for example "Paediatrician"
     */
    public Specialist(String id, String name, String specialty) {
        super(id, name);
        this.specialty = cleanText(specialty, DEFAULT_SPECIALTY);
    }

    /**
     * Creates a specialist who works the given weekdays.
     *
     * @param id          the professional's identifier, digits only
     * @param name        the professional's name
     * @param workingDays the weekdays this professional works
     * @param specialty   the area of medicine, for example "Paediatrician"
     */
    public Specialist(String id, String name, ArrayList<String> workingDays,
                      String specialty) {
        super(id, name, workingDays);
        this.specialty = cleanText(specialty, DEFAULT_SPECIALTY);
    }

    /**
     * Returns the area of medicine this specialist works in.
     *
     * @return the specialty
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * {@inheritDoc}
     *
     * @return "Specialist"
     */
    @Override
    public String getRole() {
        return "Specialist";
    }

    /**
     * Returns the shared details plus the specialty.
     *
     * @return a multi-line description of this specialist
     */
    @Override
    public String toString() {
        return super.toString() + "\n"
             + "Specialty:    " + specialty;
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
        return "Dr " + getName() + " (Specialist - " + specialty + ") prescribed "
             + medication + " for " + patientName + ".";
    }
}
