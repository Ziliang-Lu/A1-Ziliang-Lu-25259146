import java.util.ArrayList;

/**
 * Part 1: models a health professional.
 *
 * Fields: identifier, name, gender, workingDays.
 * Methods: getIdentifier(), getName(), getGender(), getWorkingDays(), worksOn(),
 *          getRole(), getDepartment(), equals(), hashCode(), toString(),
 *          getIntroduction().
 *
 * The class is abstract, so it cannot be created directly.
 */
public abstract class HealthProfessional {

    // the seven names a working day may use
    private static final String[] WEEKDAY_NAMES = {
        "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    };

    // the three genders the clinic records
    private static final String[] GENDER_NAMES = {"Female", "Male", "Other"};

    private static final String DEFAULT_IDENTIFIER = "0";   // fallback identifier
    private static final String DEFAULT_NAME = "Unnamed professional";   // fallback name
    private static final String DEFAULT_GENDER = "Other";   // fallback gender

    private String identifier;              // digits only, never empty
    private String name;                    // trimmed, never empty
    private String gender;                  // Female, Male or Other
    private ArrayList<String> workingDays;  // validated, no duplicates

    /**
     * Creates a professional who works the default weekdays.
     *
     * @param identifier digits only, for example "1001"
     * @param name the professional's name
     * @param gender Female, Male or Other
     */
    public HealthProfessional(String identifier, String name, String gender) {
        // passes the default working days to the full constructor
        this(identifier, name, gender, createDefaultWorkingDays());
    }

    /**
     * Creates a professional who works the given weekdays.
     *
     * @param identifier digits only, for example "1001"
     * @param name the professional's name
     * @param gender Female, Male or Other
     * @param workingDays the weekdays this professional works
     */
    public HealthProfessional(String identifier, String name, String gender,
                              ArrayList<String> workingDays) {
        this.identifier = cleanIdentifier(identifier);
        this.name = cleanText(name, DEFAULT_NAME);
        this.gender = cleanGender(gender);
        this.workingDays = cleanWorkingDays(workingDays);
    }

    /**
     * Builds the weekdays used when the caller supplies none.
     *
     * @return a new list with Monday, Wednesday and Friday
     */
    private static ArrayList<String> createDefaultWorkingDays() {
        ArrayList<String> days = new ArrayList<String>();
        days.add("Monday");
        days.add("Wednesday");
        days.add("Friday");
        return days;
    }

    /**
     * Keeps the digits of the identifier and nothing else.
     *
     * @param identifier the text supplied by the caller
     * @return a string of digits, never empty
     */
    private static String cleanIdentifier(String identifier) {
        String digitsOnly = "";

        if (identifier != null) {
            // compares each character with '0' and '9' instead of using a
            // regular expression, so only Module 1 operators are needed
            for (int index = 0; index < identifier.length(); index++) {
                char character = identifier.charAt(index);
                if (character >= '0' && character <= '9') {
                    digitsOnly = digitsOnly + character;
                }
            }
        }

        if (digitsOnly.length() == 0) {
            return DEFAULT_IDENTIFIER;
        }
        return digitsOnly;
    }

    /**
     * Trims a piece of text and falls back to a default when nothing is left.
     *
     * @param text the text supplied by the caller, may be null
     * @param fallback the value returned when the text is empty
     * @return the trimmed text, or the fallback
     */
    protected static String cleanText(String text, String fallback) {
        if (text == null) {
            return fallback;
        }
        String trimmed = text.trim();
        if (trimmed.length() == 0) {
            return fallback;
        }
        return trimmed;
    }

    /**
     * Keeps the gender only when it is Female, Male or Other.
     *
     * @param gender the text supplied by the caller
     * @return one of the three accepted values, or "Other"
     */
    private static String cleanGender(String gender) {
        String candidate = cleanText(gender, DEFAULT_GENDER);

        for (int index = 0; index < GENDER_NAMES.length; index++) {
            if (GENDER_NAMES[index].equals(candidate)) {
                return candidate;
            }
        }
        return DEFAULT_GENDER;
    }

    /**
     * Keeps the real weekday names, removes duplicates, and falls back to the
     * default days when none are left.
     *
     * @param days the weekdays supplied by the caller, may be null
     * @return a clean list of weekday names, never empty
     */
    private static ArrayList<String> cleanWorkingDays(ArrayList<String> days) {
        ArrayList<String> cleaned = new ArrayList<String>();

        if (days != null) {
            for (String day : days) {
                String candidate = cleanText(day, "");

                // unknown names are dropped and repeated days are ignored
                if (isWeekdayName(candidate) && !cleaned.contains(candidate)) {
                    cleaned.add(candidate);
                }
            }
        }

        if (cleaned.size() == 0) {
            return createDefaultWorkingDays();
        }
        return cleaned;
    }

    /**
     * Tells whether the text is one of the seven weekday names.
     *
     * @param text the text to check
     * @return true when the text names a weekday
     */
    private static boolean isWeekdayName(String text) {
        for (int index = 0; index < WEEKDAY_NAMES.length; index++) {
            if (WEEKDAY_NAMES[index].equals(text)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the professional's identifier.
     *
     * @return the identifier, digits only
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Returns the professional's name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the professional's gender.
     *
     * @return Female, Male or Other
     */
    public String getGender() {
        return gender;
    }

    /**
     * Returns a copy of the working days.
     *
     * @return a new list holding the working days
     */
    public ArrayList<String> getWorkingDays() {
        // a copy is returned so the caller cannot change the internal list
        return new ArrayList<String>(workingDays);
    }

    /**
     * Tells whether this professional works on the given day.
     *
     * @param day the weekday to check, for example "Monday"
     * @return true when the professional works that day
     */
    public boolean worksOn(String day) {
        return workingDays.contains(day);
    }

    /**
     * Returns the kind of professional this object is.
     *
     * @return the role, for example "General Practitioner"
     */
    public abstract String getRole();

    /**
     * Returns the department this professional works in.
     *
     * @return the department, for example "General Medicine"
     */
    public abstract String getDepartment();

    /**
     * Compares two professionals by identifier.
     *
     * @param other the object to compare with
     * @return true when both objects have the same identifier
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HealthProfessional)) {
            return false;
        }
        HealthProfessional that = (HealthProfessional) other;
        return identifier.equals(that.identifier);
    }

    /**
     * Returns a hash code that agrees with equals().
     *
     * @return a hash code built from the identifier
     */
    @Override
    public int hashCode() {
        return identifier.hashCode();
    }

    /**
     * Returns the details every professional has in common.
     *
     * @return a multi-line description of the shared details
     */
    @Override
    public String toString() {
        return "Identifier:   " + identifier + "\n"
             + "Name:         " + name + "\n"
             + "Gender:       " + gender + "\n"
             + "Role:         " + getRole() + "\n"
             + "Department:   " + getDepartment() + "\n"
             + "Working days: " + describeWorkingDays();
    }

    /**
     * Returns a short introduction that a patient could read.
     *
     * @return a sentence describing this professional
     */
    public String getIntroduction() {
        return getName() + " is a " + getRole() + " in " + getDepartment()
             + ", and is available on " + describeWorkingDays() + ".";
    }

    /**
     * Joins the working days into one readable line.
     *
     * @return the working days separated by commas
     */
    protected String describeWorkingDays() {
        String description = "";

        for (int index = 0; index < workingDays.size(); index++) {
            if (index > 0) {
                description = description + ", ";
            }
            description = description + workingDays.get(index);
        }
        return description;
    }
}
