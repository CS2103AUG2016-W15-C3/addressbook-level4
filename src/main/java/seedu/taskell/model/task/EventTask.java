package seedu.taskell.model.task;

import java.util.Objects;

import seedu.taskell.commons.util.CollectionUtil;
import seedu.taskell.model.tag.UniqueTagList;

/**
 * Represents an Event task in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class EventTask extends Task {

    /**
     * Every field must be present and not null.
     */
    public EventTask(Description description, String taskType, TaskDate taskDate, TaskTime startTime, TaskTime endTime, TaskPriority taskPriority, UniqueTagList tags) {
        super(description, taskType, taskDate, startTime, endTime, taskPriority, tags);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }
}