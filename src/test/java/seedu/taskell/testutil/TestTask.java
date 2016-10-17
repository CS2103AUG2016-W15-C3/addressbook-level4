package seedu.taskell.testutil;

import seedu.taskell.model.tag.UniqueTagList;
import seedu.taskell.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Description description;
    private String taskType;
    private TaskPriority taskPriority;
    private TaskTime startTime;
    private TaskTime endTime;
    private TaskDate taskDate;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setDescription(Description description) {
        this.description = description;
    }
    
    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public void setTaskPriority(TaskPriority taskPriority) {
        this.taskPriority = taskPriority;
    }

    public void setStartTime(TaskTime startTime) {
        this.startTime = startTime;
    }
    
    public void setEndTime(TaskTime endTime) {
        this.endTime = endTime;
    }

    public void setTaskDate(TaskDate taskDate) {
        this.taskDate = taskDate;
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
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getDescription().description + " ");
        sb.append("p/" + this.getTaskType() + " ");
        sb.append("p/" + this.getTaskDate().taskDate + " ");
        sb.append("e/" + this.getStartTime().taskTime + " ");
        sb.append("e/" + this.getEndTime().taskTime + " ");
        sb.append("a/" + this.getTaskPriority().taskPriority + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
