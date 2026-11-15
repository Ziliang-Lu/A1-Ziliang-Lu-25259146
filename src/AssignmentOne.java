import java.util.ArrayList;

/**
 * Part 6: the scripted demonstration of the system.
 *
 * Methods: main(), printHeading(), createHealthProfessionals(), createPatients(),
 *          buildWorkingDays(), displayHealthProfessionals(),
 *          displayIntroductions(), demonstrateProfessionalCapabilities(),
 *          demonstrateAppointmentManagement(), displayAppointments().
 *
 * Running the program without arguments prints the demonstration. Running it
 * with the argument "console" prints the demonstration and then starts the
 * interactive front end in ClinicConsole.
 */
public class AssignmentOne {

    /**
     * Runs the demonstration, and the console when it is asked for.
     *
     * @param commandLineArguments the words typed after the class name; the word
     *        "console" starts the interactive front end
     */
    public static void main(String[] commandLineArguments) {
        System.out.println("PROG2004 Assessment 1 - Health appointment system");
        System.out.println("=================================================");
        System.out.println();

        ArrayList<HealthProfessional> professionals = createHealthProfessionals();
        ArrayList<Patient> patients = createPatients();

        displayHealthProfessionals(professionals);
        displayIntroductions(professionals);
        demonstrateProfessionalCapabilities(professionals);
        demonstrateAppointmentManagement(professionals, patients);

        System.out.println("End of demonstration.");

        if (commandLineArguments.length > 0 && commandLineArguments[0].equals("console")) {
            ClinicConsole console = new ClinicConsole(professionals, patients);
            console.run();
        } else {
            System.out.println();
            System.out.println("Run with the word console after the class name to use the");
            System.out.println("interactive front end: java -cp out AssignmentOne console");
        }
    }

    /**
     * Prints a heading with an underline as long as the heading.
     *
     * @param heading the text to print
     */
    private static void printHeading(String heading) {
        String underline = "";

        // the underline is built from the heading, so the two cannot disagree
        for (int index = 0; index < heading.length(); index++) {
            underline = underline + "-";
        }

        System.out.println(heading);
        System.out.println(underline);
        System.out.println();
    }

    /**
     * Creates the five doctors of the clinic.
     *
     * @return the list of health professionals
     */
    private static ArrayList<HealthProfessional> createHealthProfessionals() {
        ArrayList<HealthProfessional> professionals = new ArrayList<HealthProfessional>();

        // two general practitioners, one specialist, one surgeon and one nurse,
        // so all four types of the hierarchy are on duty
        professionals.add(new GeneralPractitioner("1001", "Alice Brown", "Female", "Room 4"));
        professionals.add(new GeneralPractitioner("1002", "David Kim", "Male", "Room 5"));

        // this one uses the full constructor with its own working days
        professionals.add(new Specialist("2001", "Daniel Roberts", "Male",
                buildWorkingDays("Monday", "Tuesday", "Wednesday"), "Paediatrician"));

        professionals.add(new Surgeon("3001", "James O'Connor", "Male", "Theatre 2"));
        professionals.add(new Nurse("4001", "Emma Wilson", "Female", "Ward 3"));

        return professionals;
    }

    /**
     * Creates the five patients of the clinic.
     *
     * @return the list of patients
     */
    private static ArrayList<Patient> createPatients() {
        ArrayList<Patient> patients = new ArrayList<Patient>();

        patients.add(new Patient("Michael Chen", "0412 345 678"));
        patients.add(new Patient("Sofia Rossi", "0498 765 432"));
        patients.add(new Patient("Oliver Grant", "0401 222 333"));
        patients.add(new Patient("Grace Lin", "0455 111 222"));
        patients.add(new Patient("Noah Patel", "0433 777 888"));

        return patients;
    }

    /**
     * Builds a list of three weekday names.
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
     * Displays every health professional in the list.
     *
     * @param professionals the health professionals to display
     */
    private static void displayHealthProfessionals(
            ArrayList<HealthProfessional> professionals) {
        printHeading("Health professionals at the clinic");

        // the list is typed as the parent class, so each object prints with its
        // own toString() and no type has to be handled separately
        for (int index = 0; index < professionals.size(); index++) {
            System.out.println(professionals.get(index));
            System.out.println();
        }

        System.out.println("Total health professionals: " + professionals.size());
    }

    /**
     * Displays a written introduction for every health professional.
     *
     * @param professionals the health professionals to introduce
     */
    private static void displayIntroductions(ArrayList<HealthProfessional> professionals) {
        System.out.println();
        printHeading("Who works at the clinic");

        for (int index = 0; index < professionals.size(); index++) {
            System.out.println(professionals.get(index).getIntroduction());
            System.out.println();
        }
    }

    /**
     * Shows the behaviour that belongs to each kind of professional.
     *
     * @param professionals the health professionals to exercise
     */
    private static void demonstrateProfessionalCapabilities(
            ArrayList<HealthProfessional> professionals) {
        printHeading("What each professional can do");

        for (int index = 0; index < professionals.size(); index++) {
            HealthProfessional professional = professionals.get(index);

            // the cast only happens after the object has been tested for the
            // interface, which is the safe order
            if (professional instanceof Prescribable) {
                Prescribable prescriber = (Prescribable) professional;
                System.out.println(prescriber.prescribe("paracetamol", "Michael Chen"));
            }

            if (professional instanceof Surgeon) {
                Surgeon surgeon = (Surgeon) professional;
                System.out.println(surgeon.performOperation("appendectomy", "Oliver Grant"));
            }

            if (professional instanceof Nurse) {
                Nurse nurse = (Nurse) professional;
                System.out.println(nurse.takeBloodSample("Sofia Rossi"));
            }
        }

        System.out.println();
        System.out.println("A nurse cannot prescribe, which is why no prescription appears");
        System.out.println("for the nurse above.");
    }

    /**
     * Demonstrates the appointment manager with normal and error cases.
     *
     * @param professionals the doctors the appointments are booked with
     * @param patients the patients who book
     */
    private static void demonstrateAppointmentManagement(
            ArrayList<HealthProfessional> professionals, ArrayList<Patient> patients) {
        AppointmentManager manager = new AppointmentManager();
        ArrayList<TimeSlot> schedule = TimeSlot.createDailySchedule();

        Patient michael = patients.get(0);
        Patient sofia = patients.get(1);
        Patient oliver = patients.get(2);

        HealthProfessional alice = professionals.get(0);
        HealthProfessional daniel = professionals.get(2);
        HealthProfessional james = professionals.get(3);
        HealthProfessional emma = professionals.get(4);

        // position 1 is 08:30, position 2 is 09:00 and position 4 is 10:00
        TimeSlot eightThirty = schedule.get(1);
        TimeSlot nineOClock = schedule.get(2);
        TimeSlot tenOClock = schedule.get(4);

        System.out.println();
        printHeading("Booking appointments");
        System.out.println(manager.addAppointment(michael, alice, nineOClock));
        System.out.println(manager.addAppointment(sofia, daniel, nineOClock));
        System.out.println(manager.addAppointment(oliver, james, eightThirty));
        System.out.println(manager.addAppointment(michael, emma, tenOClock));

        // Alice is free at 08:30 but booked at 09:00, so this booking is refused
        System.out.println(manager.addAppointment(michael, alice, nineOClock));

        System.out.println();
        printHeading("All appointments, earliest first");
        System.out.println(manager.getAppointmentCount() + " appointment(s) booked.");
        System.out.println();
        displayAppointments(manager.getAllAppointments());

        printHeading("Appointments for professional 1001");
        displayAppointments(manager.filterByHealthProfessional("1001"));

        printHeading("Appointments for patient 0412345678");
        displayAppointments(manager.filterByPatientPhoneNumber("0412345678"));

        printHeading("Cancelling the 09:00 appointment with professional 1001");
        System.out.println(manager.cancelByHealthProfessionalAndTime("1001", nineOClock));

        System.out.println();
        printHeading("Cancelling every appointment for patient 0412345678");
        System.out.println(manager.cancelByPatientPhoneNumber("0412345678"));

        System.out.println();
        printHeading("Trying to cancel the same appointment again");
        System.out.println(manager.cancelByHealthProfessionalAndTime("1001", nineOClock));

        System.out.println();
        printHeading("Requesting a time that is not a clinic slot");
        TimeSlot notASlot = TimeSlot.findByLabel("11:15");
        if (notASlot == null) {
            System.out.println("Cannot book: 11:15 is not one of the clinic's time slots.");
        } else {
            System.out.println(manager.addAppointment(michael, alice, notASlot));
        }

        System.out.println();
        printHeading("Booking without a health professional");
        System.out.println(manager.addAppointment(michael, null, tenOClock));

        System.out.println();
        printHeading("Appointments left at the end of the day");
        System.out.println(manager.getAppointmentCount() + " appointment(s) booked.");
        System.out.println();
        displayAppointments(manager.getAllAppointments());
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
