import java.util.ArrayList;
import java.util.Collections;

/**
 * Entry point for the health appointment system.
 *
 * <p>This class holds the {@code main} method that demonstrates the system.
 * There is no user interface and no keyboard input: the demonstration creates
 * the objects itself, exercises their behaviour and prints the results, in the
 * same way that a scripted simulation would stand in for real users.</p>
 *
 * <p><b>How the demonstration is organised.</b> {@code main} does no work of
 * its own beyond printing a banner; the real steps are separate methods with
 * names that say what they do. A {@code main} method that filled the screen
 * with details would be hard to read and impossible to reuse, so each step is
 * kept small and can be called again once Parts 4 to 6 add more to show.</p>
 *
 * <p><b>What this stage covers.</b> Parts 1 to 4 of the assessment: creating
 * health professionals of several types, storing them together as their common
 * parent type, displaying their details and their introductions, and booking
 * appointments that order themselves by time.</p>
 *
 * @author Ziliang Lu (25259146)
 * @version 1.4
 */
public class AssignmentOne {

    /**
     * Runs the demonstration.
     *
     * <p>Only the order of the steps lives here, which makes the flow of the
     * demonstration obvious at a glance: the professionals are created once and
     * then handed to the step that displays them.</p>
     *
     * @param args command line arguments, not used by this program
     */
    public static void main(String[] args) {
        System.out.println("PROG2004 Assessment 1 - Health appointment system");
        System.out.println("=================================================");
        System.out.println();

        ArrayList<HealthProfessional> professionals = createHealthProfessionals();
        displayHealthProfessionals(professionals);
        displayIntroductions(professionals);
        demonstrateAppointmentOrdering(professionals);
    }

    /**
     * Creates the health professionals used in the demonstration.
     *
     * <p>Every object is created through its concrete type, because that is
     * what decides what the object holds. They are then added to a list typed
     * as the parent class, which is what lets the display step handle all of
     * them with one piece of code.</p>
     *
     * <p>The mix of constructors is deliberate: most professionals are created
     * with the shorter constructor so the default schedule shows up in the
     * output, and the rest are created with the full constructor so that a
     * supplied schedule is clearly different.</p>
     *
     * @return the list of health professionals
     */
    private static ArrayList<HealthProfessional> createHealthProfessionals() {
        ArrayList<HealthProfessional> professionals = new ArrayList<HealthProfessional>();

        // Three general practitioners. The first two use the shorter
        // constructor, so they inherit the default working days.
        professionals.add(new GeneralPractitioner("1001", "Alice Brown", "Room 4"));
        professionals.add(new GeneralPractitioner("1002", "Michael Chen", "Room 5"));

        // The third one supplies its own schedule, which proves that the full
        // constructor changes only the days and nothing else.
        professionals.add(new GeneralPractitioner("1003", "Priya Nair",
                buildWorkingDays("Tuesday", "Thursday", "Friday"), "Room 6"));

        // One to two of each of the other types, as the assessment asks. The
        // list is typed as HealthProfessional, so nothing here has to know
        // which subclass will be added next.
        professionals.add(new Specialist("2001", "Daniel Roberts", "Paediatrician"));
        professionals.add(new Specialist("2002", "Sofia Alvarez",
                buildWorkingDays("Monday", "Thursday", "Saturday"), "Cardiologist"));

        professionals.add(new Surgeon("3001", "James O'Connor", "Theatre 2"));
        professionals.add(new Surgeon("3002", "Wei Zhang",
                buildWorkingDays("Tuesday", "Wednesday", "Friday"), "Theatre 1"));

        professionals.add(new Nurse("4001", "Emma Wilson", "Ward 3"));
        professionals.add(new Nurse("4002", "Omar Haddad",
                buildWorkingDays("Monday", "Tuesday", "Saturday"), "Ward 5"));

        return professionals;
    }

    /**
     * Builds a list of three weekday names.
     *
     * <p>The clinic works in three-day schedules, so this helper keeps the
     * constructor calls above short. Building the list inline for every
     * professional would repeat the same four lines nine times and make the
     * list of professionals hard to scan.</p>
     *
     * @param first the first weekday
     * @param second the second weekday
     * @param third the third weekday
     * @return a list holding the three weekday names
     */
    private static ArrayList<String> buildWorkingDays(String first, String second,
                                                      String third) {
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
     * printed, that object's own {@code toString()} runs, and its
     * {@code toString()} in turn calls its own {@code getRole()} and appends
     * its own extra details. This is polymorphism: one loop, many types.</p>
     *
     * @param professionals the health professionals to display
     */
    private static void displayHealthProfessionals(
            ArrayList<HealthProfessional> professionals) {
        System.out.println("Health professionals at the clinic");
        System.out.println("----------------------------------");
        System.out.println();

        // A plain counting loop is used instead of the shorter for-each form
        // because the demonstration also needs the position for nothing else
        // yet, and the counting loop is the form taught in Module 1.
        for (int index = 0; index < professionals.size(); index++) {
            HealthProfessional professional = professionals.get(index);
            System.out.println(professional);

            // A blank line after each entry keeps nine records from running
            // together into one unreadable block.
            System.out.println();
        }

        System.out.println("Total health professionals: " + professionals.size());
    }

    /**
     * Displays a written introduction for every health professional.
     *
     * <p>The same method is called on every object, yet each subtype answers
     * with its own sentence, because {@code getIntroduction()} is overridden in
     * every concrete class. Nothing in this loop mentions a subtype, and no
     * subtype has to be handled separately.</p>
     *
     * @param professionals the health professionals to introduce
     */
    private static void displayIntroductions(ArrayList<HealthProfessional> professionals) {
        System.out.println();
        System.out.println("Who works at the clinic");
        System.out.println("-----------------------");
        System.out.println();

        for (int index = 0; index < professionals.size(); index++) {
            System.out.println(professionals.get(index).getIntroduction());
            System.out.println();
        }
    }

    /**
     * Demonstrates that appointments order themselves by time.
     *
     * <p>The appointments are created deliberately out of order, printed in the
     * order they were created, sorted with {@code Collections.sort(...)}, and
     * printed again. Because {@code Appointment} implements
     * {@code Comparable<Appointment>}, the sort needs to be told nothing about
     * how to compare two appointments.</p>
     *
     * <p>The last part of the method shows the refusal: booking without a health
     * professional returns {@code null}, so an incomplete appointment cannot be
     * created in the first place.</p>
     *
     * @param professionals the health professionals the appointments are booked
     *        with
     */
    private static void demonstrateAppointmentOrdering(
            ArrayList<HealthProfessional> professionals) {
        ArrayList<TimeSlot> dailySchedule = TimeSlot.createDailySchedule();
        ArrayList<Appointment> appointments = new ArrayList<Appointment>();

        Patient michael = new Patient("Michael Chen", "0412 345 678");
        Patient sofia = new Patient("Sofia Rossi", "0498 765 432");
        Patient oliver = new Patient("Oliver Grant", "0401 222 333");

        // The slots are picked so the list is not already in time order.
        // Position 6 is 11:00, position 3 is 09:30, position 1 is 08:30 and
        // position 4 is 10:00 on the standard timetable.
        appointments.add(Appointment.book(michael, professionals.get(0), dailySchedule.get(6)));
        appointments.add(Appointment.book(sofia, professionals.get(3), dailySchedule.get(3)));
        appointments.add(Appointment.book(oliver, professionals.get(5), dailySchedule.get(1)));
        appointments.add(Appointment.book(michael, professionals.get(7), dailySchedule.get(4)));

        System.out.println("Appointments in the order they were booked");
        System.out.println("------------------------------------------");
        System.out.println();
        displayAppointments(appointments);

        System.out.println();
        System.out.println("The same appointments sorted by time");
        System.out.println("------------------------------------");
        System.out.println();

        // Collections.sort only needs the interface: the list says what to sort
        // and Appointment says how two appointments compare with each other.
        Collections.sort(appointments);
        displayAppointments(appointments);

        System.out.println();
        System.out.println("Attempt to book an appointment without a professional");
        System.out.println("-----------------------------------------------------");

        Appointment incomplete = Appointment.book(michael, null, dailySchedule.get(2));
        if (incomplete == null) {
            System.out.println("Cannot book: the health professional is missing.");
        } else {
            System.out.println("Unexpected: the appointment was created.");
        }
    }

    /**
     * Displays every appointment in the list.
     *
     * @param appointments the appointments to display
     */
    private static void displayAppointments(ArrayList<Appointment> appointments) {
        for (int index = 0; index < appointments.size(); index++) {
            System.out.println(appointments.get(index));
            System.out.println();
        }
    }
}
