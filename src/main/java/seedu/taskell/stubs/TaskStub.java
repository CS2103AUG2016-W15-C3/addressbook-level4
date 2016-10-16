package seedu.taskell.stubs;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import seedu.taskell.model.tag.UniqueTagList;

/** TaskStub is a placeholder class for Task class until Task class is ready for integration
 *  all attributes will have dummy data or empty list
 *  TODO: to be deleted when Task is integrated
 * */

public class TaskStub {
    private DescriptionStub description;
    private LocalDate date;
    private LocalTime time;
    private TaskPriorityStub priority;
    private UniqueTagList tagList;
    
    public TaskStub() {
        description = new DescriptionStub();
        date = LocalDate.of(2016, Month.JANUARY, 13);
        time = LocalTime.of(13, 13);
        priority = new TaskPriorityStub();
        tagList = new UniqueTagList();
    }
    
    public String getDescription() {
        return description.toString();
    }
    
    public String getDate() {
        return date.toString();
    }
    
    public String getTime() {
        return time.toString();
    }
    
    public TaskPriorityStub getPriority() {
        return priority;
    }
    
    public UniqueTagList getTags() {
        return tagList;
    }
    
    public void setDescription(String newDescription) {
        description = new DescriptionStub(newDescription);
    }
    
    public void setDate(int year, int month, int dayOfMonth) {
        date = LocalDate.of(year, month, dayOfMonth);
    }
    
    public void setTime(int hour, int minute) {
        time = LocalTime.of(hour, minute);
    }
    
    public void setTaskPriority(TaskPriorityStub newPriority) {
        priority = newPriority;
    }
}
