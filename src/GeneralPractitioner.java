import java.util.ArrayList;

/**
 * A general practitioner: the doctor a patient normally sees first.
 *
 * <p>A general practitioner adds a consulting room and the ability to
 * prescribe medication to the details that every health professional already
 * has. Nothing about the identifier, the name or the working days is repeated
 * here, because all of that is inherited from {@link HealthProfessional}.</p>
 *
 * <p><b>Why it implements {@link Prescribable}.</b> Prescribing is something a
 * general practitioner can do, not something they are, so it is a capability
 * rather than a kind of professional. Keeping it in an interface means the
 * nurses cannot prescribe, without the class hierarchy having to be rearranged
 * to express that.</p>
 *
 * @author Ziliang Lu (25259146)
 * @version 1.3
 */
public class GeneralPractitioner extends HealthProfessional implements Prescribable {

    /** The room used when a caller supplies nothing usable. */
    private static final String DEFAULT_ROOM = "Unassigned room";

    /**
     * The consulting room this general practitioner works from.
     */
    private String clinicRoom;

    /**
     * Creates a general practitioner who works the default weekdays.
     *
     * @param identifier the professional's identifier; only the digits of this
     *        text are kept
     * @param name the professional's name; blank text is replaced by a
     *        placeholder so that the object is never nameless
     * @param clinicRoom the consulting room, for example "Room 4"; blank text
     *        is replaced by a placeholder
     */
    public GeneralPractitioner(String identifier, String name, String clinicRoom) {
        // super(...) hands the shared values to the parent's constructor, which
        // cleans and stores them. Without this call the identifier and the name
        // would have nowhere to live, because those fields are private there.
        super(identifier, name);
        this.clinicRoom = cleanText(clinicRoom, DEFAULT_ROOM);
    }

    /**
     * Creates a general practitioner who works the given weekdays.
     *
     * @param identifier the professional's identifier; only the digits of this
     *        text are kept
     * @param name the professional's name; blank text is replaced by a
     *        placeholder so that the object is never nameless
     * @param workingDays the weekdays this professional works; unknown names
     *        and duplicates are dropped, and an empty result falls back to the
     *        default schedule
     * @param clinicRoom the consulting room, for example "Room 4"; blank text
     *        is replaced by a placeholder
     */
    public GeneralPractitioner(String identifier, String name, ArrayList<String> workingDays,
                               String clinicRoom) {
        super(identifier, name, workingDays);
        this.clinicRoom = cleanText(clinicRoom, DEFAULT_ROOM);
    }

    /**
     * Returns the room this general practitioner consults from.
     *
     * @return the consulting room, never empty
     */
    public String getClinicRoom() {
        return clinicRoom;
    }

    /**
     * Returns the kind of health professional this object is.
     *
     * <p>This is the method the parent class left abstract. It is what makes a
     * list of health professionals print the correct type on every line
     * without the printing code asking what each object is.</p>
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
     * <p>The shared part is not rebuilt here: {@code super.toString()} already
     * knows how to print it, and calling it keeps every type in the same
     * layout. Only the room is appended, because only the room is new.</p>
     *
     * @return a multi-line description of this general practitioner
     */
    @Override
    public String toString() {
        return super.toString() + "\n"
             + "Clinic room:  " + clinicRoom;
    }

    /**
     * Returns a one-paragraph introduction for a patient to read.
     *
     * <p>The consulting room belongs in the sentence because "where do I go"
     * is the first question a patient asks, and the working days close it so
     * the sentence also says when the doctor can be seen.</p>
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
     * <p>The message is returned rather than printed, so the caller decides
     * where the sentence appears and the method stays usable from anywhere in
     * the program.</p>
     *
     * @param medication the name of the medication
     * @param patientName the name of the patient receiving it
     * @return a message describing the prescription
     */
    @Override
    public String prescribe(String medication, String patientName) {
        return "Dr " + getName() + " (General Practitioner) prescribed "
             + medication + " for " + patientName + ".";
    }
}
