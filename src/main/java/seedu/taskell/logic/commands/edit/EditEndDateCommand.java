//@@author A0142073R
package seedu.taskell.logic.commands.edit;

import seedu.taskell.commons.core.Messages;
import seedu.taskell.commons.core.UnmodifiableObservableList;
import seedu.taskell.commons.exceptions.IllegalValueException;
import seedu.taskell.logic.commands.AddCommand;
import seedu.taskell.logic.commands.Command;
import seedu.taskell.logic.commands.CommandResult;
import seedu.taskell.model.tag.Tag;
import seedu.taskell.model.tag.UniqueTagList;
import seedu.taskell.model.task.ReadOnlyTask;
import seedu.taskell.model.task.Task;
import seedu.taskell.model.task.TaskDate;
import seedu.taskell.model.task.TaskPriority;
import seedu.taskell.model.task.TaskTime;
import seedu.taskell.model.task.UniqueTaskList;
import seedu.taskell.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Edits a task EndDate identified using it's last displayed index from the task
 * manager.
 */
public class EditEndDateCommand extends Command {
    public static final String COMMAND_WORD = "edit-endDate";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the EndDate of a task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer) NEW_DATE\n" + "Example: " + COMMAND_WORD + " 1 8-8-2016 ";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Original Task: %1$s \n\nUpdatedTask: %2$s";

    public final int targetIndex;
    public final TaskDate endDate;

    public EditEndDateCommand(int targetIndex, String EndDate) throws IllegalValueException {
        this.targetIndex = targetIndex;
        this.endDate = new TaskDate(EndDate);
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);
        if (taskToEdit.getTaskType().equals("FLOATING")) {
            return new CommandResult("Unable to edit date of floating task");
        }
        Task newTask = new Task(taskToEdit.getDescription(), taskToEdit.getTaskType(), taskToEdit.getStartDate(),

                endDate, taskToEdit.getStartTime(), taskToEdit.getEndTime(), taskToEdit.getTaskPriority(), taskToEdit.getTaskStatus(),

                taskToEdit.getTags());
        try {
            model.editTask(taskToEdit, newTask);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(AddCommand.MESSAGE_DUPLICATE_TASK);
        }

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit, newTask));
    }
}
//@@author 