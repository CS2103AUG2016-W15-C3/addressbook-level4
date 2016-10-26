package seedu.taskell.model;

import java.util.Set;

import seedu.taskell.commons.core.UnmodifiableObservableList;
import seedu.taskell.model.task.Task;
import seedu.taskell.model.task.ReadOnlyTask;
import seedu.taskell.model.task.UniqueTaskList;
import seedu.taskell.model.task.UniqueTaskList.TaskNotFoundException;

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

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;
    
    /** Edits the given task 
     * @throws TaskNotFoundException */
    void editTask(ReadOnlyTask task,Task newTask) throws UniqueTaskList.DuplicateTaskException, TaskNotFoundException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();
    
    /** Updates the filter of the filtered task list to filter by priority*/
    void updateFilteredTaskListPriority(Set<String> keywords);
    
    /** Updates the filter of the filtered task list to filter by the given keywords (AND operation)*/
    void updateFilteredTaskList(Set<String> keywords);

    /** Updates the filter of the filtered task list to filter by the any of given keywords (OR operation)*/
    void updateFilteredTaskListByAnyKeyword(Set<String> keywords);
}
