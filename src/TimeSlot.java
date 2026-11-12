import java.util.ArrayList;

/**
 * One bookable slot in the clinic's daily schedule.
 *
 * <p>The clinic runs to a fixed timetable: it opens at 08:00, closes at 16:00,
 * and every appointment lasts thirty minutes. A slot is therefore not an
 * arbitrary moment; it is one of the sixteen positions in that timetable. This
 * class represents one of those positions.</p>
 *
 * <p><b>Why the time is an object rather than a string.</b> Two appointments
 * have to be ordered by time, and text such as "9:30" and "10:00" does not
 * compare correctly as text. Storing the start as a whole number of minutes
 * makes {@link #compareTo(TimeSlot)} a single numeric comparison, and the
 * readable form is produced only when the slot is printed.</p>
 *
 * <p><b>Why the schedule is generated, not listed.</b> The three constants
 * below describe the whole timetable. Changing the opening time, the closing
 * time or the length of an appointment is a change to those constants, and
 * every part of the system that asks this class for slots follows automatically.
 * A hand-written list of slots would have to be edited in every place it was
 * copied to.</p>
 *
 * @author Ziliang Lu (25259146)
 * @version 1.0
 */
public class TimeSlot implements Comparable<TimeSlot> {

    /** The hour the clinic opens, in the 24-hour clock. */
    public static final int CLINIC_OPENING_HOUR = 8;

    /** The hour the clinic closes, in the 24-hour clock. */
    public static final int CLINIC_CLOSING_HOUR = 16;

    /** How long one appointment lasts, in minutes. */
    public static final int SLOT_LENGTH_IN_MINUTES = 30;

    /**
     * How many minutes after midnight this slot starts.
     *
     * <p>The value is always one that the schedule generator produced, because
     * the constructor is private: a caller cannot invent 11:17 and put it into
     * the system.</p>
     */
    private int startMinuteOfDay;

    /**
     * Creates a slot.
     *
     * <p>The constructor is private so that slots can only be obtained from
     * {@link #createDailySchedule()} or one of the finder methods. That is what
     * guarantees a slot is always on the timetable.</p>
     *
     * @param startMinuteOfDay the minute of the day this slot starts at
     */
    private TimeSlot(int startMinuteOfDay) {
        this.startMinuteOfDay = startMinuteOfDay;
    }

    /**
     * Builds the timetable for one day.
     *
     * <p>A new list is built on every call rather than a single shared list
     * being returned, because a caller that edited a shared list would change
     * the timetable for everything else in the program.</p>
     *
     * @return every slot in the day, earliest first
     */
    public static ArrayList<TimeSlot> createDailySchedule() {
        ArrayList<TimeSlot> schedule = new ArrayList<TimeSlot>();
        int startMinute = CLINIC_OPENING_HOUR * 60;
        int closingMinute = CLINIC_CLOSING_HOUR * 60;

        // The loop stops as soon as a slot would run past closing time, so the
        // last slot of the day starts early enough to finish before 16:00.
        while (startMinute + SLOT_LENGTH_IN_MINUTES <= closingMinute) {
            schedule.add(new TimeSlot(startMinute));
            startMinute = startMinute + SLOT_LENGTH_IN_MINUTES;
        }

        return schedule;
    }

    /**
     * Finds the slot that starts at the given time.
     *
     * @param hour the hour, on the 24-hour clock, for example 9
     * @param minute the minute, for example 30
     * @return the matching slot, or {@code null} when that time is not a slot
     *         in the timetable
     */
    public static TimeSlot findByHourAndMinute(int hour, int minute) {
        int wantedMinute = hour * 60 + minute;
        ArrayList<TimeSlot> schedule = createDailySchedule();

        for (int index = 0; index < schedule.size(); index++) {
            TimeSlot slot = schedule.get(index);
            if (slot.startMinuteOfDay == wantedMinute) {
                return slot;
            }
        }
        return null;
    }

    /**
     * Finds the slot that starts at the given time, written as "HH:mm".
     *
     * <p>The text is read by hand rather than with a parsing library, and a
     * malformed value simply returns {@code null}: an invalid time is an
     * ordinary input mistake that the caller reports as a warning, not a
     * situation that stops the program.</p>
     *
     * @param time the time to look for, for example "08:30"
     * @return the matching slot, or {@code null} when the text is not a time on
     *         the timetable
     */
    public static TimeSlot findByLabel(String time) {
        if (time == null) {
            return null;
        }

        String text = time.trim();
        int colon = text.indexOf(':');
        if (colon < 0) {
            return null;
        }

        String hourPart = text.substring(0, colon);
        String minutePart = text.substring(colon + 1);
        if (!isDigitsOnly(hourPart) || !isDigitsOnly(minutePart)) {
            return null;
        }

        int hour = Integer.parseInt(hourPart);
        int minute = Integer.parseInt(minutePart);

        // Hours still have to be in range: "99:00" is made of digits but it is
        // not a time of day, and converting it would produce a slot that the
        // timetable can never hold.
        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            return null;
        }

        return findByHourAndMinute(hour, minute);
    }

    /**
     * Tells whether the text contains digits and nothing else.
     *
     * @param text the text to check
     * @return {@code true} when every character is a digit and at least one
     *         character is present
     */
    private static boolean isDigitsOnly(String text) {
        if (text.length() == 0) {
            return false;
        }
        for (int index = 0; index < text.length(); index++) {
            char character = text.charAt(index);
            if (character < '0' || character > '9') {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns how many minutes after midnight this slot starts.
     *
     * @return the start of the slot, in minutes after midnight
     */
    public int getStartMinuteOfDay() {
        return startMinuteOfDay;
    }

    /**
     * Returns how many minutes after midnight this slot ends.
     *
     * @return the end of the slot, in minutes after midnight
     */
    public int getEndMinuteOfDay() {
        return startMinuteOfDay + SLOT_LENGTH_IN_MINUTES;
    }

    /**
     * Returns the start of the slot as a readable time.
     *
     * <p>Each part is padded to two digits so that a column of times lines up,
     * which is why "08:30" is printed instead of "8:30".</p>
     *
     * @return the start of the slot, for example "08:30"
     */
    public String getLabel() {
        int hour = startMinuteOfDay / 60;
        int minute = startMinuteOfDay % 60;

        String hourText = "" + hour;
        if (hour < 10) {
            hourText = "0" + hourText;
        }

        String minuteText = "" + minute;
        if (minute < 10) {
            minuteText = "0" + minuteText;
        }

        return hourText + ":" + minuteText;
    }

    /**
     * Orders two slots from earliest to latest.
     *
     * <p>Because the start is stored as a number of minutes, the comparison is
     * a numeric one, which is what makes sorting a whole day of appointments
     * correct without any text parsing.</p>
     *
     * @param other the slot to compare against
     * @return a negative number when this slot is earlier, zero when the slots
     *         are the same, and a positive number when this slot is later
     */
    @Override
    public int compareTo(TimeSlot other) {
        if (startMinuteOfDay < other.startMinuteOfDay) {
            return -1;
        }
        if (startMinuteOfDay > other.startMinuteOfDay) {
            return 1;
        }
        return 0;
    }

    /**
     * Tells whether two slots are the same position on the timetable.
     *
     * @param other the object to compare with
     * @return {@code true} when both objects describe the same slot
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TimeSlot)) {
            return false;
        }
        TimeSlot that = (TimeSlot) other;
        return startMinuteOfDay == that.startMinuteOfDay;
    }

    /**
     * Returns a hash code consistent with {@link #equals(Object)}.
     *
     * @return the start minute, which is unique within one day
     */
    @Override
    public int hashCode() {
        return startMinuteOfDay;
    }

    /**
     * Returns the readable form of the slot.
     *
     * @return the start of the slot, for example "08:30"
     */
    @Override
    public String toString() {
        return getLabel();
    }
}
