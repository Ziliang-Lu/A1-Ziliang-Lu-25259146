# A1-25259146 — PROG2004 Assessment 1

Health appointment system, implemented in Java with object-oriented design
concepts from Modules 1–3 of PROG2004 Object Oriented Programming.

There is no user interface, no menu, no login and no data persistence: the
project is a set of collaborating classes that model the domain, plus a
scripted demonstration in `main()` that exercises their behaviour and prints
the results to the console.

## Planned structure

```
A1-25259146/
├── src/
│   └── AssignmentOne.java     entry point: main() and the scripted demonstration
└── README.md
```

Additional classes are added under `src/` as the design develops:

| Area | Purpose |
|---|---|
| Health professional model | Common data and behaviour shared by every health professional |
| Health professional types | At least four specific types, including a general practitioner |
| Appointment model | Links a patient, a time slot and a chosen health professional |
| Appointment management | Add, view, filter, sort and cancel appointments for the day |

## Domain constraints

- A health professional is identified by a numeric ID and a name, and works a
  fixed set of weekdays.
- Bookings are for the current day only.
- The clinic schedule is fixed: 08:00–16:00 in 30-minute slots.
- A health professional can never be double-booked into the same time slot.

## Build and run

Compile and run from the project root with JDK 26:

```
javac -d out src/AssignmentOne.java
java -cp out AssignmentOne
```

## Progress

Development history is tracked with regular commits in this repository, and
time spent on the project is recorded with WakaTime.
