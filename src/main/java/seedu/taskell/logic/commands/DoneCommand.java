package seedu.taskell.logic.commands;

import seedu.taskell.commons.core.Messages;
import seedu.taskell.commons.core.UnmodifiableObservableList;
import seedu.taskell.model.task.ReadOnlyTask;
import seedu.taskell.model.task.Task;
import seedu.taskell.model.task.TaskComplete;
import seedu.taskell.model.task.UniqueTaskList;
import seedu.taskell.model.task.UniqueTaskList.TaskNotFoundException;

public class DoneCommand extends Command {
    public static final String COMMAND_WORD = "done";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Done the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DONE_TASK_SUCCESS = "Done Task: %1$s";

    public final int targetIndex;
    
    
    public DoneCommand(int targetIndex) {
        this.targetIndex = targetIndex;
        
    }
    
    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        
        TaskComplete isComplete = new TaskComplete("false");
        
        ReadOnlyTask taskToDone = lastShownList.get(targetIndex - 1);
        
        Task newTask = new Task(taskToDone.getDescription(), taskToDone.getTaskType(), taskToDone.getTaskDate(), taskToDone.getStartTime(),
                taskToDone.getEndTime(),  taskToDone.getTaskPriority(), isComplete, taskToDone.getTags());
        
        try {
            model.editTask(taskToDone, newTask);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(AddCommand.MESSAGE_DUPLICATE_TASK);
        }

        return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, taskToDone));
    }

}
