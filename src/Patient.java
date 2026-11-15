/**
 * Part 4: a patient of the clinic.
 *
 * Fields: name, phoneNumber.
 * Methods: getName(), getPhoneNumber(), toString().
 *
 * The phone number is stored as text because a mobile number that starts with
 * a zero would lose that zero if it were stored as a number, and the number is
 * only compared and printed, never used in arithmetic.
 */
public class Patient {

    private static final String DEFAULT_NAME = "Unknown patient";   // fallback name
    private static final String DEFAULT_PHONE_NUMBER = "0";         // fallback number

    private String name;         // trimmed, never empty
    private String phoneNumber;  // digits only, spaces removed

    /**
     * Creates a patient.
     *
     * @param name the patient's name, may be blank
     * @param phoneNumber the patient's mobile number, may contain spaces
     */
    public Patient(String name, String phoneNumber) {
        this.name = cleanName(name);
        this.phoneNumber = cleanPhoneNumber(phoneNumber);
    }

    /**
     * Trims the name and uses a placeholder when nothing is left.
     *
     * @param name the name supplied by the caller, may be null
     * @return the trimmed name, or "Unknown patient"
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
     * Keeps only the digits of the phone number.
     *
     * @param phoneNumber the number supplied by the caller, may be null
     * @return a string of digits, never empty
     */
    private static String cleanPhoneNumber(String phoneNumber) {
        String digitsOnly = "";

        if (phoneNumber != null) {
            // removes the spaces people type, so "0412 345 678" and
            // "0412345678" become the same text and can be compared
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
     * @return the name, never empty
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the patient's mobile number.
     *
     * @return the number, digits only
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Returns the name and the number on one line.
     *
     * @return the patient as text, for example "Michael Chen (0412345678)"
     */
    @Override
    public String toString() {
        return name + " (" + phoneNumber + ")";
    }
}
