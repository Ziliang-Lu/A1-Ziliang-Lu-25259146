import java.util.ArrayList;

/**
 * Part 2: a surgeon.
 *
 * Fields: theatre.
 * Methods: getTheatre(), getRole(), getDepartment(), toString(),
 *          getIntroduction(), prescribe(), performOperation().
 *
 * It extends HealthProfessional and implements Prescribable.
 */
public class Surgeon extends HealthProfessional implements Prescribable {

    private static final String DEFAULT_THEATRE = "Unassigned theatre";   // fallback theatre

    private String theatre;   // the operating theatre

    /**
     * Creates a surgeon who works the default weekdays.
     *
     * @param identifier digits only, for example "3001"
     * @param name the doctor's name
     * @param gender Female, Male or Other
     * @param theatre the operating theatre, for example "Theatre 2"
     */
    public Surgeon(String identifier, String name, String gender, String theatre) {
        super(identifier, name, gender);
        this.theatre = cleanText(theatre, DEFAULT_THEATRE);
    }

    /**
     * Creates a surgeon who works the given weekdays.
     *
     * @param identifier digits only, for example "3001"
     * @param name the doctor's name
     * @param gender Female, Male or Other
     * @param workingDays the weekdays this doctor works
     * @param theatre the operating theatre, for example "Theatre 2"
     */
    public Surgeon(String identifier, String name, String gender,
                   ArrayList<String> workingDays, String theatre) {
        super(identifier, name, gender, workingDays);
        this.theatre = cleanText(theatre, DEFAULT_THEATRE);
    }

    /**
     * Returns the operating theatre.
     *
     * @return the theatre, for example "Theatre 2"
     */
    public String getTheatre() {
        return theatre;
    }

    /**
     * Returns the role of this object.
     *
     * @return "Surgeon"
     */
    @Override
    public String getRole() {
        return "Surgeon";
    }

    /**
     * Returns the department of this type.
     *
     * @return "Surgery"
     */
    @Override
    public String getDepartment() {
        return "Surgery";
    }

    /**
     * Returns the shared details plus the operating theatre.
     *
     * @return a multi-line description of this doctor
     */
    @Override
    public String toString() {
        return super.toString() + "\n"
             + "Theatre:      " + theatre;
    }

    /**
     * Returns a one-sentence introduction.
     *
     * @return a sentence describing this surgeon
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
     * @param patientName the name of the patient
     * @return the sentence that describes the prescription
     */
    @Override
    public String prescribe(String medication, String patientName) {
        return "Dr " + getName() + " (Surgeon) prescribed "
             + medication + " for " + patientName + ".";
    }

    /**
     * Performs an operation on a patient.
     *
     * @param procedure the name of the procedure
     * @param patientName the name of the patient
     * @return the sentence that describes the operation
     */
    public String performOperation(String procedure, String patientName) {
        return "Dr " + getName() + " performed " + procedure
             + " on " + patientName + " in " + theatre + ".";
    }
}
