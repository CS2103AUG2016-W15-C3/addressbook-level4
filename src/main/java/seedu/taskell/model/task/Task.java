package seedu.taskell.model.task;

import java.util.Objects;

import seedu.taskell.commons.util.CollectionUtil;
import seedu.taskell.model.tag.UniqueTagList;

/**
 * Represents a Task in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {
    public static final String FLOATING_TASK = "FLOATING";
    public static final String DEADLINE_TASK = "DEADLINE";
    public static final String EVENT_TASK = "EVENT";

    private Description description;
    private String taskType;
    private TaskDate taskDate;
    private TaskTime startTime;
    private TaskTime endTime;
    private TaskPriority taskPriority;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Description description, String taskType, TaskDate taskDate, TaskTime startTime, TaskTime endTime, TaskPriority taskPriority, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(description, taskType, taskDate, startTime, endTime, taskPriority, tags);
        this.description = description;
        this.taskType = taskType;
        this.taskDate = taskDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.taskPriority = taskPriority;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getDescription(), source.getTaskType(), source.getTaskDate(), source.getStartTime(), source.getEndTime(), source.getTaskPriority(), source.getTags());
    }

    @Override
    public Description getDescription() {
        return description;
    }
    
    @Override
    public String getTaskType() {
        return taskType;
    }

    @Override
    public TaskDate getTaskDate() {
        return taskDate;
    }

    @Override
    public TaskTime getStartTime() {
        return startTime;
    }
    
    @Override
    public TaskTime getEndTime() {
        return endTime;
    }

    @Override
    public TaskPriority getTaskPriority() {
        return taskPriority;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, taskDate, startTime, endTime, taskPriority, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
