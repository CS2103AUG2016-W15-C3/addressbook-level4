package seedu.taskell.model.task;

import seedu.taskell.commons.exceptions.IllegalValueException;
import seedu.taskell.model.tag.UniqueTagList;

//@@author A0139257X
/**
 * Represents an Event task in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class EventTask extends Task {
    public static final String MESSAGE_EVENT_CONSTRAINTS = "Start date and time must be before end date and time"
            + "\nAll date and time should not before current time";
    
    private String[] taskComponentArray;
    private boolean[] hasTaskComponentArray;
    
    public EventTask(String[] taskComponentArray, boolean[] hasTaskComponentArray, UniqueTagList tags) throws IllegalValueException {
        
        this.taskComponentArray = taskComponentArray;
        this.hasTaskComponentArray = hasTaskComponentArray;
        this.tags = tags;
        
        setTaskComponents();
    }
    
    private void setTaskComponents() throws IllegalValueException {
        TaskDate eventStartDate = new TaskDate(this.taskComponentArray[START_DATE]);
        TaskTime eventEndTime = new TaskTime(this.taskComponentArray[END_TIME]);
        RecurringType eventRecurringType = new RecurringType(this.taskComponentArray[RECURRING_TYPE]);
        
        TaskTime eventStartTime = setStartTime(eventStartDate);
        eventStartDate = adjustStartDate(eventStartDate, eventStartTime);
        
        TaskDate eventEndDate = setEndDate(eventStartDate, eventStartTime, eventEndTime);
        
        if (!isValidEventDuration(eventStartDate, eventEndDate, eventStartTime, eventEndTime)) {
            throw new IllegalValueException(MESSAGE_EVENT_CONSTRAINTS);
        }
        
        if(!isValidRecurringEvent(eventStartDate, eventEndDate, eventRecurringType)){
            throw new IllegalValueException(RecurringType.MESSAGE_INVALID_RECURRING_DURATION);
        }
        
        this.description = new Description(taskComponentArray[DESCRIPTION]);
        this.taskType = EVENT_TASK;
        this.startDate = eventStartDate;
        this.endDate = eventEndDate;
        this.startTime = eventStartTime;
        this.endTime = eventEndTime;
        this.taskPriority = new TaskPriority(taskComponentArray[TASK_PRIORITY]);
        this.recurringType = eventRecurringType;
        this.taskStatus = new TaskStatus(TaskStatus.INCOMPLETE);
    }
    
    private TaskDate adjustStartDate(TaskDate eventStartDate, TaskTime eventStartTime) throws IllegalValueException {
        if (TaskDate.isValidFullDate(taskComponentArray[START_DATE]) 
                && eventStartDate.isBefore(TaskDate.getTodayDate())) {
            throw new IllegalValueException(MESSAGE_EVENT_CONSTRAINTS);
        } else if (TaskDate.isValidMonthAndYear(taskComponentArray[START_DATE])
                && eventStartDate.isBefore(TaskDate.getTodayDate())) {
            throw new IllegalValueException(MESSAGE_EVENT_CONSTRAINTS);
        }
        
        if (!hasTaskComponentArray[START_DATE_COMPONENT] && eventStartTime.isBefore(TaskTime.getTimeNow())) {
            eventStartDate = eventStartDate.getNextDay();
        }
        
        return eventStartDate;
    }
    
    private TaskTime setStartTime(TaskDate eventStartDate) throws IllegalValueException {
        if (eventStartDate.equals(TaskDate.getTodayDate()) && !hasTaskComponentArray[Task.START_TIME_COMPONENT]) {
            return TaskTime.getTimeNow();
        } else {
            return new TaskTime(taskComponentArray[Task.START_TIME]);
        }
    }
    
    private TaskDate setEndDate(TaskDate eventStartDate, TaskTime eventStartTime, TaskTime eventEndTime) throws IllegalValueException {
        
        initialiseEndDate(eventStartDate);
        
        TaskDate eventEndDate = adjustEndDateDependingOnDateForm(eventStartDate, eventStartTime, eventEndTime);
        
        eventEndDate = adjustEndDateDependingOnEndTime(eventStartDate, eventEndDate, eventStartTime, eventEndTime);
        
        return eventEndDate;
    }
    
    /**
     * Adjust end date when start and end date is the same but end time is before start time.
     * Bring end date to the next day
     */
    private TaskDate adjustEndDateDependingOnEndTime(TaskDate eventStartDate, TaskDate eventEndDate, TaskTime eventStartTime, 
            TaskTime eventEndTime) throws IllegalValueException {
        if (!hasTaskComponentArray[END_DATE_COMPONENT] && eventStartDate.equals(eventEndDate) && eventEndTime.isBefore(eventStartTime)) {
            eventEndDate = eventEndDate.getNextDay();
        }
        return eventEndDate;
    }
    
    /**
     * Adjustment is made to end date to make sure end date is never before start date
     */
    private TaskDate adjustEndDateDependingOnDateForm(TaskDate eventStartDate, TaskTime eventStartTime, 
            TaskTime eventEndTime) throws IllegalValueException {
        
        TaskDate eventEndDate = new TaskDate(taskComponentArray[Task.END_DATE]);
        String userInputEndDate = taskComponentArray[END_DATE];
        
        if (TaskDate.isValidFullDate(userInputEndDate) && eventEndDate.isBefore(eventStartDate)
                || TaskDate.isValidMonthAndYear(userInputEndDate) && eventEndDate.isBefore(eventStartDate)) {
            throw new IllegalValueException(MESSAGE_EVENT_CONSTRAINTS);
        } else if (TaskDate.isValidDayOfWeek(userInputEndDate)) {
            eventEndDate = determineDayInWeekGivenName(eventStartDate, eventEndDate);
        } else if (TaskDate.isValidMonth(userInputEndDate)) {
            eventEndDate = determineEndDateGivenMonth(eventStartDate, eventEndDate);
        } else if (TaskDate.isValidDayAndMonth(userInputEndDate)) {
            eventEndDate = determineEndDateGivenMonth(eventStartDate, eventEndDate);
        } 
        
        return eventEndDate;
    }
    
    private void initialiseEndDate(TaskDate eventStartDate) {
        if (!hasTaskComponentArray[Task.END_DATE_COMPONENT]) {
            taskComponentArray[Task.END_DATE] = eventStartDate.toString();
        }
    }
    
    /**
     * Determines end date in the week with respect to start date
     */
    private TaskDate determineDayInWeekGivenName(TaskDate eventStartDate, TaskDate eventEndDate) {
        
        if (!eventEndDate.isAfter(eventStartDate) && hasTaskComponentArray[END_DATE_COMPONENT]) {
            try {
                eventEndDate = eventEndDate.getNextWeek();
            } catch (IllegalValueException e) {
                return null;
            }
        }
        return eventEndDate;
    }
    
    /**
     * Determine end date in the year with respect to start date
     */
    private TaskDate determineEndDateGivenMonth(TaskDate eventStartDate, TaskDate eventEndDate) {
        if (!eventEndDate.isAfter(eventStartDate)&& hasTaskComponentArray[END_DATE_COMPONENT]) {
            try {
                int yearOfExpectedEndDate = eventStartDate.getNextYear().getYearInt();
                int dayInEndDate = eventEndDate.getDayInt();
                int monthInEndDate = eventEndDate.getMonthInt();
                eventEndDate = new TaskDate(TaskDate.convertToStandardFormat(dayInEndDate, monthInEndDate, yearOfExpectedEndDate));
            } catch (IllegalValueException e) {
                return null;
            }
        }
        return eventEndDate;
    }
    
    //@@author
    
    //@@author A0148004R
    private boolean isValidRecurringEvent(TaskDate startDate, TaskDate endDate, RecurringType recurringType){
        
        if(recurringType.recurringType.equals(RecurringType.DAILY_RECURRING)){
            return Math.abs(TaskDate.between(startDate, endDate)) <= TaskDate.NUM_DAYS_PER_DAY;
        } else if(recurringType.recurringType.equals(RecurringType.WEEKLY_RECURRING)){
            return Math.abs(TaskDate.between(startDate, endDate)) <= TaskDate.NUM_DAYS_IN_A_WEEK;
        } else if(recurringType.recurringType.equals(RecurringType.MONTHLY_RECURRING)){
            return Math.abs(TaskDate.between(startDate, endDate)) <= TaskDate.NUM_DAYS_IN_A_MONTH;
        } else {
            return true;
        }
    }
    
    //@@author A0139257X
    private boolean isValidEventDuration(TaskDate startDate, TaskDate endDate, TaskTime startTime, TaskTime endTime) {
        TaskDate today = TaskDate.getTodayDate();
        TaskTime currentTime = TaskTime.getTimeNow();
        
        if (startDate.isBefore(today) || endDate.isBefore(today)) {
            return false;
        } else if (startDate.isAfter(endDate)) {
            return false;
        } else if (startDate.equals(today) && startTime.isBefore(currentTime)) { 
            return false;
        } else if (endDate.equals(today) && endTime.isBefore(currentTime)){
            return false;
        } else {
            return true;
        }
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }
}