import java.util.ArrayList;

/**
 * A nurse, who works on a ward and can take blood samples.
 *
 * <p>A nurse adds the ward they are based on and the ability to take a blood
 * sample to the details shared by every health professional. Unlike the
 * doctors in this system a nurse cannot prescribe medication, so this class
 * deliberately does not implement {@link Prescribable}.</p>
 *
 * @author Ziliang Lu (25259146)
 * @version 1.0
 */
public class Nurse extends HealthProfessional {

    /** The ward used when a caller supplies nothing usable. */
    private static final String DEFAULT_WARD = "Unassigned ward";

    private String ward;

    /**
     * Creates a nurse who works the default weekdays.
     *
     * @param id   the professional's identifier, digits only
     * @param name the professional's name
     * @param ward the ward, for example "Ward 3"
     */
    public Nurse(String id, String name, String ward) {
        super(id, name);
        this.ward = cleanText(ward, DEFAULT_WARD);
    }

    /**
     * Creates a nurse who works the given weekdays.
     *
     * @param id          the professional's identifier, digits only
     * @param name        the professional's name
     * @param workingDays the weekdays this professional works
     * @param ward        the ward, for example "Ward 3"
     */
    public Nurse(String id, String name, ArrayList<String> workingDays, String ward) {
        super(id, name, workingDays);
        this.ward = cleanText(ward, DEFAULT_WARD);
    }

    /**
     * Returns the ward this nurse is based on.
     *
     * @return the ward
     */
    public String getWard() {
        return ward;
    }

    /**
     * {@inheritDoc}
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
     * @param patientName the name of the patient
     * @return a message describing the sample that was taken
     */
    public String takeBloodSample(String patientName) {
        return "Nurse " + getName() + " took a blood sample from "
             + patientName + " on " + ward + ".";
    }
}
