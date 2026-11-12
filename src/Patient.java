/**
 * A patient of the clinic.
 *
 * <p><b>Why this is its own class.</b> A name and a contact number are not
 * details of an appointment; they describe a person, and the same person may
 * book more than once. Keeping them together in one type means the appointment
 * only has to hold a reference to a patient, and every place that prints a
 * patient prints it the same way. It also gives the appointment system one
 * obvious answer to the question "how do I find all of Mary's bookings". This
 * is the has-a relationship from Module 2: an appointment has a patient
 * rather than being a patient.</p>
 *
 * <p><b>Why the phone number is text and not a number.</b> A mobile number
 * that begins with a zero would lose that zero the moment it was stored as a
 * number, and the number is never used for arithmetic - it is only compared and
 * printed. Storing it as {@code String} keeps it exactly as the patient said
 * it.</p>
 *
 * @author Ziliang Lu (25259146)
 * @version 1.0
 */
public class Patient {

    /** The name used when a caller supplies nothing usable. */
    private static final String DEFAULT_NAME = "Unknown patient";

    /** The number used when a caller supplies nothing usable. */
    private static final String DEFAULT_PHONE_NUMBER = "0";

    /**
     * The patient's name. It is trimmed and never empty.
     */
    private String name;

    /**
     * The patient's mobile number, digits only.
     *
     * <p>The spaces people type ("0412 345 678") are removed so that two
     * records of the same patient can be compared without worrying about where
     * the spaces were.</p>
     */
    private String phoneNumber;

    /**
     * Creates a patient.
     *
     * @param name the patient's name; blank text is replaced by a placeholder
     * @param phoneNumber the patient's mobile number, which may contain spaces;
     *        only the digits are kept, and an empty result falls back to a
     *        placeholder
     */
    public Patient(String name, String phoneNumber) {
        this.name = cleanName(name);
        this.phoneNumber = cleanPhoneNumber(phoneNumber);
    }

    /**
     * Trims the supplied name and falls back to a placeholder when nothing is
     * left.
     *
     * <p>The same rule is written here instead of being borrowed from
     * {@link HealthProfessional}: a patient is not a health professional, so
     * neither type should have to depend on the other just to trim a string.</p>
     *
     * @param name the name supplied by the caller, which may be null
     * @return the trimmed name, or the placeholder when there is nothing left
     */
    private static String cleanName(String name) {
        if (name == null) {
            return DEFAULT_NAME;
        }
        String trimmed = name.trim();
        if (trimmed.length() == 0) {
            return DEFAULT_NAME;
        }
        return trimmed;
    }

    /**
     * Keeps the digits of the supplied number and nothing else.
     *
     * <p>Filtering the characters rather than trusting the caller is what makes
     * the number safe to compare: "0412 345 678" and "0412345678" become the
     * same text, so a search by phone number finds both.</p>
     *
     * @param phoneNumber the number supplied by the caller, which may be null
     *        or contain spaces and brackets
     * @return a number containing digits only; never null and never empty
     */
    private static String cleanPhoneNumber(String phoneNumber) {
        String digitsOnly = "";
        if (phoneNumber != null) {

            // The characters are checked with a simple comparison instead of a
            // regular expression, so the rule uses only the operators that were
            // covered in Module 1.
            for (int index = 0; index < phoneNumber.length(); index++) {
                char character = phoneNumber.charAt(index);
                if (character >= '0' && character <= '9') {
                    digitsOnly = digitsOnly + character;
                }
            }
        }

        if (digitsOnly.length() == 0) {
            return DEFAULT_PHONE_NUMBER;
        }
        return digitsOnly;
    }

    /**
     * Returns the patient's name.
     *
     * <p>There is no setter: a patient's record is created once and then
     * treated as read-only, which keeps every appointment that refers to this
     * patient consistent with the details it was booked with.</p>
     *
     * @return the name, never empty
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the patient's mobile number.
     *
     * @return the number, digits only, never empty
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Returns the patient's name and number on one line.
     *
     * <p>An appointment calls this instead of rebuilding the two values, which
     * is the same idea as an account calling the customer's own
     * {@code toString()}: the owner of the data decides how it reads.</p>
     *
     * @return a one-line description of the patient
     */
    @Override
    public String toString() {
        return name + " (" + phoneNumber + ")";
    }
}
