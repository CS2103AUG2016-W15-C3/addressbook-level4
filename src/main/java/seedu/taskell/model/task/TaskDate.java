package seedu.taskell.model.task;

import java.util.StringTokenizer;
import java.util.Locale;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;

/**
 * Represents a Task's date in the task manager.
 * Guarantees: is valid as declared in {@link #isValidDate(String)}
 */
public class TaskDate {
    public static final int JANUARY = 1;
    public static final int FEBRUARY = 2;
    public static final int MARCH = 3;
    public static final int APRIL = 4;
    public static final int MAY = 5;
    public static final int JUNE = 6;
    public static final int JULY = 7;
    public static final int AUGUST = 8;
    public static final int SEPTEMBER = 9;
    public static final int OCTOBER = 10;
    public static final int NOVEMBER = 11;
    public static final int DECEMBER = 12;

    public static final int NOT_A_VALID_MONTH = 0;
    public static final int NOT_A_VALID_DAY_OF_THE_WEEK = 0;

    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;
    public static final int SUNDAY = 7;

    public static final int TODAY = 1;
    public static final int TOMORROW = 2;
    public static final int ONLY_CONTAIN_DAY_NAME_IN_WEEK = 3;
    public static final int ONLY_CONTAIN_MONTH = 4;
    public static final int ONLY_CONTAIN_DAY_AND_MONTH = 5;
    public static final int ONLY_CONTAIN_MONTH_AND_YEAR = 6;

    public static final int NUM_DAYS_IN_A_WEEK = 7;
    public static final int NUM_MONTHS_IN_A_YEAR = 12;

    public static final String FIRST_DAY_OF_THE_MONTH = "1";

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy");
    SimpleDateFormat sdf = new SimpleDateFormat("d M y");

    String day;
    String month;
    String year;
    String dayNameInWeek;

    /**
     * Initialize the different fields
     */
    public TaskDate(String fullDate) {
        getPartOfDate(fullDate);
    }

    /**
     * Initialize the different fields given the different permutation of dates
     * @param string
     * @param formatOfDate
     */
    public TaskDate(String string, int formatOfDate) {
        LocalDate today = LocalDate.now();

        if (formatOfDate == TODAY) {
            getPartOfDate(today.getDayOfMonth() + "", today.getMonthValue() + "", today.getYear() + "");
        } else if (formatOfDate == TOMORROW) {
            LocalDate tomorrow = today.plusDays(1);
            getPartOfDate(tomorrow.getDayOfMonth() + "", tomorrow.getMonthValue() + "", tomorrow.getYear() + "");
        } else if (formatOfDate == ONLY_CONTAIN_DAY_NAME_IN_WEEK) {
            int dateToSet = convertDayOfWeekIntoInteger(string);
            String todayDayNameInWeek = today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
            int todayDayInWeek = convertDayOfWeekIntoInteger(todayDayNameInWeek);
            int daysToAdd = dateToSet - todayDayInWeek;
            if (daysToAdd < 0) {
                daysToAdd += NUM_DAYS_IN_A_WEEK;
            }
            LocalDate finalDate = today.plusDays(daysToAdd);
            getPartOfDate(finalDate.getDayOfMonth() + "", finalDate.getMonthValue() + "", finalDate.getYear() + "");
        } else if (formatOfDate == ONLY_CONTAIN_MONTH) {
            getPartOfDate(FIRST_DAY_OF_THE_MONTH, string, getThisYear());
        } else if (formatOfDate == ONLY_CONTAIN_DAY_AND_MONTH) {
            StringTokenizer st = new StringTokenizer(string, " .-");
            day = st.nextToken();
            month = convertMonthIntoInteger(st.nextToken()) + "";
            getPartOfDate(day, month, getThisYear());
        } else if (formatOfDate == ONLY_CONTAIN_MONTH_AND_YEAR) {
            StringTokenizer st = new StringTokenizer(string, " .-");
            month = convertMonthIntoInteger(st.nextToken()) + "";
            year = st.nextToken();
            getPartOfDate(FIRST_DAY_OF_THE_MONTH, month, year);
        }
    }

    public void getPartOfDate(String dateToConvert) {
        StringTokenizer st = new StringTokenizer(dateToConvert, " .-");
        String[] tokenArr = new String[3];
        int i = 0;
        while (st.hasMoreTokens()) {
            tokenArr[i] = st.nextToken();
            i++;
        }

        String day = tokenArr[0];
        String month = convertMonthIntoInteger(tokenArr[1]) + "";
        String year = tokenArr[2];

        getPartOfDate(day, month, year);
    }

    private void getPartOfDate(String day, String month, String year) {
        LocalDate localDate = LocalDate.of(Integer.valueOf(year), convertMonthIntoInteger(month), Integer.valueOf(day));
        dayNameInWeek = localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);

        this.day = day;
        this.year = year;

        month = localDate.getMonth().getDisplayName(TextStyle.FULL, Locale.US).toLowerCase();
        this.month = month.substring(0, 1).toUpperCase() + month.substring(1);
    }

    public static boolean isValidDate(String dateToValidate) {
        if (dateToValidate == null) {
            return false;
        }

        return isValidFullDate(dateToValidate) || isValidMonthAndYear(dateToValidate)
                || isValidDayAndMonth(dateToValidate) || isValidMonth(dateToValidate) || isValidToday(dateToValidate)
                || isValidTomorrow(dateToValidate) || isValidDayOfWeek(dateToValidate);
    }

    public static boolean isValidDayOfWeek(String dateToValidate) {
        if (convertDayOfWeekIntoInteger(dateToValidate) == NOT_A_VALID_DAY_OF_THE_WEEK) {
            return false;
        }
        return true;
    }

    public static boolean isValidMonthAndYear(String dateToValidate) {
        if (isValidFormat(dateToValidate, "MMM y") || isValidFormat(dateToValidate, "MMM-y")
                || isValidFormat(dateToValidate, "MMM.y")) {
            return true;
        }
        return false;
    }

    public static boolean isValidDayAndMonth(String dateToValidate) {
        if (isValidFormat(dateToValidate, "d MMM") || isValidFormat(dateToValidate, "d-MMM")
                || isValidFormat(dateToValidate, "d.MMM")) {
            return true;
        }
        return false;
    }

    public static boolean isValidFullDate(String dateToValidate) {
        if (isValidFormat(dateToValidate, "d M y") || isValidFormat(dateToValidate, "d MMM y")
                || isValidFormat(dateToValidate, "d-M-y") || isValidFormat(dateToValidate, "d-MMM-y")
                || isValidFormat(dateToValidate, "d.M.y") || isValidFormat(dateToValidate, "d.MMM.y")
                || isValidFormat(dateToValidate, "d.M-y") || isValidFormat(dateToValidate, "d.MMM-y")
                || isValidFormat(dateToValidate, "d-M.y") || isValidFormat(dateToValidate, "d-MMM.y")) {
            return true;
        }
        return false;
    }

    public static boolean isValidFormat(String dateToValidate, String acceptedFormat) {
        if (dateToValidate == null) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(acceptedFormat);
        sdf.setLenient(false);

        try {
            // if not valid, it will throw ParseException
            Date date = sdf.parse(dateToValidate);
            System.out.println(date);
        } catch (ParseException e) {
            return false;
        }

        return true;
    }

    public static boolean isValidToday(String dateToValidate) {
        dateToValidate.toLowerCase();
        switch (dateToValidate) {
        case "today":
            // Fallthrough
        case "tdy":
            return true;
        default:
            return false;
        }
    }

    public static boolean isValidTomorrow(String dateToValidate) {
        dateToValidate.toLowerCase();
        switch (dateToValidate) {
        case "tomorrow":
            // Fallthrough
        case "tmr":
            return true;
        default:
            return false;
        }
    }

    public static boolean isValidMonth(String month) {
        if (convertMonthIntoInteger(month) == NOT_A_VALID_MONTH) {
            return false;
        } else {
            return true;
        }
    }

    private static int convertDayOfWeekIntoInteger(String day) {
        day = day.toLowerCase();

        switch (day) {
        case "mon":
            // Fallthrough
        case "monday":
            return MONDAY;
        case "tue":
            // Fallthrough
        case "tues":
            // Fallthrough
        case "tuesday":
            return TUESDAY;
        case "wed":
            // Fallthrough
        case "wednesday":
            return WEDNESDAY;
        case "thu":
            // Fallthrough
        case "thur":
            // Fallthrough
        case "thurs":
            // Fallthrough
        case "thursday":
            return THURSDAY;
        case "fri":
            // Fallthrough
        case "friday":
            return FRIDAY;
        case "sat":
            // Fallthrough
        case "saturday":
            return SATURDAY;
        case "sun":
            // Fallthrough
        case "sunday":
            return SUNDAY;
        default:
            return NOT_A_VALID_DAY_OF_THE_WEEK;
        }
    }

    private static int convertMonthIntoInteger(String month) {
        if (!month.matches("[\\d]+")) {
            month.toLowerCase();
        }

        switch (month) {
        case JANUARY + "":
            // Fallthrough
        case "jan":
            // Fallthrough
        case "january":
            return JANUARY;
        case FEBRUARY + "":
            // Fallthrough
        case "feb":
            // Fallthrough
        case "february":
            return FEBRUARY;
        case MARCH + "":
            // Fallthrough
        case "mar":
            // Fallthrough
        case "march":
            return MARCH;
        case APRIL + "":
            // Fallthrough
        case "apr":
            // Fallthrough
        case "april":
            return APRIL;
        case MAY + "":
            // Fallthrough
        case "may":
            return MAY;
        case JUNE + "":
            // Fallthrough
        case "jun":
            // Fallthrough
        case "june":
            return JUNE;
        case JULY + "":
            // Fallthrough
        case "jul":
            // Fallthrough
        case "july":
            return JULY;
        case AUGUST + "":
            // Fallthrough
        case "aug":
            // Fallthrough
        case "august":
            return AUGUST;
        case SEPTEMBER + "":
            // Fallthrough
        case "sep":
            // Fallthrough
        case "september":
            return SEPTEMBER;
        case OCTOBER + "":
            // Fallthrough
        case "oct":
            // Fallthrough
        case "october":
            return OCTOBER;
        case NOVEMBER + "":
            // Fallthrough
        case "nov":
            // Fallthrough
        case "november":
            return NOVEMBER;
        case DECEMBER + "":
            // Fallthrough
        case "dec":
            // Fallthrough
        case "december":
            return DECEMBER;
        default:
            return NOT_A_VALID_MONTH;
        }
    }

    public static String getTodayDate() {
        return LocalDate.now().format(dtf);
    }

    public static String getTomorrowDate() {
        return LocalDate.now().plusDays(1).format(dtf);
    }

    public static String getThisYear() {
        return LocalDate.now().getYear() + "";
    }

    @Override
    public String toString() {
        return dayNameInWeek + ", " + day + " " + month + " " + year;
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskDate // instanceof handles nulls
                && this.day.equals(((TaskDate) other).day)
                && this.month.equals(((TaskDate) other).month)
                && this.year.equals(((TaskDate) other).year));
    }

    @Override
    public int hashCode() {
        return (day + month + year + dayNameInWeek).hashCode();
    }
}
