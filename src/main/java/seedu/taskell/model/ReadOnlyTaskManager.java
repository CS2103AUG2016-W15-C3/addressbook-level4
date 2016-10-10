package seedu.taskell.model;


import java.util.List;

import seedu.taskell.model.task.ReadOnlyTask;
import seedu.taskell.model.task.UniqueDoneList;
import seedu.taskell.model.task.UniqueTaskList;
import seedu.taskell.model.tag.Tag;
import seedu.taskell.model.tag.UniqueTagList;

/**
 * Unmodifiable view of an task manager
 */
public interface ReadOnlyTaskManager {

    UniqueTagList getUniqueTagList();

    UniqueTaskList getUniqueTaskList();

    UniqueDoneList getUniqueDoneList();
    /**
     * Returns an unmodifiable view of tasks list
     */
    List<ReadOnlyTask> getTaskList();
    /**
     * Returns an unmodifiable view of completed tasks list
     */
    List<ReadOnlyTask> getDoneList();
    /**
     * Returns an unmodifiable view of tags list
     */
    List<Tag> getTagList();

}
