package seedu.taskell.model;

import javafx.collections.transformation.FilteredList;
import seedu.taskell.commons.core.ComponentManager;
import seedu.taskell.commons.core.LogsCenter;
import seedu.taskell.commons.core.UnmodifiableObservableList;
import seedu.taskell.commons.events.model.TaskManagerChangedEvent;
import seedu.taskell.commons.util.StringUtil;
import seedu.taskell.logic.commands.UndoCommand;
import seedu.taskell.model.task.Task;
import seedu.taskell.model.task.ReadOnlyTask;
import seedu.taskell.model.task.UniqueTaskList;
import seedu.taskell.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.taskell.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Represents the in-memory model of the task manager data. All changes to any
 * model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final FilteredList<Task> filteredTasks;

    /**
     * Initializes a ModelManager with the given TaskManager TaskManager and its
     * variables should not be null
     */
    public ModelManager(TaskManager src, UserPrefs userPrefs) {
        super();
        assert src != null;
        assert userPrefs != null;

        logger.fine("Initializing with task manager: " + src + " and user prefs " + userPrefs);

        taskManager = new TaskManager(src);
        filteredTasks = new FilteredList<>(taskManager.getTasks());
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    public ModelManager(ReadOnlyTaskManager initialData, UserPrefs userPrefs) {
        taskManager = new TaskManager(initialData);
        filteredTasks = new FilteredList<>(taskManager.getTasks());
    }

    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        taskManager.resetData(newData);
        indicateTaskManagerChanged();
    }

    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskManagerChanged() {
        raise(new TaskManagerChangedEvent(taskManager));
    }

    @Override
    public synchronized void editTask(ReadOnlyTask old, Task toEdit)
            throws DuplicateTaskException, TaskNotFoundException {
        taskManager.editTask(old, toEdit);
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskManager.removeTask(target);
        // UndoCommand.updateMostRecentDeletedTask(target);
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskManager.addTask(task);
        // UndoCommand.updateMostRecentAddedTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    // =========== Filtered Task List Accessors
    // ===============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    // @@author A0142073R
    public void updateFilteredTaskListPriority(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new PriorityQualifier(keywords)));
    }
    // @@author

    /** @@author A0142130A **/
    @Override
    public void updateFilteredTaskListByAnyKeyword(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new TagsQualifier(keywords)));
    }

    /** @@author **/

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    // ========== Inner classes/interfaces used for filtering
    // ==================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);

        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);

        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        /** @@author A0142130A **/
        @Override
        public boolean run(ReadOnlyTask task) {
            String searchString = task.getDescription().description + " " + task.tagsSimpleString();
            return nameKeyWords.stream().allMatch(keyword -> StringUtil.containsIgnoreCase(searchString, keyword));
        }

        /** @@author **/

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

    /** @@author A0142130A **/
    private class TagsQualifier implements Qualifier {
        private Set<String> tagsKeyWords;

        TagsQualifier(Set<String> keyWords) {
            this.tagsKeyWords = keyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return tagsKeyWords.stream()
                    .filter(keyword -> StringUtil.containsIgnoreCase(task.tagsSimpleString(), keyword)).findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", tagsKeyWords);
        }
    }

    /** @@author **/

    // @@author A0142073R
    private class PriorityQualifier implements Qualifier {
        private Set<String> PriorityKeyWords;

        PriorityQualifier(Set<String> keyWords) {
            this.PriorityKeyWords = keyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            String searchString = task.getTaskPriority().taskPriority;
            return PriorityKeyWords.stream().allMatch(keyword -> StringUtil.containsIgnoreCase(searchString, keyword));
        }

        @Override
        public String toString() {
            return "priority=" + String.join(", ", PriorityKeyWords) + "\n";
        }
    }
    // @@ author
}
