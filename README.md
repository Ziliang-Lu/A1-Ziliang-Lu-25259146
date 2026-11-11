# A1-Ziliang-Lu-25259146 — PROG2004 Assessment 1

Health appointment system, implemented in Java with object-oriented design
concepts from Modules 1–3 of PROG2004 Object Oriented Programming.

There is no user interface, no menu, no login and no data persistence: the
project is a set of collaborating classes that model the domain, plus a
scripted demonstration in `main()` that exercises their behaviour and prints
the results to the console.

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
│   └── Nurse.java                 adds a ward, takes blood samples
└── README.md
```

| Area | Status |
|---|---|
| Health professional model | done — `HealthProfessional` |
| Health professional types | done — four types plus the `Prescribable` capability |
| Health professional objects | done — created and displayed together in `main()` |
| Appointment model | not started |
| Appointment management | not started |

## Domain constraints

- A health professional is identified by a numeric ID and a name, and works a
  fixed set of weekdays.
- Bookings are for the current day only.
- The clinic schedule is fixed: 08:00–16:00 in 30-minute slots.
- A health professional can never be double-booked into the same time slot.

## Design notes

The design follows the patterns from the Modules 1–3 tutorials:

- **Abstraction** — `HealthProfessional` is abstract, so a professional with no
  specialty cannot be created, and every subclass is forced to answer
  `getRole()`. `Prescribable` abstracts the prescribing capability on its own,
  which inheritance alone cannot express.
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

## Code style

One style is used everywhere; it never varies between files:

- opening braces stay on the same line as the declaration (K&R)
- four spaces per indent level, never tabs
- every class and every method carries a Javadoc comment
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
