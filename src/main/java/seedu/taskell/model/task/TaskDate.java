package seedu.taskell.model.task;

import java.util.StringTokenizer;

import seedu.taskell.commons.exceptions.IllegalValueException;

import java.util.Locale;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
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
    public static final int FULL_DATE_DISPLAY = 7;              //NAME_OF_DAY_IN_WEEK, DAY MONTH YEAR

    public static final int NUM_DAYS_IN_A_WEEK = 7;
    public static final int NUM_MONTHS_IN_A_YEAR = 12;

    public static final String FIRST_DAY_OF_THE_MONTH = "1";
    
    public static final String DATE_DELIMITER = " .-/";
    
    public static final int LENGTH_OF_KEYWORD_BY = 2+1;

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy");
    SimpleDateFormat sdf = new SimpleDateFormat("d M y");
    
    public static final String MESSAGE_TASK_DATE_CONSTRAINTS =
            "Task dates should be separated by '-' or '.' or '/'"
            + "\nSpelling of month should be in full or 3-letters";

    public String day;
    public String month;
    public String year;
    public String dayNameInWeek;
    public String taskDateInputByUser;
    public String taskDateStandardFormat;
    public String taskDateForDisplay;
    
    /**
     * Initialize the different fields given date in the format of 
     * DAY-MONTH-YEAR, separated by DATE_DELIMITER
     * @throws IllegalValueException 
     */
    public TaskDate(String fullDate) throws IllegalValueException {
        assert fullDate != null;
        if (!isValidDate(fullDate)) {
            throw new IllegalValueException(MESSAGE_TASK_DATE_CONSTRAINTS);
        }
        taskDateInputByUser = fullDate;
        getPartOfDate(fullDate);
    }
    
    /**
     * Initialize the different fields given the different permutation of dates
     * @param string
     * @param formatOfDate
     * @throws IllegalValueException 
     */
    public TaskDate(String string, int formatOfDate) throws IllegalValueException {
        assert string != null;
        
        taskDateInputByUser = string;
        
        if (formatOfDate == FULL_DATE_DISPLAY) {
            StringTokenizer st = new StringTokenizer(string, " ,");
            dayNameInWeek = st.nextToken();
            day = st.nextToken();
            month = st.nextToken();
            year = st.nextToken();
            taskDateForDisplay = string;
            string = day.trim() + "-" + convertMonthIntoInteger(month.trim()) + "-" + year.trim();
            taskDateStandardFormat = string;
        }
        
        if (!isValidDate(string)) {
            throw new IllegalValueException(MESSAGE_TASK_DATE_CONSTRAINTS);
        }
        
        LocalDate today = LocalDate.now();
        
        try {
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
                int month = convertMonthIntoInteger(string);
                getPartOfDate(FIRST_DAY_OF_THE_MONTH, month+"", getThisYear());
            } else if (formatOfDate == ONLY_CONTAIN_DAY_AND_MONTH) {
                StringTokenizer st = new StringTokenizer(string, DATE_DELIMITER);
                day = st.nextToken();
                month = convertMonthIntoInteger(st.nextToken()) + "";
                getPartOfDate(day, month, getThisYear());
            } else if (formatOfDate == ONLY_CONTAIN_MONTH_AND_YEAR) {
                StringTokenizer st = new StringTokenizer(string, DATE_DELIMITER);
                month = convertMonthIntoInteger(st.nextToken()) + "";
                year = st.nextToken();
                getPartOfDate(FIRST_DAY_OF_THE_MONTH, month, year);
            }
        } catch (DateTimeException dte) {
            throw new IllegalValueException(MESSAGE_TASK_DATE_CONSTRAINTS);
        }
    }

    /**
     * Extract the different fields from date having the format of
     * DAY-MONTH-YEAR, separated by DATE_DELIMITER
     * @throws DateTimeException
     */
    public void getPartOfDate(String dateToConvert) throws DateTimeException {
        StringTokenizer st = new StringTokenizer(dateToConvert, DATE_DELIMITER);
        String[] tokenArr = new String[3];
        int i = 0;
        while (st.hasMoreTokens()) {
            tokenArr[i] = st.nextToken();
            i++;
        }

        String day = tokenArr[0];
        String month = convertMonthIntoInteger(tokenArr[1]) + "";
        String year = tokenArr[2];

        try {
        getPartOfDate(day, month, year);
        } catch (DateTimeException dte) {
            throw dte;
        }
    }

    /**
     * Extract the different fields of a given valid date
     * @throws DateTimeException
     */
    private void getPartOfDate(String day, String month, String year) throws DateTimeException {
        assert(day!=null);
        assert(month!=null);
        assert(year!=null);
        
        try {
            LocalDate localDate = LocalDate.of(Integer.valueOf(year), convertMonthIntoInteger(month), Integer.valueOf(day));
            this.dayNameInWeek = localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);

            this.day = day.trim();
            this.year = year.trim();

            month = localDate.getMonth().getDisplayName(TextStyle.FULL, Locale.US).toLowerCase();
            this.month = month.substring(0, 1).toUpperCase() + month.substring(1).trim();
            this.taskDateForDisplay = dayNameInWeek + ", " + this.day + " " + this.month + " " + this.year;
            this.taskDateStandardFormat = convertToStandardFormat();
        } catch (DateTimeException dte) {
            throw dte;
        }
    }
    
    /**
     * Convert this TaskDate to te format of
     * DAY_MONTH-YEAR
     */
    public String convertToStandardFormat() {
        return day + "-" + convertMonthIntoInteger(month) + "-" + year;
    }

    /**
     * Returns true if a given string is a valid task date.
     */
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
                || isValidFormat(dateToValidate, "MMM.y")
                || isValidFormat(dateToValidate, "MMM/y")) {
            return true;
        }
        return false;
    }

    public static boolean isValidDayAndMonth(String dateToValidate) {
        if (isValidFormat(dateToValidate, "d MMM") || isValidFormat(dateToValidate, "d-MMM")
                || isValidFormat(dateToValidate, "d.MMM")
                || isValidFormat(dateToValidate, "d/MMM")) {
            return true;
        }
        return false;
    }

    public static boolean isValidFullDate(String dateToValidate) {
        if (isValidFormat(dateToValidate, "d M y") || isValidFormat(dateToValidate, "d MMM y")
                || isValidFormat(dateToValidate, "d-M-y") || isValidFormat(dateToValidate, "d-MMM-y")
                || isValidFormat(dateToValidate, "d.M.y") || isValidFormat(dateToValidate, "d.MMM.y")
                || isValidFormat(dateToValidate, "d.M-y") || isValidFormat(dateToValidate, "d.MMM-y")
                || isValidFormat(dateToValidate, "d-M.y") || isValidFormat(dateToValidate, "d-MMM.y")
                || isValidFormat(dateToValidate, "d/M/y") || isValidFormat(dateToValidate, "d/MMM/y")
                || isValidFormat(dateToValidate, "d-M/y") || isValidFormat(dateToValidate, "d-MMM/y")
                || isValidFormat(dateToValidate, "d/M-y") || isValidFormat(dateToValidate, "d/MMM-y")
                || isValidFormat(dateToValidate, "d.M/y") || isValidFormat(dateToValidate, "d.MMM/y")
                || isValidFormat(dateToValidate, "d/M.y") || isValidFormat(dateToValidate, "d/MMM.y")) {
            return true;
        }
        return false;
    }

    /**
     * Returns true if a given string has a valid format supported by SimpleDateFormat.
     */
    public static boolean isValidFormat(String dateToValidate, String acceptedFormat) {
        if (dateToValidate == null) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(acceptedFormat);
        sdf.setLenient(false);

        try {
            // if not valid, it will throw ParseException
            Date date = sdf.parse(dateToValidate);
        } catch (ParseException e) {
            return false;
        }

        return true;
    }

    public static boolean isValidToday(String dateToValidate) {
        assert (dateToValidate != null);
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
        assert (dateToValidate != null);
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

    /**
     * Returns an integer representing the day in a week
     */
    private static int convertDayOfWeekIntoInteger(String day) {
        assert (day != null);
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

    /**
     * Returns an integer representing the month of a year.
     */
    private static int convertMonthIntoInteger(String month) {
        assert (month!= null);
        if (Character.isLetter(month.charAt(0))) {
            month = month.toLowerCase();
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
        case "sept":
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

    /**
     * Get today's date in the format of
     * NAME_OF_DAY_IN_WEEK, DAY MONTH YEAR
     */
    public static String getTodayDate() {
        return LocalDate.now().format(dtf);
    }

    /**
     * Get tomorrow's date in the format of
     * NAME_OF_DAY_IN_WEEK, DAY MONTH YEAR
     */
    public static String getTomorrowDate() {
        return LocalDate.now().plusDays(1).format(dtf);
    }

    /**
     * Returns a string representing the integer value of this year
     */
    public static String getThisYear() {
        return LocalDate.now().getYear() + "";
    }

    /**
     * Returns a string with the format of
     * NAME_OF_DAY_IN_WEEK, DAY MONTH YEAR
     */
    @Override
    public String toString() {
        return taskDateForDisplay;
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskDate // instanceof handles nulls
                && this.taskDateStandardFormat.equals(((TaskDate)other).taskDateStandardFormat));
    }

    @Override
    public int hashCode() {
        return taskDateStandardFormat.hashCode();
    }
}