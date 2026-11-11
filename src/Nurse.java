import java.util.ArrayList;

/**
 * A nurse, who works on a ward and can take blood samples.
 *
 * <p>A nurse adds the ward they are based on and the ability to take a blood
 * sample to the details that every health professional already has.</p>
 *
 * <p><b>Why this class does not implement {@link Prescribable}.</b> The
 * interface describes a capability that only the doctors have. Leaving it off
 * this class is not an omission: it is the design statement that a nurse cannot
 * prescribe. Because the capability lives in an interface rather than in the
 * class hierarchy, the compiler enforces that rule and the demonstration can
 * check it with {@code instanceof}.</p>
 *
 * @author Ziliang Lu (25259146)
 * @version 1.2
 */
public class Nurse extends HealthProfessional {

    /** The ward used when a caller supplies nothing usable. */
    private static final String DEFAULT_WARD = "Unassigned ward";

    /**
     * The ward this nurse is based on.
     */
    private String ward;

    /**
     * Creates a nurse who works the default weekdays.
     *
     * @param identifier the professional's identifier; only the digits of this
     *        text are kept
     * @param name the professional's name; blank text is replaced by a
     *        placeholder so that the object is never nameless
     * @param ward the ward, for example "Ward 3"; blank text is replaced by a
     *        placeholder
     */
    public Nurse(String identifier, String name, String ward) {
        // The parent's constructor deals with the identifier, the name and the
        // working days; the ward is stored here because only a nurse has one.
        super(identifier, name);
        this.ward = cleanText(ward, DEFAULT_WARD);
    }

    /**
     * Creates a nurse who works the given weekdays.
     *
     * @param identifier the professional's identifier; only the digits of this
     *        text are kept
     * @param name the professional's name; blank text is replaced by a
     *        placeholder so that the object is never nameless
     * @param workingDays the weekdays this professional works; unknown names
     *        and duplicates are dropped, and an empty result falls back to the
     *        default schedule
     * @param ward the ward, for example "Ward 3"; blank text is replaced by a
     *        placeholder
     */
    public Nurse(String identifier, String name, ArrayList<String> workingDays, String ward) {
        super(identifier, name, workingDays);
        this.ward = cleanText(ward, DEFAULT_WARD);
    }

    /**
     * Returns the ward this nurse is based on.
     *
     * @return the ward, never empty
     */
    public String getWard() {
        return ward;
    }

    /**
     * Returns the kind of health professional this object is.
     *
     * @return "Nurse"
     */
    @Override
    public String getRole() {
        return "Nurse";
    }

    /**
     * Returns the shared details plus the ward.
     *
     * @return a multi-line description of this nurse
     */
    @Override
    public String toString() {
        return super.toString() + "\n"
             + "Ward:         " + ward;
    }

    /**
     * Takes a blood sample from a patient.
     *
     * <p>This is the behaviour that belongs to a nurse alone, in the same way
     * that prescribing belongs to the doctors. Having each type answer a
     * different question is what stops the hierarchy from being four copies of
     * the same class.</p>
     *
     * @param patientName the name of the patient
     * @return a message describing the sample that was taken, including the ward
     */
    public String takeBloodSample(String patientName) {
        return "Nurse " + getName() + " took a blood sample from "
             + patientName + " on " + ward + ".";
    }
}
