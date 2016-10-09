package seedu.taskell.model.task;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


/**
 * Represents a Task's time in the task manager.
 * is valid as declared in {@link #isValidTime(String)}
 */
public class TaskTime {
    private static final String ZERO_MINUTE = "00";

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("h:mma");

    public static final Pattern TASK_TIME_ARGS_FORMAT = Pattern
            .compile("(?<hour>(1[0-2]|[1-9]))" + "(.|-|:)(?<minute>([0-5][0-9]))" + "(?<antePost>(am|pm))");
    public static final Pattern TASK_TIME_HOUR_ONLY_FORMAT = Pattern
            .compile("(?<hour>(1[0-2]|[1-9]))" + "(?<antePost>(am|pm))");
    final static String FULL_TIME_REGEX = "^(1[0-2]|[1-9])(.|-|:)([0-5][0-9])(am|pm)$";
    final static String HOUR_ONLY_TIME_REGEX = "^(1[0-2]|[1-9])(am|pm)$";

    String hour;
    String minute;
    String antePost;    //AM or PM post-fix

    public TaskTime(String time) {
        getPartOfTime(time);
    }

    public static boolean isValidTime(String time) {
        if (time == null) {
            return false;
        }

        if (time.matches(HOUR_ONLY_TIME_REGEX) || time.matches(FULL_TIME_REGEX)) {
            return true;
        }

        return false;
    }

    public void getPartOfTime(String time) {
        final Matcher matcherFullArg = TASK_TIME_ARGS_FORMAT.matcher(time.trim());
        final Matcher matcherHourOnly = TASK_TIME_HOUR_ONLY_FORMAT.matcher(time.trim());
        if (matcherFullArg.matches()) {
            this.hour = matcherFullArg.group("hour");
            this.minute = matcherFullArg.group("minute");
            this.antePost = matcherFullArg.group("antePost");
            System.out.println(this.antePost);
        } else if (matcherHourOnly.matches()) {
            this.hour = matcherHourOnly.group("hour");
            this.minute = ZERO_MINUTE;
            this.antePost = matcherHourOnly.group("antePost");
        }
    }

    public static String getTimeNow() {
        LocalTime currTime = LocalTime.now();
        return LocalTime.of(currTime.getHour(), currTime.getMinute()).format(dtf);
    }

    @Override
    public String toString() {
        return hour + ":" + minute + antePost.toUpperCase();
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskTime // instanceof handles nulls
                && this.hour.equals(((TaskTime) other).hour)
                && this.minute.equals(((TaskTime) other).minute)
                && this.antePost.equals(((TaskTime) other).antePost)); // state check
    }
    
    @Override
    public int hashCode() {
        return (hour + minute + antePost).hashCode();
    }

}
