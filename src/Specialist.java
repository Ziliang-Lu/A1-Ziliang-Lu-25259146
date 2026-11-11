import java.util.ArrayList;

/**
 * A specialist doctor, for example a paediatrician, radiologist or
 * cardiologist.
 *
 * <p>A specialist adds the area of medicine they specialise in, and the ability
 * to prescribe medication, to the details that every health professional
 * already has. The shared data is inherited, so a specialist that is renamed or
 * given a different schedule is changed in one place only.</p>
 *
 * <p><b>Why the specialty is a field rather than its own subclass.</b> A
 * paediatrician and a cardiologist do the same job in this system: they see
 * patients in their area, and they prescribe. Making each area a separate class
 * would copy the same code once per area and would not add any behaviour, so
 * the area is recorded as data instead.</p>
 *
 * @author Ziliang Lu (25259146)
 * @version 1.2
 */
public class Specialist extends HealthProfessional implements Prescribable {

    /** The specialty used when a caller supplies nothing usable. */
    private static final String DEFAULT_SPECIALTY = "General medicine";

    /**
     * The area of medicine this specialist works in.
     */
    private String specialty;

    /**
     * Creates a specialist who works the default weekdays.
     *
     * @param identifier the professional's identifier; only the digits of this
     *        text are kept
     * @param name the professional's name; blank text is replaced by a
     *        placeholder so that the object is never nameless
     * @param specialty the area of medicine, for example "Paediatrician";
     *        blank text is replaced by a placeholder
     */
    public Specialist(String identifier, String name, String specialty) {
        // Only the values the parent understands are passed up. The specialty
        // belongs to this class alone, so it is stored here after the parent
        // has finished with its own fields.
        super(identifier, name);
        this.specialty = cleanText(specialty, DEFAULT_SPECIALTY);
    }

    /**
     * Creates a specialist who works the given weekdays.
     *
     * @param identifier the professional's identifier; only the digits of this
     *        text are kept
     * @param name the professional's name; blank text is replaced by a
     *        placeholder so that the object is never nameless
     * @param workingDays the weekdays this professional works; unknown names
     *        and duplicates are dropped, and an empty result falls back to the
     *        default schedule
     * @param specialty the area of medicine, for example "Paediatrician";
     *        blank text is replaced by a placeholder
     */
    public Specialist(String identifier, String name, ArrayList<String> workingDays,
                      String specialty) {
        super(identifier, name, workingDays);
        this.specialty = cleanText(specialty, DEFAULT_SPECIALTY);
    }

    /**
     * Returns the area of medicine this specialist works in.
     *
     * @return the specialty, never empty
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * Returns the kind of health professional this object is.
     *
     * <p>The role is kept as "Specialist" rather than being merged with the
     * specialty, so the output always answers the same two questions in the
     * same order: what kind of professional is this, and what area do they
     * work in.</p>
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
     * <p>{@code super.toString()} prints the identifier, the name, the role and
     * the working days, so this method only adds the one line that is unique to
     * a specialist.</p>
     *
     * @return a multi-line description of this specialist
     */
    @Override
    public String toString() {
        return super.toString() + "\n"
             + "Specialty:    " + specialty;
    }

    /**
     * Prescribes a medication to a patient.
     *
     * <p>The specialty is repeated inside the message because the patient
     * needs to know which area the doctor works in, while the printed record of
     * the professional already shows it on its own line.</p>
     *
     * @param medication the name of the medication
     * @param patientName the name of the patient receiving it
     * @return a message describing the prescription
     */
    @Override
    public String prescribe(String medication, String patientName) {
        return "Dr " + getName() + " (Specialist - " + specialty + ") prescribed "
             + medication + " for " + patientName + ".";
    }
}
