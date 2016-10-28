//@@author A0142073R
package seedu.taskell.logic.commands.edit;

import seedu.taskell.commons.core.Messages;
import seedu.taskell.commons.core.UnmodifiableObservableList;
import seedu.taskell.commons.exceptions.IllegalValueException;
import seedu.taskell.logic.commands.AddCommand;
import seedu.taskell.logic.commands.Command;
import seedu.taskell.logic.commands.CommandResult;
import seedu.taskell.logic.commands.UndoCommand;
import seedu.taskell.model.tag.Tag;
import seedu.taskell.model.tag.UniqueTagList;
import seedu.taskell.model.task.Description;
import seedu.taskell.model.task.ReadOnlyTask;
import seedu.taskell.model.task.Task;
import seedu.taskell.model.task.TaskDate;
import seedu.taskell.model.task.TaskPriority;
import seedu.taskell.model.task.TaskTime;
import seedu.taskell.model.task.UniqueTaskList;
import seedu.taskell.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Edits a task identified using it's last displayed index from the task manager.
 */
public class EditDescriptionCommand extends Command {
    public static final String COMMAND_WORD_1 = "edit-desc";
    public static final String COMMAND_WORD_2 = "edit-name";

    public static final String MESSAGE_USAGE = COMMAND_WORD_1
            + "/"+ COMMAND_WORD_2
            + ": Edits the description task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer) NEW_DESCRIPTION\n"
            + "Example: " + COMMAND_WORD_1 + " 1 buy cake\n"
            + "Example: " + COMMAND_WORD_2 + " 2 do 2103t\n";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Original Task: %1$s \n\nUpdated Task: %2$s";

    public final int targetIndex;
    public final Description description;
    
    public EditDescriptionCommand(int targetIndex, String newDescription) throws IllegalValueException {
        this.targetIndex = targetIndex;
        this.description = new Description(newDescription);
    }
    
    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);
        
        Task newTask = new Task(description,taskToEdit.getTaskType(),taskToEdit.getStartDate(), taskToEdit.getEndDate(), taskToEdit.getStartTime(),taskToEdit.getEndTime(),
                taskToEdit.getTaskPriority(),taskToEdit.getTaskStatus(), taskToEdit.getTags()
        );
        try {
            model.editTask(taskToEdit,newTask);
            UndoCommand.addTaskToCommandHistory(newTask);
            UndoCommand.addOldTaskToCommandHistory((Task) taskToEdit);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        } catch (UniqueTaskList.DuplicateTaskException e) {
            UndoCommand.deletePreviousCommand();
            return new CommandResult(AddCommand.MESSAGE_DUPLICATE_TASK);
        }

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit, newTask));
    }
}
//@@author 
