import java.util.ArrayList;

/**
 * Models a health professional who works at the clinic.
 *
 * <p>This class holds the three pieces of information that every health
 * professional has, whatever their specialty: an identifier made up of digits
 * only, a name, and the fixed set of weekdays on which they work. It also
 * holds the behaviour that goes with that information, so the concrete types
 * only have to add what makes them different.</p>
 *
 * <p><b>Why the class is abstract.</b> The clinic never records a health
 * professional who has no specialty, so a plain {@code new
 * HealthProfessional(...)} would describe nothing that exists. Declaring the
 * class {@code abstract} makes that mistake impossible to compile, and every
 * concrete subclass is forced to answer {@link #getRole()} before it can be
 * created. This is the abstraction pillar from Module 3.</p>
 *
 * <p><b>How the data is protected.</b> All three fields are {@code private}
 * and the class publishes getters only, so code outside this class can read a
 * professional's details but can never put the object into an inconsistent
 * state. The constructor is the single place where the fields are set, and it
 * cleans what it is given before storing it. This is the encapsulation pillar
 * from Module 2.</p>
 *
 * <p><b>Why there are two constructors.</b> Most professionals work the same
 * three days, so a caller who does not care about the schedule supplies only
 * the identifier and the name. The shorter constructor delegates to the longer
 * one with {@code this(...)}, so the cleaning code is written once and cannot
 * drift apart. This is constructor overloading and constructor chaining, both
 * from Module 3.</p>
 *
 * @author Ziliang Lu (25259146)
 * @version 1.2
 */
public abstract class HealthProfessional {

    /**
     * The only weekday names that a working day is allowed to be.
     *
     * <p>The names are kept in one place so that the validation below and the
     * default schedule cannot disagree with each other. If the clinic ever
     * changed the names it uses, this array would be the only thing to edit.</p>
     */
    private static final String[] WEEKDAY_NAMES = {
        "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    };

    /**
     * The identifier used when a caller supplies nothing usable.
     *
     * <p>It is named as a constant rather than written as "0" inside the
     * method, so the fallback value can be changed in one place and cannot be
     * mistyped somewhere else.</p>
     */
    private static final String DEFAULT_IDENTIFIER = "0";

    /**
     * The name used when a caller supplies nothing usable.
     *
     * <p>A professional with a blank name would print as an empty line, which
     * looks like a bug in the output. A visible placeholder is both honest and
     * easier to spot in the demonstration.</p>
     */
    private static final String DEFAULT_NAME = "Unnamed professional";

    /**
     * Identifies the professional. Only digits are ever stored here.
     */
    private String identifier;

    /**
     * The professional's name. It is trimmed and never empty.
     */
    private String name;

    /**
     * The weekdays this professional works, in the order the caller supplied
     * them, without duplicates and without unknown names.
     */
    private ArrayList<String> workingDays;

    /**
     * Creates a health professional who works the default weekdays
     * (Monday, Wednesday and Friday).
     *
     * @param identifier the professional's identifier; only the digits of this
     *        text are kept
     * @param name the professional's name; blank text is replaced by a
     *        placeholder so that the object is never nameless
     */
    public HealthProfessional(String identifier, String name) {
        // Constructor chaining: the shorter constructor does no work of its
        // own. It hands the default schedule to the longer constructor, so both
        // constructors end up running exactly the same cleaning code.
        this(identifier, name, createDefaultWorkingDays());
    }

    /**
     * Creates a health professional who works the given weekdays.
     *
     * @param identifier the professional's identifier; only the digits of this
     *        text are kept
     * @param name the professional's name; blank text is replaced by a
     *        placeholder so that the object is never nameless
     * @param workingDays the weekdays this professional works; unknown names
     *        and duplicates are dropped, and an empty result falls back to the
     *        default schedule
     */
    public HealthProfessional(String identifier, String name,
                              ArrayList<String> workingDays) {
        this.identifier = cleanIdentifier(identifier);
        this.name = cleanText(name, DEFAULT_NAME);
        this.workingDays = cleanWorkingDays(workingDays);
    }

    /**
     * Creates the weekdays a professional works when the caller does not
     * supply any.
     *
     * <p>A new list is built on every call instead of a shared constant being
     * returned, because a caller that edited a shared list would silently
     * change the default for every professional created afterwards.</p>
     *
     * @return a new list holding Monday, Wednesday and Friday
     */
    private static ArrayList<String> createDefaultWorkingDays() {
        ArrayList<String> days = new ArrayList<String>();
        days.add("Monday");
        days.add("Wednesday");
        days.add("Friday");
        return days;
    }

    /**
     * Keeps the digits of the supplied identifier and nothing else, so the
     * rule "an identifier is a number" can never be broken.
     *
     * @param identifier the identifier supplied by the caller, which may be
     *        null, blank, or contain letters and punctuation
     * @return an identifier containing digits only; never null and never empty
     */
    private static String cleanIdentifier(String identifier) {
        String digitsOnly = "";

        // A null check comes first because every method called on the text
        // would fail on null, and a caller that "forgot" the identifier is an
        // ordinary mistake rather than an exceptional situation.
        if (identifier != null) {

            // The characters are inspected one at a time with a simple
            // comparison instead of a regular expression or Character.isDigit:
            // comparing two characters only needs the operators from Module 1,
            // and the rule stays readable to anyone marking the code.
            for (int index = 0; index < identifier.length(); index++) {
                char character = identifier.charAt(index);
                if (character >= '0' && character <= '9') {
                    digitsOnly = digitsOnly + character;
                }
            }
        }

        // Falling back to a constant keeps the promise that the getter always
        // returns a usable identifier, so no caller has to check for null.
        if (digitsOnly.length() == 0) {
            return DEFAULT_IDENTIFIER;
        }
        return digitsOnly;
    }

    /**
     * Trims a piece of text and falls back to a default when nothing is left.
     *
     * <p>The method is {@code protected static} because every subclass needs
     * the same treatment for its own text fields, and the treatment does not
     * depend on any object being created.</p>
     *
     * @param text the text supplied by the caller, which may be null
     * @param fallback the value to use when the text is empty
     * @return the trimmed text, or the fallback when there is nothing left
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
     * <p>Filtering here, rather than trusting the caller, is what makes the
     * working schedule safe to print and to compare: the class never holds a
     * name such as "Funday" that the rest of the clinic would not recognise.</p>
     *
     * @param days the weekdays supplied by the caller, which may be null
     * @return a clean list of weekday names, never null and never empty
     */
    private static ArrayList<String> cleanWorkingDays(ArrayList<String> days) {
        ArrayList<String> cleaned = new ArrayList<String>();

        if (days != null) {
            for (String day : days) {
                String candidate = cleanText(day, "");

                // An unknown name is dropped and a repeated day is ignored:
                // "Monday, Monday" would print twice and "Funday" would mean
                // nothing to any other part of the system.
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
     * <p>The names are compared in a fixed order rather than sorted, because
     * the order the clinic lists them in is the order people expect to read
     * them on a printed schedule.</p>
     *
     * @param text the text to check
     * @return {@code true} when the text names a weekday
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
     * <p>There is deliberately no setter. The identifier is what distinguishes
     * one professional from another, so letting other classes rewrite it later
     * would allow two records to claim the same identity.</p>
     *
     * @return the identifier, digits only, never empty
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Returns the professional's name.
     *
     * @return the name, never empty
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the weekdays this professional works.
     *
     * <p>The copy is what makes the encapsulation real: the getter's signature
     * promises the caller that this list can be read, and returning the copy
     * keeps the promise that no outside edit can change the schedule that was
     * validated in the constructor.</p>
     *
     * @return a copy of the working days
     */
    public ArrayList<String> getWorkingDays() {
        return new ArrayList<String>(workingDays);
    }

    /**
     * Tells whether this professional works on the given day.
     *
     * <p>This is the method the appointment system asks before it offers a
     * booking, so the rule "who works when" stays inside the professional
     * rather than being spread across the classes that use it.</p>
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
     * <p>Every concrete subclass must answer this. That is what lets the
     * shared details be printed without the parent class knowing which subtype
     * it is dealing with, and it is the method that makes polymorphism work
     * when the objects are stored together in one list.</p>
     *
     * @return the role, for example "General Practitioner"
     */
    public abstract String getRole();

    /**
     * Returns the details every health professional has in common.
     *
     * <p>Subclasses call this from their own {@code toString()} and then append
     * what is unique to them, so the shared part is written once and every type
     * prints in the same layout.</p>
     *
     * @return a multi-line description of the shared details
     */
    @Override
    public String toString() {
        // The label column is padded to fourteen characters so the values line
        // up underneath each other when several professionals are printed in a
        // row. The value is built with string concatenation only, which is the
        // output technique covered in Module 1.
        return "Identifier:   " + identifier + "\n"
             + "Name:         " + name + "\n"
             + "Role:         " + getRole() + "\n"
             + "Working days: " + describeWorkingDays();
    }

    /**
     * Joins the working days into one readable line.
     *
     * <p>The commas are added between the days rather than after each one, so
     * the line never ends with a stray comma.</p>
     *
     * @return the working days separated by commas
     */
    private String describeWorkingDays() {
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
