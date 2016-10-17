package seedu.taskell.model;

import java.util.Set;

import seedu.taskell.commons.core.UnmodifiableObservableList;
import seedu.taskell.model.task.Task;
import seedu.taskell.model.task.ReadOnlyTask;
import seedu.taskell.model.task.UniqueTaskList;
import seedu.taskell.model.task.UniqueDoneList;
/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the TaskManager */
    ReadOnlyTaskManager getTaskManager();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;
    void deleteDoneTask(ReadOnlyTask target) throws UniqueDoneList.TaskNotFoundException;
    
    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;
    void addDoneTask(Task task);
    
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);
    
    /** Updates the filter of the filtered completed task list to show all tasks */
    void updateFilteredDoneListToShowAll();

}
