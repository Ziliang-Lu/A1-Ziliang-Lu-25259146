import java.util.ArrayList;

/**
 * Part 2: a nurse.
 *
 * Fields: ward.
 * Methods: getWard(), getRole(), getDepartment(), toString(),
 *          getIntroduction(), takeBloodSample().
 *
 * It extends HealthProfessional but does not implement Prescribable, because a
 * nurse cannot prescribe medication.
 */
public class Nurse extends HealthProfessional {

    private static final String DEFAULT_WARD = "Unassigned ward";   // fallback ward

    private String ward;   // the ward this nurse works on

    /**
     * Creates a nurse who works the default weekdays.
     *
     * @param identifier digits only, for example "4001"
     * @param name the nurse's name
     * @param gender Female, Male or Other
     * @param ward the ward, for example "Ward 3"
     */
    public Nurse(String identifier, String name, String gender, String ward) {
        super(identifier, name, gender);
        this.ward = cleanText(ward, DEFAULT_WARD);
    }

    /**
     * Creates a nurse who works the given weekdays.
     *
     * @param identifier digits only, for example "4001"
     * @param name the nurse's name
     * @param gender Female, Male or Other
     * @param workingDays the weekdays this nurse works
     * @param ward the ward, for example "Ward 3"
     */
    public Nurse(String identifier, String name, String gender,
                 ArrayList<String> workingDays, String ward) {
        super(identifier, name, gender, workingDays);
        this.ward = cleanText(ward, DEFAULT_WARD);
    }

    /**
     * Returns the ward.
     *
     * @return the ward, for example "Ward 3"
     */
    public String getWard() {
        return ward;
    }

    /**
     * Returns the role of this object.
     *
     * @return "Nurse"
     */
    @Override
    public String getRole() {
        return "Nurse";
    }

    /**
     * Returns the department of this type.
     *
     * @return "Nursing"
     */
    @Override
    public String getDepartment() {
        return "Nursing";
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
     * Returns a one-sentence introduction.
     *
     * @return a sentence describing this nurse
     */
    @Override
    public String getIntroduction() {
        return "Nurse " + getName() + " is based on " + ward
             + ", and is available on " + describeWorkingDays() + ".";
    }

    /**
     * Takes a blood sample from a patient.
     *
     * @param patientName the name of the patient
     * @return the sentence that describes the sample that was taken
     */
    public String takeBloodSample(String patientName) {
        return "Nurse " + getName() + " took a blood sample from " + patientName
             + " on " + ward + ".";
    }
}
