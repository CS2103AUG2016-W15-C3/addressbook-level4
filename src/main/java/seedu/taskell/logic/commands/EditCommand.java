//@@author A0142073R
package seedu.taskell.logic.commands;

import seedu.taskell.commons.core.Messages;
import seedu.taskell.commons.core.UnmodifiableObservableList;
import seedu.taskell.commons.exceptions.IllegalValueException;
import seedu.taskell.logic.commands.Command;
import seedu.taskell.logic.commands.CommandResult;
import seedu.taskell.logic.commands.UndoCommand;
import seedu.taskell.model.task.Description;
import seedu.taskell.model.task.ReadOnlyTask;
import seedu.taskell.model.task.Task;
import seedu.taskell.model.task.TaskDate;
import seedu.taskell.model.task.TaskPriority;
import seedu.taskell.model.task.TaskTime;
import seedu.taskell.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.taskell.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Edits a task identified using it's last displayed index from the task
 * manager.
 */
public class EditCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the different parts of a task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer) RESPECTIVE TASK PARAMETERS\n" + "Example: " + COMMAND_WORD
            + " 1 desc: buy cake st: 7am et: 8am sd: 11-12-2016 ed: 12-12-2016 p: 2\n";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Original Task: %1$s \n\nUpdated Task: %2$s";

    private final int targetIndex;

    private Description description;
    private TaskDate startDate;
    private TaskDate endDate;
    private TaskTime startTime;
    private TaskTime endTime;
    private TaskPriority taskPriority;

    private boolean hasChangedDescription;
    private boolean hasChangedStartDate;
    private boolean hasChangedEndDate;
    private boolean hasChangedStartTime;
    private boolean hasChangedEndTime;
    private boolean hasChangedPriority;

    public EditCommand(int targetIndex, Description newDescription, boolean hasChangedDescription,
            TaskDate newStartDate, boolean hasChangedStartDate, TaskDate newEndDate, boolean hasChangedEndDate,
            TaskTime newStartTime, boolean hasChangedStartTime, TaskTime newEndTime, boolean hasChangedEndTime,
            TaskPriority newPriority, boolean hasChangedPriority) throws IllegalValueException {
        this.targetIndex = targetIndex;
        description = newDescription;
        this.hasChangedDescription = hasChangedDescription;
        startTime = newStartTime;
        this.hasChangedStartTime = hasChangedStartTime;
        endTime = newEndTime;
        this.hasChangedEndTime = hasChangedEndTime;
        startDate = newStartDate;
        this.hasChangedStartDate = hasChangedStartDate;
        endDate = newEndDate;
        this.hasChangedEndDate = hasChangedEndDate;
        taskPriority = newPriority;
        this.hasChangedPriority = hasChangedPriority;
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);
        if (hasChangedDescription == false) {
            description = taskToEdit.getDescription();
        }
        if (hasChangedStartTime == false) {
            startTime = taskToEdit.getStartTime();
        }
        if (hasChangedEndTime == false) {
            endTime = taskToEdit.getEndTime();
        }
        if (hasChangedStartDate == false) {
            startDate = taskToEdit.getStartDate();
        }
        if (hasChangedEndDate == false) {
            endDate = taskToEdit.getEndDate();
        }
        if (hasChangedPriority == false) {
            taskPriority = taskToEdit.getTaskPriority();
        }

        Task newTask = new Task(description, taskToEdit.getTaskType(), startDate, endDate, startTime, endTime,
                taskPriority, taskToEdit.getRecurringType(), taskToEdit.getTaskStatus(), taskToEdit.getTags());

        try {
            model.editTask(taskToEdit, newTask);
        } catch (TaskNotFoundException | DuplicateTaskException pnfe) {
            assert false : "The target task cannot be missing";
        } 

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit, newTask));
    }
}
// @@author