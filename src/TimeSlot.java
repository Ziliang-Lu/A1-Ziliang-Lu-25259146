import java.util.ArrayList;

/**
 * Part 4: one bookable slot in the clinic timetable.
 *
 * Fields: startMinuteOfDay.
 * Methods: createDailySchedule(), findByHourAndMinute(), findByLabel(),
 *          getStartMinuteOfDay(), getEndMinuteOfDay(), getLabel(),
 *          compareTo(), equals(), hashCode(), toString().
 *
 * The three constants below describe the whole timetable, so changing the
 * opening hour, the closing hour or the slot length changes every slot at once.
 */
public class TimeSlot implements Comparable<TimeSlot> {

    public static final int CLINIC_OPENING_HOUR = 8;      // 24-hour clock
    public static final int CLINIC_CLOSING_HOUR = 16;     // 24-hour clock
    public static final int SLOT_LENGTH_IN_MINUTES = 30;  // one appointment

    private int startMinuteOfDay;   // minutes after midnight

    /**
     * Creates a slot.
     *
     * The constructor is private, so a slot can only come from the timetable
     * and a time such as 11:17 can never enter the system.
     *
     * @param startMinuteOfDay the minute of the day this slot starts at
     */
    private TimeSlot(int startMinuteOfDay) {
        this.startMinuteOfDay = startMinuteOfDay;
    }

    /**
     * Builds the timetable for one day.
     *
     * @return every slot in the day, earliest first
     */
    public static ArrayList<TimeSlot> createDailySchedule() {
        ArrayList<TimeSlot> schedule = new ArrayList<TimeSlot>();
        int startMinute = CLINIC_OPENING_HOUR * 60;
        int closingMinute = CLINIC_CLOSING_HOUR * 60;

        // stops before a slot would run past closing time
        while (startMinute + SLOT_LENGTH_IN_MINUTES <= closingMinute) {
            schedule.add(new TimeSlot(startMinute));
            startMinute = startMinute + SLOT_LENGTH_IN_MINUTES;
        }

        return schedule;
    }

    /**
     * Finds the slot that starts at the given time.
     *
     * @param hour the hour, for example 9
     * @param minute the minute, for example 30
     * @return the matching slot, or null when the time is not in the timetable
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
     * @param time the time to look for, for example "08:30"
     * @return the matching slot, or null when the text is not a clinic time
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

        // checks the range as well, because "99:00" is made of digits but is
        // not a time of day
        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            return null;
        }

        return findByHourAndMinute(hour, minute);
    }

    /**
     * Tells whether the text contains digits and nothing else.
     *
     * @param text the text to check
     * @return true when every character is a digit and the text is not empty
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
     * @param other the slot to compare against
     * @return a negative number when this slot is earlier, zero when both are
     *         the same, and a positive number when this slot is later
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
     * @return true when both objects describe the same slot
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
     * Returns a hash code that agrees with equals().
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
