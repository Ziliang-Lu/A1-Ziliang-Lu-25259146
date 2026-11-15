import java.util.ArrayList;

/**
 * Part 2: a specialist doctor.
 *
 * Fields: specialty.
 * Methods: getSpecialty(), getRole(), getDepartment(), toString(),
 *          getIntroduction(), prescribe().
 *
 * It extends HealthProfessional and implements Prescribable.
 */
public class Specialist extends HealthProfessional implements Prescribable {

    private static final String DEFAULT_SPECIALTY = "General medicine";   // fallback area

    private String specialty;   // the area of medicine

    /**
     * Creates a specialist who works the default weekdays.
     *
     * @param identifier digits only, for example "2001"
     * @param name the doctor's name
     * @param gender Female, Male or Other
     * @param specialty the area of medicine, for example "Paediatrician"
     */
    public Specialist(String identifier, String name, String gender, String specialty) {
        super(identifier, name, gender);
        this.specialty = cleanText(specialty, DEFAULT_SPECIALTY);
    }

    /**
     * Creates a specialist who works the given weekdays.
     *
     * @param identifier digits only, for example "2001"
     * @param name the doctor's name
     * @param gender Female, Male or Other
     * @param workingDays the weekdays this doctor works
     * @param specialty the area of medicine, for example "Paediatrician"
     */
    public Specialist(String identifier, String name, String gender,
                      ArrayList<String> workingDays, String specialty) {
        super(identifier, name, gender, workingDays);
        this.specialty = cleanText(specialty, DEFAULT_SPECIALTY);
    }

    /**
     * Returns the area of medicine.
     *
     * @return the specialty, for example "Paediatrician"
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * Returns the role of this object.
     *
     * @return "Specialist"
     */
    @Override
    public String getRole() {
        return "Specialist";
    }

    /**
     * Returns the department of this type.
     *
     * @return "Specialist Medicine"
     */
    @Override
    public String getDepartment() {
        return "Specialist Medicine";
    }

    /**
     * Returns the shared details plus the area of medicine.
     *
     * @return a multi-line description of this doctor
     */
    @Override
    public String toString() {
        return super.toString() + "\n"
             + "Specialty:    " + specialty;
    }

    /**
     * Returns a one-sentence introduction.
     *
     * @return a sentence describing this specialist
     */
    @Override
    public String getIntroduction() {
        return "Dr " + getName() + " works as a " + specialty
             + ", and is available on " + describeWorkingDays() + ".";
    }

    /**
     * Prescribes a medication to a patient.
     *
     * @param medication the name of the medication
     * @param patientName the name of the patient
     * @return the sentence that describes the prescription
     */
    @Override
    public String prescribe(String medication, String patientName) {
        return "Dr " + getName() + " (Specialist - " + specialty + ") prescribed "
             + medication + " for " + patientName + ".";
    }
}
