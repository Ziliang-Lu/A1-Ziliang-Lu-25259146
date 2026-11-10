import java.util.ArrayList;

/**
 * Models a health professional who works at the clinic.
 *
 * <p>Every kind of health professional shares the same basic information: an
 * identifier made up of digits only, a name, and the fixed set of weekdays on
 * which the professional works. This shared data, and the behaviour that goes
 * with it, is defined once here so that the concrete types only have to add
 * what makes them different.</p>
 *
 * <p>The class is {@code abstract} on purpose. The clinic does not employ a
 * health professional who has no specialty, so it makes no sense to create a
 * plain {@code new HealthProfessional(...)}. Every concrete subclass is forced
 * to answer {@link #getRole()} and to present its own details.</p>
 *
 * <p>All fields are private and only getters are provided, so external code
 * can read a professional's data but can never put the object into an invalid
 * state. The constructor is the single place where the data is set, and it is
 * responsible for checking what it is given.</p>
 *
 * @author Ziliang Lu (25259146)
 * @version 1.0
 */
public abstract class HealthProfessional {

    /** The only weekday names that a working day is allowed to be. */
    private static final String[] WEEKDAY_NAMES = {
        "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    };

    /** The identifier used when a caller supplies nothing usable. */
    private static final String DEFAULT_ID = "0";

    /** The name used when a caller supplies nothing usable. */
    private static final String DEFAULT_NAME = "Unnamed professional";

    private String id;
    private String name;
    private ArrayList<String> workingDays;

    /**
     * Creates a health professional who works the default weekdays
     * (Monday, Wednesday and Friday).
     *
     * @param id   the professional's identifier, digits only
     * @param name the professional's name
     */
    public HealthProfessional(String id, String name) {
        // Constructor chaining: the real work is done once, in the full
        // constructor below, instead of being copied into both constructors.
        this(id, name, defaultWorkingDays());
    }

    /**
     * Creates a health professional who works the given weekdays.
     *
     * @param id           the professional's identifier, digits only
     * @param name         the professional's name
     * @param workingDays  the weekdays this professional works
     */
    public HealthProfessional(String id, String name, ArrayList<String> workingDays) {
        this.id = cleanId(id);
        this.name = cleanText(name, DEFAULT_NAME);
        this.workingDays = cleanWorkingDays(workingDays);
    }

    /**
     * Returns the weekdays a professional works when the caller does not
     * supply any.
     *
     * @return a new list holding Monday, Wednesday and Friday
     */
    private static ArrayList<String> defaultWorkingDays() {
        ArrayList<String> days = new ArrayList<>();
        days.add("Monday");
        days.add("Wednesday");
        days.add("Friday");
        return days;
    }

    /**
     * Keeps the digits of the supplied identifier and nothing else, so that
     * the "id is a number" rule can never be broken.
     *
     * @param id the identifier supplied by the caller
     * @return an identifier containing digits only
     */
    private static String cleanId(String id) {
        String digitsOnly = "";
        if (id != null) {
            for (int i = 0; i < id.length(); i++) {
                char character = id.charAt(i);
                if (character >= '0' && character <= '9') {
                    digitsOnly = digitsOnly + character;
                }
            }
        }
        if (digitsOnly.length() == 0) {
            return DEFAULT_ID;
        }
        return digitsOnly;
    }

    /**
     * Trims a piece of text and falls back to a default when nothing is left.
     *
     * @param text     the text supplied by the caller
     * @param fallback the value to use when the text is empty
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
     * Keeps only the supplied days that are real weekday names, removes
     * duplicates, and falls back to the default days when none are left.
     *
     * @param days the weekdays supplied by the caller
     * @return a clean list of weekday names
     */
    private static ArrayList<String> cleanWorkingDays(ArrayList<String> days) {
        ArrayList<String> cleaned = new ArrayList<>();
        if (days != null) {
            for (String day : days) {
                String candidate = cleanText(day, "");
                if (isWeekdayName(candidate) && !cleaned.contains(candidate)) {
                    cleaned.add(candidate);
                }
            }
        }
        if (cleaned.size() == 0) {
            return defaultWorkingDays();
        }
        return cleaned;
    }

    /**
     * Tells whether the text is one of the seven weekday names.
     *
     * @param text the text to check
     * @return {@code true} when the text names a weekday
     */
    private static boolean isWeekdayName(String text) {
        for (int i = 0; i < WEEKDAY_NAMES.length; i++) {
            if (WEEKDAY_NAMES[i].equals(text)) {
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
    public String getId() {
        return id;
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
     * Returns the weekdays this professional works.
     *
     * <p>A copy is returned so that code outside the class cannot change the
     * professional's schedule by editing the list it was given.</p>
     *
     * @return a copy of the working days
     */
    public ArrayList<String> getWorkingDays() {
        return new ArrayList<String>(workingDays);
    }

    /**
     * Tells whether this professional works on the given day.
     *
     * @param day the weekday to check, for example "Monday"
     * @return {@code true} when the professional works that day
     */
    public boolean worksOn(String day) {
        return workingDays.contains(day);
    }

    /**
     * Returns the kind of health professional this object represents.
     *
     * <p>Every concrete subclass must answer this, which is what allows the
     * shared details to be printed without the parent class knowing which
     * subtype it is dealing with.</p>
     *
     * @return the role, for example "General Practitioner"
     */
    public abstract String getRole();

    /**
     * Returns the details every health professional has in common.
     *
     * <p>Subclasses call this from their own {@code toString()} so that the
     * shared part is written once, and they only append what is unique to
     * them.</p>
     *
     * @return a multi-line description of the shared details
     */
    @Override
    public String toString() {
        return "ID:           " + id + "\n"
             + "Name:         " + name + "\n"
             + "Role:         " + getRole() + "\n"
             + "Working days: " + describeWorkingDays();
    }

    /**
     * Joins the working days into one readable line.
     *
     * @return the working days separated by commas
     */
    private String describeWorkingDays() {
        String description = "";
        for (int i = 0; i < workingDays.size(); i++) {
            if (i > 0) {
                description = description + ", ";
            }
            description = description + workingDays.get(i);
        }
        return description;
    }
}
