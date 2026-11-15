import java.util.ArrayList;
import java.util.Scanner;

/**
 * Part 6: the interactive front end of the clinic.
 *
 * Fields: manager, patients, professionals, schedule, scanner, currentPatient.
 * Methods: run(), showLoginMenu(), logIn(), registerPatient(), showPatientMenu(),
 *          bookAppointment(), showDoctors(), showDoctorDetails(),
 *          showFreeSlots(), showMyAppointments(), showAllAppointments().
 *
 * A patient logs in with a name and a phone number first, and only then can
 * book, look at their own appointments or cancel one. The console is started by
 * AssignmentOne when the program is given the argument "console".
 */
public class ClinicConsole {

    private AppointmentManager manager;                  // the day's bookings
    private ArrayList<Patient> patients;                 // the known patients
    private ArrayList<HealthProfessional> professionals; // the doctors on duty
    private ArrayList<TimeSlot> schedule;                // the clinic timetable
    private Scanner scanner;                             // reads the keyboard
    private Patient currentPatient;                      // the patient logged in

    /**
     * Creates the console around the data the demonstration already built.
     *
     * @param professionals the doctors and the nurse on duty
     * @param patients the patients who can log in
     */
    public ClinicConsole(ArrayList<HealthProfessional> professionals,
                         ArrayList<Patient> patients) {
        this.professionals = professionals;
        this.patients = patients;
        this.manager = new AppointmentManager();
        this.schedule = TimeSlot.createDailySchedule();
        this.scanner = new Scanner(System.in);
        addStartingAppointments();
    }

    /**
     * Runs the console until the user quits.
     */
    public void run() {
        boolean running = true;

        System.out.println();
        System.out.println("Clinic appointment console");
        System.out.println("==========================");

        while (running) {
            if (currentPatient == null) {
                running = showLoginMenu();
            } else {
                running = showPatientMenu();
            }
        }

        System.out.println("Console closed.");
    }

    /**
     * Shows the menu used before a patient logs in.
     *
     * @return false when the user wants to quit
     */
    private boolean showLoginMenu() {
        System.out.println();
        System.out.println("1  log in");
        System.out.println("2  list all appointments");
        System.out.println("0  quit");

        String choice = readLine("Your choice: ");

        if (choice.equals("1")) {
            logIn();
        } else if (choice.equals("2")) {
            showAllAppointments();
        } else if (choice.equals("0")) {
            return false;
        } else {
            System.out.println("Unknown choice: " + choice);
        }
        return true;
    }

    /**
     * Asks for a patient name and a phone number, and logs the patient in.
     */
    private void logIn() {
        showPatientNames();
        String choice = readLine("Choose a patient (0 to register): ");

        if (choice.equals("0")) {
            registerPatient();
            return;
        }

        Patient patient = findPatientByNumber(choice);
        if (patient == null) {
            System.out.println("There is no patient with that number.");
            return;
        }

        String phoneNumber = readLine("Phone number: ");

        // the phone number works like a password, so a wrong number gives the
        // same message as a wrong name and does not say which one was wrong
        if (!patient.getPhoneNumber().equals(phoneNumber)) {
            System.out.println("Name or phone number do not match.");
            return;
        }

        currentPatient = patient;
        System.out.println("Logged in as " + currentPatient.getName() + ".");
    }

    /**
     * Registers a new patient and logs them in.
     */
    private void registerPatient() {
        System.out.println();
        System.out.println("New patient");

        String name = readLine("Name: ");
        String phoneNumber = readLine("Phone number: ");

        if (name.length() == 0) {
            System.out.println("A name is required.");
            return;
        }
        if (phoneNumber.length() == 0) {
            System.out.println("A phone number is required.");
            return;
        }

        // one phone number can only belong to one patient, so a second account
        // cannot take over an existing patient's appointments
        if (findPatientByPhoneNumber(phoneNumber) != null) {
            System.out.println("That phone number is already registered.");
            return;
        }

        Patient patient = new Patient(name, phoneNumber);
        patients.add(patient);

        currentPatient = patient;
        System.out.println("Registered and logged in as " + currentPatient.getName() + ".");
    }

    /**
     * Shows the menu used after a patient logs in.
     *
     * @return false when the user wants to quit
     */
    private boolean showPatientMenu() {
        System.out.println();
        System.out.println("Logged in as " + currentPatient.getName());
        System.out.println("1  book an appointment");
        System.out.println("2  my appointments");
        System.out.println("3  list all appointments");
        System.out.println("9  log out");
        System.out.println("0  quit");

        String choice = readLine("Your choice: ");

        if (choice.equals("1")) {
            bookAppointment();
        } else if (choice.equals("2")) {
            showMyAppointments();
        } else if (choice.equals("3")) {
            showAllAppointments();
        } else if (choice.equals("9")) {
            currentPatient = null;
            System.out.println("Logged out.");
        } else if (choice.equals("0")) {
            return false;
        } else {
            System.out.println("Unknown choice: " + choice);
        }
        return true;
    }

    /**
     * Books an appointment: the doctor is chosen first, then the time, then the
     * booking is made for the patient who is logged in.
     */
    private void bookAppointment() {
        showDoctors();
        String choice = readLine("Choose a doctor (0 to go back): ");

        if (choice.equals("0")) {
            return;
        }

        HealthProfessional professional = findProfessionalByNumber(choice);
        if (professional == null) {
            System.out.println("There is no doctor with that number.");
            return;
        }

        showDoctorDetails(professional);
        String confirm = readLine("1 to choose this doctor, 0 to go back: ");

        if (!confirm.equals("1")) {
            return;
        }

        TimeSlot timeSlot = chooseTimeSlot(professional);
        if (timeSlot == null) {
            return;
        }

        System.out.println(manager.addAppointment(currentPatient, professional, timeSlot));
    }

    /**
     * Shows the time slots of one doctor and lets the user pick a free one.
     *
     * @param professional the doctor whose slots are shown
     * @return the chosen slot, or null when the user goes back or picks a slot
     *         that is already booked
     */
    private TimeSlot chooseTimeSlot(HealthProfessional professional) {
        System.out.println();
        System.out.println("Time slots for " + professional.getName());

        for (int index = 0; index < schedule.size(); index++) {
            TimeSlot timeSlot = schedule.get(index);
            String status = "free";

            // a booked slot is still shown, so the user can see why it cannot be
            // chosen, but it is marked and refused below
            if (isBooked(professional, timeSlot)) {
                status = "already booked";
            }

            System.out.println("  " + (index + 1) + "  " + timeSlot.getLabel() + "   " + status);
        }

        String choice = readLine("Choose a time (0 to go back): ");
        if (choice.equals("0")) {
            return null;
        }

        TimeSlot timeSlot = findSlotByNumber(choice);
        if (timeSlot == null) {
            System.out.println("There is no time with that number.");
            return null;
        }

        if (isBooked(professional, timeSlot)) {
            System.out.println("That time is already booked for " + professional.getName() + ".");
            return null;
        }

        return timeSlot;
    }

    /**
     * Shows the appointments of the logged in patient and lets them cancel one.
     */
    private void showMyAppointments() {
        ArrayList<Appointment> mine =
                manager.filterByPatientPhoneNumber(currentPatient.getPhoneNumber());

        System.out.println();
        System.out.println("My appointments (" + mine.size() + ")");

        if (mine.size() == 0) {
            System.out.println("  You have no appointments.");
            return;
        }

        for (int index = 0; index < mine.size(); index++) {
            Appointment appointment = mine.get(index);
            System.out.println("  " + (index + 1) + "  " + appointment.getTimeSlot().getLabel()
                    + "   " + appointment.getHealthProfessional().getName()
                    + "   " + appointment.getHealthProfessional().getRole());
        }

        String choice = readLine("Choose an appointment (0 to go back): ");
        if (choice.equals("0")) {
            return;
        }

        Appointment chosen = null;
        for (int index = 0; index < mine.size(); index++) {
            if (choice.equals("" + (index + 1))) {
                chosen = mine.get(index);
            }
        }

        if (chosen == null) {
            System.out.println("There is no appointment with that number.");
            return;
        }

        System.out.println();
        System.out.println("Appointment details");
        System.out.println("  Time:   " + chosen.getTimeSlot().getLabel());
        System.out.println("  Doctor: " + chosen.getHealthProfessional().getName()
                + " (" + chosen.getHealthProfessional().getGender() + ", "
                + chosen.getHealthProfessional().getRole() + ", "
                + chosen.getHealthProfessional().getDepartment() + ")");
        System.out.println("1  keep this appointment");
        System.out.println("2  cancel this appointment");

        String action = readLine("Your choice: ");

        if (action.equals("2")) {
            System.out.println(manager.cancelByHealthProfessionalAndTime(
                    chosen.getHealthProfessional().getIdentifier(), chosen.getTimeSlot()));
        } else if (action.equals("1")) {
            System.out.println("The appointment was kept.");
        } else {
            System.out.println("Unknown choice: " + action);
        }
    }

    /**
     * Shows every appointment of the day, earliest first.
     */
    private void showAllAppointments() {
        ArrayList<Appointment> appointments = manager.getAllAppointments();

        System.out.println();
        System.out.println("All appointments (" + appointments.size() + ")");

        for (int index = 0; index < appointments.size(); index++) {
            Appointment appointment = appointments.get(index);
            System.out.println("  " + appointment.getTimeSlot().getLabel() + "   "
                    + appointment.getPatient().getName() + "   "
                    + appointment.getHealthProfessional().getName());
        }
    }

    /**
     * Shows the patients as a numbered list of names.
     *
     * Only the names are printed, because the phone number works like a password
     * and must not be shown to the other patients.
     */
    private void showPatientNames() {
        System.out.println();
        System.out.println("Patients");

        for (int index = 0; index < patients.size(); index++) {
            System.out.println("  " + (index + 1) + "  " + patients.get(index).getName());
        }
        System.out.println("  0  I am a new patient");
    }

    /**
     * Shows the doctors as a numbered list with the department and today's
     * booking count.
     */
    private void showDoctors() {
        System.out.println();
        System.out.println("Doctors on duty");

        for (int index = 0; index < professionals.size(); index++) {
            HealthProfessional professional = professionals.get(index);
            int booked = manager.countAppointmentsFor(professional.getIdentifier());

            System.out.println("  " + (index + 1) + "  " + professional.getName()
                    + "  (" + professional.getGender() + ")");
            System.out.println("      " + professional.getRole()
                    + " - " + professional.getDepartment()
                    + " - today " + booked + " appointment(s)");
        }
    }

    /**
     * Shows everything recorded about one doctor.
     *
     * @param professional the doctor to show
     */
    private void showDoctorDetails(HealthProfessional professional) {
        System.out.println();
        System.out.println("Doctor details");
        System.out.println(professional);
        System.out.println("Introduction: " + professional.getIntroduction());
    }

    /**
     * Tells whether one doctor is already booked at one time.
     *
     * @param professional the doctor to check
     * @param timeSlot the time to check
     * @return true when that doctor already has an appointment then
     */
    private boolean isBooked(HealthProfessional professional, TimeSlot timeSlot) {
        ArrayList<Appointment> booked =
                manager.filterByHealthProfessional(professional.getIdentifier());

        for (int index = 0; index < booked.size(); index++) {
            if (booked.get(index).getTimeSlot().equals(timeSlot)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Books the appointments that already exist when the console starts.
     */
    private void addStartingAppointments() {
        manager.addAppointment(patients.get(0), professionals.get(0), schedule.get(2));
        manager.addAppointment(patients.get(1), professionals.get(2), schedule.get(3));
        manager.addAppointment(patients.get(2), professionals.get(3), schedule.get(4));
        manager.addAppointment(patients.get(3), professionals.get(0), schedule.get(5));
        manager.addAppointment(patients.get(4), professionals.get(4), schedule.get(6));
    }

    /**
     * Finds a patient by the number shown in the list.
     *
     * @param choice the number typed by the user
     * @return the matching patient, or null when the number is not in the list
     */
    private Patient findPatientByNumber(String choice) {
        for (int index = 0; index < patients.size(); index++) {
            if (choice.equals("" + (index + 1))) {
                return patients.get(index);
            }
        }
        return null;
    }

    /**
     * Finds a patient by phone number.
     *
     * @param phoneNumber the number typed by the user
     * @return the matching patient, or null when nobody matches
     */
    private Patient findPatientByPhoneNumber(String phoneNumber) {
        for (int index = 0; index < patients.size(); index++) {
            if (patients.get(index).getPhoneNumber().equals(phoneNumber)) {
                return patients.get(index);
            }
        }
        return null;
    }

    /**
     * Finds a doctor by the number shown in the list.
     *
     * @param choice the number typed by the user
     * @return the matching doctor, or null when the number is not in the list
     */
    private HealthProfessional findProfessionalByNumber(String choice) {
        for (int index = 0; index < professionals.size(); index++) {
            if (choice.equals("" + (index + 1))) {
                return professionals.get(index);
            }
        }
        return null;
    }

    /**
     * Finds a time slot by the number shown in the list.
     *
     * @param choice the number typed by the user
     * @return the matching slot, or null when the number is not in the list
     */
    private TimeSlot findSlotByNumber(String choice) {
        for (int index = 0; index < schedule.size(); index++) {
            if (choice.equals("" + (index + 1))) {
                return schedule.get(index);
            }
        }
        return null;
    }

    /**
     * Reads one line typed by the user.
     *
     * @param prompt the text shown before reading
     * @return the trimmed answer, or "0" when the input has ended
     */
    private String readLine(String prompt) {
        System.out.print(prompt);

        if (!scanner.hasNextLine()) {
            return "0";
        }
        return scanner.nextLine().trim();
    }
}
