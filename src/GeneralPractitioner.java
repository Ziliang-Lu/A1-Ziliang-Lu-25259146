import java.util.ArrayList;

/**
 * Part 2: a general practitioner.
 *
 * Fields: clinicRoom.
 * Methods: getClinicRoom(), getRole(), getDepartment(), toString(),
 *          getIntroduction(), prescribe().
 *
 * It extends HealthProfessional and implements Prescribable.
 */
public class GeneralPractitioner extends HealthProfessional implements Prescribable {

    private static final String DEFAULT_ROOM = "Unassigned room";   // fallback room

    private String clinicRoom;   // the consulting room

    /**
     * Creates a general practitioner who works the default weekdays.
     *
     * @param identifier digits only, for example "1001"
     * @param name the doctor's name
     * @param gender Female, Male or Other
     * @param clinicRoom the room where patients are seen
     */
    public GeneralPractitioner(String identifier, String name, String gender,
                               String clinicRoom) {
        // the parent constructor stores the shared values
        super(identifier, name, gender);
        this.clinicRoom = cleanText(clinicRoom, DEFAULT_ROOM);
    }

    /**
     * Creates a general practitioner who works the given weekdays.
     *
     * @param identifier digits only, for example "1001"
     * @param name the doctor's name
     * @param gender Female, Male or Other
     * @param workingDays the weekdays this doctor works
     * @param clinicRoom the room where patients are seen
     */
    public GeneralPractitioner(String identifier, String name, String gender,
                               ArrayList<String> workingDays, String clinicRoom) {
        super(identifier, name, gender, workingDays);
        this.clinicRoom = cleanText(clinicRoom, DEFAULT_ROOM);
    }

    /**
     * Returns the consulting room.
     *
     * @return the room, for example "Room 4"
     */
    public String getClinicRoom() {
        return clinicRoom;
    }

    /**
     * Returns the role of this object.
     *
     * @return "General Practitioner"
     */
    @Override
    public String getRole() {
        return "General Practitioner";
    }

    /**
     * Returns the department of this type.
     *
     * @return "General Medicine"
     */
    @Override
    public String getDepartment() {
        return "General Medicine";
    }

    /**
     * Returns the shared details plus the consulting room.
     *
     * @return a multi-line description of this doctor
     */
    @Override
    public String toString() {
        return super.toString() + "\n"
             + "Clinic room:  " + clinicRoom;
    }

    /**
     * Returns a one-sentence introduction.
     *
     * @return a sentence describing this general practitioner
     */
    @Override
    public String getIntroduction() {
        return "Dr " + getName() + " is a general practitioner consulting from "
             + clinicRoom + ", and is available on " + describeWorkingDays() + ".";
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
        return "Dr " + getName() + " (General Practitioner) prescribed "
             + medication + " for " + patientName + ".";
    }
}
