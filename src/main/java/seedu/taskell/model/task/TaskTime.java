package seedu.taskell.model.task;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.taskell.commons.exceptions.IllegalValueException;


/**
 * Represents a Task's taskTime in the task manager.
 */
public class TaskTime {

    public static final String ZERO_MINUTE = "00";
    public static final String NOON = "12:00PM";
    public static final String MIDNIGHT = "12:00AM";
    
    public static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("h:mma");
    
    public static final String DEFAULT_START_TIME = LocalTime.now().format(dtf);
    public static final String DEFAULT_END_TIME = "11:59PM";

    public static final Pattern TASK_TIME_ARGS_FORMAT = Pattern
            .compile("(?<hour>(1[0-2]|[1-9]))" + "(.|-|:)(?<minute>([0-5][0-9]))" + "(?<antePost>(am|pm|AM|PM|Am|Pm|aM|pM))");
    public static final Pattern TASK_TIME_HOUR_ONLY_FORMAT = Pattern
            .compile("(?<hour>(1[0-2]|[1-9]))" + "(?<antePost>(am|pm|AM|PM|Am|Pm|aM|pM))");
    final static String FULL_TIME_REGEX = "^(1[0-2]|[1-9])(.|-|:)([0-5][0-9])(am|pm|AM|PM|Am|Pm|aM|pM)$";
    final static String HOUR_ONLY_TIME_REGEX = "^(1[0-2]|[1-9])(am|pm|AM|PM|Am|Pm|aM|pM)$";
    
    public static final String MESSAGE_TASK_TIME_CONSTRAINTS =
            "Time should be in 12hour clock format."
            + "\nHour and Minute are separated by '.' or ':'"
            + "\nEg. 9.30am or 12:50pm";

    public String taskTime; //Standard format

    public TaskTime(String time) throws IllegalValueException {
        if (isValidTime(time)) {
            setTime(time);
        } else {
            throw new IllegalValueException(MESSAGE_TASK_TIME_CONSTRAINTS);
        }
    }

    public static boolean isValidTime(String time) {
        assert time != null;
        if (time.isEmpty()) {
            return false;
        }

        if (time.matches(HOUR_ONLY_TIME_REGEX) || time.matches(FULL_TIME_REGEX)) {
            return true;
        }

        return false;
    }

    public void setTime(String time) throws IllegalValueException {
        final Matcher matcherFullArg = TASK_TIME_ARGS_FORMAT.matcher(time.trim());
        final Matcher matcherHourOnly = TASK_TIME_HOUR_ONLY_FORMAT.matcher(time.trim());
        if (matcherFullArg.matches()) {
            this.taskTime = setTime(matcherFullArg.group("hour"), matcherFullArg.group("minute"), matcherFullArg.group("antePost"));
        } else if (matcherHourOnly.matches()) {
            this.taskTime = setTime(matcherFullArg.group("hour"), ZERO_MINUTE, matcherFullArg.group("antePost"));
        } else {
            throw new IllegalValueException(MESSAGE_TASK_TIME_CONSTRAINTS);
        }
    }
    
    public String setTime(String hour, String minute, String antePost) {
        this.taskTime =  hour + ":" + minute + antePost.toUpperCase();
        return taskTime;
    }

    public static String getTimeNow() {
        LocalTime currTime = LocalTime.now();
        return LocalTime.of(currTime.getHour(), currTime.getMinute()).format(dtf);
    }
    
    public String getHour() {
        assert taskTime != null;
        
        final Matcher matcherFullArg = TASK_TIME_ARGS_FORMAT.matcher(taskTime.trim());
        if (matcherFullArg.matches()) {
            return matcherFullArg.group("hour");
        }
        
        return "";
    }
    
    public String getMinute() {
        assert taskTime != null;
        
        final Matcher matcherFullArg = TASK_TIME_ARGS_FORMAT.matcher(taskTime.trim());
        if (matcherFullArg.matches()) {
            return matcherFullArg.group("minute");
        }
        
        return "";
    }
    
    public String getAntePost() {
        assert taskTime != null;
        
        final Matcher matcherFullArg = TASK_TIME_ARGS_FORMAT.matcher(taskTime.trim());
        if (matcherFullArg.matches()) {
            return matcherFullArg.group("antePost");
        }
        
        return "";
    }
    
    @Override
    public String toString() {
        return taskTime;
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskTime // instanceof handles nulls
                && this.taskTime.equals(((TaskTime) other).taskTime)); // state check
    }
    
    @Override
    public int hashCode() {
        return (taskTime).hashCode();
    }
}
