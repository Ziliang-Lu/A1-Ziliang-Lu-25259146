# A1-Ziliang-Lu-25259146 — PROG2004 Assessment 1

Health appointment system, implemented in Java with object-oriented design
concepts from Modules 1–3 of PROG2004 Object Oriented Programming.

There is no user interface, no menu, no login and no data persistence in the
scripted demonstration: the project is a set of collaborating classes that model
the domain, plus a scripted demonstration in `main()` that exercises their
behaviour and prints the results to the console. An optional console front end
adds a menu on top of the same classes, and is started with the argument
`console`.

## Structure

```
A1-Ziliang-Lu-25259146/
├── src/
│   ├── AssignmentOne.java         entry point: main() and the demonstration
│   ├── HealthProfessional.java    abstract parent of every professional type
│   ├── Prescribable.java          capability: may prescribe medication
│   ├── GeneralPractitioner.java   a doctor, adds a consulting room
│   ├── Specialist.java            a doctor, adds an area of medicine
│   ├── Surgeon.java               a doctor, adds an operating theatre
│   ├── Nurse.java                 adds a ward, takes blood samples
│   ├── Patient.java               a patient: name and mobile number
│   ├── TimeSlot.java              one position in the clinic timetable
│   ├── Appointment.java           patient + professional + time slot
│   ├── AppointmentManager.java    booking, filtering, sorting, cancelling
│   └── ClinicConsole.java         interactive front end: login, book, cancel
└── README.md
```

| Area | Status |
|---|---|
| Health professional model | done — `HealthProfessional` |
| Health professional types | done — four types plus the `Prescribable` capability |
| Health professional objects | done — five doctors displayed together in `main()` |
| Appointment model | done — `Patient`, `TimeSlot` and `Appointment` with `Comparable` |
| Appointment management | done — booking, filtering, sorting and cancelling |
| End-to-end demonstration | done — `main()` shows the normal and the error cases |
| Interactive front end | done — `ClinicConsole`, started with the argument `console` |

## Domain constraints

- A health professional is identified by a numeric identifier and a name, and
  works a fixed set of weekdays.
- Bookings are for the current day only.
- The clinic schedule is fixed: 08:00–16:00 in 30-minute slots.
- A health professional can never be double-booked into the same time slot.

## Design notes

The design follows the patterns from the Modules 1–3 tutorials:

- **Abstraction** — `HealthProfessional` is abstract, so a professional with no
  specialty cannot be created, and every subclass is forced to answer
  `getRole()` and `getDepartment()`. `Prescribable` abstracts the prescribing
  capability on its own, which inheritance alone cannot express.
- **Encapsulation** — all fields are private and only getters are exposed. The
  constructor is the single place where data is set, and it is responsible for
  keeping the object valid, so external code can read a professional's details
  but can never put one into an invalid state.
- **Inheritance** — the four concrete types extend `HealthProfessional`, call
  `super(...)` to set up the shared fields, and reuse the shared behaviour
  instead of copying it.
- **Polymorphism** — the demonstration keeps every professional in a single
  `ArrayList<HealthProfessional>` and prints it in one loop; each object runs
  its own overridden `toString()`, which in turn calls its own `getRole()`.
- **Constructor overloading** — every type offers a shorter constructor that
  delegates with `this(...)` to the full one and supplies sensible defaults for
  the working days, stored as `private static final` constants.
- **Composition (has-a)** — an `Appointment` has a `Patient`, a
  `HealthProfessional` and a `TimeSlot` rather than copying their fields, so a
  change to a professional's room is visible everywhere at once. Its
  `toString()` calls theirs instead of re-formatting them.
- **`Comparable`** — `Appointment` and `TimeSlot` order themselves, so
  `Collections.sort(...)` sorts a day of bookings, and a filtered subset, with
  no comparison logic in the caller.
- **`equals` and `hashCode`** — two professionals are the same person when their
  identifiers match, and two appointments are the same booking slot when the
  professional and the time match. That second definition is exactly the rule
  the clinic forbids, so a double booking is detected by one `contains(...)`
  call.
- **Capability interface with `instanceof`** — the demonstration asks each
  professional whether it is `Prescribable` before prescribing, which is how one
  loop serves four types while the nurse correctly receives no prescription.
- **Gender and department** — every professional stores a gender and answers
  `getDepartment()`, so the console can show the same fields for all four types
  while each subclass supplies its own department.
- **Login kept out of the model** — `ClinicConsole` asks for the patient name and
  the phone number that works like a password; `Patient` only stores data. The
  check therefore lives in the console, and no domain class knows that a user
  interface exists.

## Code style

One style is used everywhere; it never varies between files:

- opening braces stay on the same line as the declaration (K&R)
- four spaces per indent level, never tabs
- every class and every method carries a Javadoc comment, because the
  assessment asks for Javadoc documentation
- fields and constants carry one short `//` comment at the end of the
  declaration line, for example `private String identifier;   // digits only`
- one blank line between members; files end with exactly one newline
- lines never exceed 100 columns
- identifiers are written in full words, never abbreviated (`identifier`,
  not `id`; `working days`, not `wd`)
- method names begin with a verb (`createHealthProfessionals`,
  `buildWorkingDays`, `displayHealthProfessionals`, `describeWorkingDays`)
- fields are `private` and camelCase; constants are `UPPER_CASE`

## Build and run

Compile and run from the project root with JDK 26:

```
javac -d out src/*.java
java -cp out AssignmentOne
```

## Progress

Development history is tracked with regular commits in this repository, and
time spent on the project is recorded with WakaTime.

## How to run the program, step by step

The whole procedure is five steps. Every command runs in the terminal of the
project folder, and the order matters: the program always runs the compiled
`.class` files, never the source files.

```bash
# ---------------------------------------------------------------------------
# Step 1 - open the project folder in Visual Studio Code
#   File > Open Folder > A1-Ziliang-Lu-25259146
#   The top line of the Explorer must show this folder name, and src/ must
#   contain 12 .java files.
#   Opening a folder higher up (for example "Object Oriented Programming") still
#   compiles, but "file not found" errors almost always mean the wrong folder.
# ---------------------------------------------------------------------------

# ---------------------------------------------------------------------------
# Step 2 - open the built-in terminal
#   Ctrl + ` (the backtick key, below Esc)
#   The prompt must end with the project folder name, for example
#   PS C:\...\A1-Ziliang-Lu-25259146>
#   If it does not, change directory to the project folder first:
cd "<the full path of the project folder>"
# ---------------------------------------------------------------------------

# ---------------------------------------------------------------------------
# Step 3 - check that a JDK is available
javac -version
# Expected output: javac 26.0.2.1
# If the terminal says javac is not recognised, the JDK is not on the PATH:
# close and reopen the terminal, or repair the PATH before going on.
# ---------------------------------------------------------------------------

# ---------------------------------------------------------------------------
# Step 4 - compile every source file into the out folder
javac -d out src/*.java
# Expected output: nothing at all. Silence means the compilation succeeded.
# A folder named out/ now holds 12 .class files. It is build output: it is
# listed in .gitignore, so it is never committed and never included in the
# submitted zip. Delete it at any time; the next compile recreates it.
# ---------------------------------------------------------------------------

# ---------------------------------------------------------------------------
# Step 5 - run the scripted demonstration
java -cp out AssignmentOne
# Expected output: 227 lines, starting with
#   PROG2004 Assessment 1 - Health appointment system
# and ending with
#   End of demonstration.
# The output follows the parts of the assessment, in this order:
#   health professionals at the clinic      Part 3
#   who works at the clinic                 introductions
#   what each professional can do           Part 2
#   booking appointments                    Part 5
#   all appointments, earliest first        Part 4 and Part 5
#   appointments for professional 1001      filtering by professional
#   appointments for patient 0412345678     filtering by patient
#   cancelling ...                          cancelling, three ways
#   refusing ...                            the error cases
#   end of demonstration
# ---------------------------------------------------------------------------

# ---------------------------------------------------------------------------
# Optional - keep the output for later reading
java -cp out AssignmentOne > output.txt
# output.txt is not committed; delete it when it is no longer needed.
# ---------------------------------------------------------------------------

# ---------------------------------------------------------------------------
# Optional - start the interactive console
java -cp out AssignmentOne console
# After the demonstration the interactive front end starts:
#
#   Step 1  log in
#     The five patients are listed by name only; the phone number is never
#     shown. Choose a number, then type the phone number that works like a
#     password. A wrong number gives:
#         Name or phone number do not match.
#     Choose 0 to register instead: enter a name and a phone number and the
#     patient is logged in straight away. A number that is already registered
#     gives:
#         That phone number is already registered.
#
#   Step 2  the menu after logging in
#     1  book an appointment
#     2  my appointments
#     3  list all appointments
#     9  log out
#     0  quit
#
#   Step 3  booking (the doctor is chosen first, then the time)
#     Each doctor is shown with the name, the gender, the role, the department
#     and today's booking count. After choosing one, the full profile is shown,
#     including the introduction. Then the doctor's time slots are listed: a
#     slot that is already booked is marked "already booked", and choosing it
#     is refused:
#         That time is already booked for Alice Brown.
#
#   Step 4  my appointments (keep or cancel)
#     The patient's own appointments are listed; choosing one shows its details
#     and then offers:
#         1  keep this appointment
#         2  cancel this appointment
#     After a cancellation the list is shown again, with the booking removed.
#
# Without the console argument (Step 5) there is only the scripted
# demonstration and no menu at all.
# ---------------------------------------------------------------------------

# ---------------------------------------------------------------------------
# Note: after changing any .java file, repeat Step 4 before Step 5.
# ---------------------------------------------------------------------------
```

### If something goes wrong

| Message | Cause | Fix |
|---|---|---|
| `javac` is not recognised | the JDK is not on the PATH | reopen the terminal |
| `Could not find or load main class` | Step 4 was skipped | run Step 4 again |
| `src/*.java: no such file` | the terminal is in the wrong folder | run the `cd` in Step 2 |
| the output stops early | the buffer was trimmed | use `> output.txt` |
