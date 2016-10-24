package seedu.taskell.testutil;

import seedu.taskell.model.tag.Tag;
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
    private TaskComplete isCompleted;
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
    
    public void setTaskComplete(TaskComplete isCompleted) {
        this.isCompleted = isCompleted;
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
    public TaskComplete getIsComplete() {
        return isCompleted;
    }
    
    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getDescription().description + " ");
        sb.append("on " + this.getTaskDate().taskDate + " ");
        sb.append("startat " + this.getStartTime().taskTime + " ");
        sb.append("endat " + this.getEndTime().taskTime + " ");
        sb.append(TaskPriority.PREFIX + this.getTaskPriority().taskPriority + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append(Tag.PREFIX + s.tagName + " "));
        return sb.toString();
    }
}
