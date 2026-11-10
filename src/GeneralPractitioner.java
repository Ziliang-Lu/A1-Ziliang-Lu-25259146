import java.util.ArrayList;

/**
 * A general practitioner: the doctor a patient normally sees first.
 *
 * <p>A general practitioner adds the room they consult from, and the ability
 * to prescribe medication, to the details shared by every health
 * professional.</p>
 *
 * @author Ziliang Lu (25259146)
 * @version 1.0
 */
public class GeneralPractitioner extends HealthProfessional implements Prescribable {

    /** The room used when a caller supplies nothing usable. */
    private static final String DEFAULT_ROOM = "Unassigned room";

    private String clinicRoom;

    /**
     * Creates a general practitioner who works the default weekdays.
     *
     * @param id        the professional's identifier, digits only
     * @param name      the professional's name
     * @param clinicRoom the consulting room, for example "Room 4"
     */
    public GeneralPractitioner(String id, String name, String clinicRoom) {
        super(id, name);
        this.clinicRoom = cleanText(clinicRoom, DEFAULT_ROOM);
    }

    /**
     * Creates a general practitioner who works the given weekdays.
     *
     * @param id          the professional's identifier, digits only
     * @param name        the professional's name
     * @param workingDays the weekdays this professional works
     * @param clinicRoom  the consulting room, for example "Room 4"
     */
    public GeneralPractitioner(String id, String name, ArrayList<String> workingDays,
                               String clinicRoom) {
        super(id, name, workingDays);
        this.clinicRoom = cleanText(clinicRoom, DEFAULT_ROOM);
    }

    /**
     * Returns the room this general practitioner consults from.
     *
     * @return the consulting room
     */
    public String getClinicRoom() {
        return clinicRoom;
    }

    /**
     * {@inheritDoc}
     *
     * @return "General Practitioner"
     */
    @Override
    public String getRole() {
        return "General Practitioner";
    }

    /**
     * Returns the shared details plus the consulting room.
     *
     * @return a multi-line description of this general practitioner
     */
    @Override
    public String toString() {
        return super.toString() + "\n"
             + "Clinic room:  " + clinicRoom;
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
        return "Dr " + getName() + " (General Practitioner) prescribed "
             + medication + " for " + patientName + ".";
    }
}
