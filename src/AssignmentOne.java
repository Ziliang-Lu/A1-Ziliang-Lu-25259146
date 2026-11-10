import java.util.ArrayList;

/**
 * Entry point for the health appointment system.
 *
 * <p>This class holds the {@code main} method that demonstrates the system.
 * There is no user interface and no keyboard input: the demonstration creates
 * the objects itself, exercises their behaviour and prints the results, in the
 * same way that a scripted simulation would stand in for real users.</p>
 *
 * <p>At this stage the program demonstrates Parts 1 to 3 of the assessment:
 * creating health professionals of several types, storing them together as
 * their common parent type, and displaying their details.</p>
 *
 * @author Ziliang Lu (25259146)
 * @version 1.0
 */
public class AssignmentOne {

    /**
     * Runs the demonstration.
     *
     * @param args command line arguments, not used
     */
    public static void main(String[] args) {
        System.out.println("PROG2004 Assessment 1 - Health appointment system");
        System.out.println("=================================================");
        System.out.println();

        ArrayList<HealthProfessional> professionals = createHealthProfessionals();
        displayHealthProfessionals(professionals);
    }

    /**
     * Creates the health professionals used in the demonstration.
     *
     * <p>Every object is created through its concrete type, then stored in a
     * single list of the common parent type, which is what lets one piece of
     * code handle all of them.</p>
     *
     * @return the list of health professionals
     */
    private static ArrayList<HealthProfessional> createHealthProfessionals() {
        ArrayList<HealthProfessional> professionals = new ArrayList<HealthProfessional>();

        // Three general practitioners, one of them created with the shorter
        // constructor that supplies the default working days.
        professionals.add(new GeneralPractitioner("1001", "Alice Brown", "Room 4"));
        professionals.add(new GeneralPractitioner("1002", "Michael Chen", "Room 5"));
        professionals.add(new GeneralPractitioner("1003", "Priya Nair",
                days("Tuesday", "Thursday", "Friday"), "Room 6"));

        // One to two of each of the other types.
        professionals.add(new Specialist("2001", "Daniel Roberts", "Paediatrician"));
        professionals.add(new Specialist("2002", "Sofia Alvarez",
                days("Monday", "Thursday", "Saturday"), "Cardiologist"));

        professionals.add(new Surgeon("3001", "James O'Connor", "Theatre 2"));
        professionals.add(new Surgeon("3002", "Wei Zhang",
                days("Tuesday", "Wednesday", "Friday"), "Theatre 1"));

        professionals.add(new Nurse("4001", "Emma Wilson", "Ward 3"));
        professionals.add(new Nurse("4002", "Omar Haddad",
                days("Monday", "Tuesday", "Saturday"), "Ward 5"));

        return professionals;
    }

    /**
     * Builds a list of three weekday names.
     *
     * <p>A short helper keeps the constructor calls above readable instead of
     * building the same list inline for every professional.</p>
     *
     * @param first  the first weekday
     * @param second the second weekday
     * @param third  the third weekday
     * @return a list holding the three weekday names
     */
    private static ArrayList<String> days(String first, String second, String third) {
        ArrayList<String> workingDays = new ArrayList<String>();
        workingDays.add(first);
        workingDays.add(second);
        workingDays.add(third);
        return workingDays;
    }

    /**
     * Displays the details of every health professional in the list.
     *
     * <p>The list is typed as the parent class, so this method does not know
     * or care which concrete type each element is. When a professional is
     * printed, that object's own {@code toString()} runs, which is
     * polymorphism: one loop, many types.</p>
     *
     * @param professionals the health professionals to display
     */
    private static void displayHealthProfessionals(
            ArrayList<HealthProfessional> professionals) {
        System.out.println("Health professionals at the clinic");
        System.out.println("----------------------------------");
        System.out.println();

        for (int i = 0; i < professionals.size(); i++) {
            HealthProfessional professional = professionals.get(i);
            System.out.println(professional);
            System.out.println();
        }

        System.out.println("Total health professionals: " + professionals.size());
    }
}
