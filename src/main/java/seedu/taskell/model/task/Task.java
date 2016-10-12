package seedu.taskell.model.task;

import java.util.ArrayList;
import java.util.Objects;

import seedu.taskell.commons.util.CollectionUtil;
import seedu.taskell.model.tag.UniqueTagList;

/**
 * Represents a Task in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {
    
    public static final String DATE_TIME_DELIMITER = "by";
    public static final String DEADlINE_CONSTRAINTS = "There should be only a valid date after the keyword 'by'";

    private Description description;
    private TaskDate taskDate;
    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Description description, TaskDate taskDate, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(description, taskDate, tags);
        this.description = description;
        this.taskDate = taskDate;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getDescription(), source.getTaskDate(), source.getTags());
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public TaskDate getTaskDate() {
        return taskDate;
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
        return Objects.hash(description, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
    
    /**
     * Returns true when tokens after keyword is not a valid date or tag
     * @param args A list of tokenized arguments
     */
    public static boolean isValidTask(ArrayList<String> args) {
        try {
            for (int i=0; i<args.size(); i++) {
                if (args.contains(Task.DATE_TIME_DELIMITER)) {
                    for (int j=args.indexOf(Task.DATE_TIME_DELIMITER)+1; j<args.size(); j++) {
                        if (args.get(j) == null) {
                            return false;
                        } else if (!TaskDate.isValidDate(args.get(j)) && !args.get(j).contains("t/")) {
                            return false;
                        }
                    }
                } 
            }
        } catch (RuntimeException rte) {
            return false;
        }
        
        return true;
    }

}
