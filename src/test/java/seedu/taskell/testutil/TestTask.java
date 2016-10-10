package seedu.taskell.testutil;

import seedu.taskell.model.task.*;
import seedu.taskell.model.tag.UniqueTagList;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Description description;
    private TaskDate taskDate;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setDescription(Description description) {
        this.description= description;
    }

    public void setTaskDate(TaskDate taskDate) {
        this.taskDate= taskDate;
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
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getDescription().description+ " ");
        sb.append("by " + this.getTaskDate().taskDateInputByUser+ " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
