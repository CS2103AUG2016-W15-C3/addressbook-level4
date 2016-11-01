package seedu.taskell.model.task;

import java.util.Objects;

import seedu.taskell.model.task.Description;
import seedu.taskell.commons.exceptions.IllegalValueException;
import seedu.taskell.commons.util.CollectionUtil;
import seedu.taskell.model.tag.UniqueTagList;

//@@author A0139257X
/**
 * Represents a Floating Task in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class FloatingTask extends Task {
    
    public static final String RECURRING_TYPE_NOT_ALLOWED = "Floating task cannot be recurring";
    public static final String EDIT_FLOATING_NOT_ALLOWED = "Floating task does not have date and time.";
    
    public FloatingTask(String description, String taskPriority, String recurringType, String taskStatus, UniqueTagList tags) throws IllegalValueException {
        this(new Description(description),
                FLOATING_TASK,
                new TaskDate(TaskDate.DEFAULT_DATE),
                new TaskDate(TaskDate.DEFAULT_DATE),
                new TaskTime(TaskTime.DEFAULT_START_TIME),
                new TaskTime(TaskTime.DEFAULT_END_TIME),
                new TaskPriority(taskPriority),
                new RecurringType(recurringType),
                new TaskStatus(taskStatus),
                tags);
    }
    
    public FloatingTask(Description description, String taskType, TaskDate startDate, TaskDate endDate, TaskTime startTime, TaskTime endTime, TaskPriority taskPriority, RecurringType recurringType, TaskStatus taskStatus, UniqueTagList tags) {
        super(description, taskType, startDate, endDate, startTime, endTime, taskPriority, recurringType, taskStatus, tags);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

}
