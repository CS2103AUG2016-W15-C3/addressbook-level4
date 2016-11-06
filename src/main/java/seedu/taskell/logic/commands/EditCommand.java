//@@author A0142073R

package seedu.taskell.logic.commands;

import static seedu.taskell.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.taskell.commons.core.Messages;
import seedu.taskell.commons.core.UnmodifiableObservableList;
import seedu.taskell.commons.exceptions.IllegalValueException;
import seedu.taskell.logic.commands.Command;
import seedu.taskell.logic.commands.CommandResult;

import seedu.taskell.model.task.Description;
import seedu.taskell.model.task.FloatingTask;
import seedu.taskell.model.task.ReadOnlyTask;
import seedu.taskell.model.task.Task;
import seedu.taskell.model.task.TaskDate;
import seedu.taskell.model.task.TaskPriority;
import seedu.taskell.model.task.TaskTime;
import seedu.taskell.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.taskell.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Edits a task identified using it's last displayed index from the task
 * manager. Supports to edit different parameters of a task including
 * description, time, date and priority of a task.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the different parts of a task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer) RESPECTIVE TASK PARAMETERS\n" + "Example: " + COMMAND_WORD
            + " 1 desc: buy cake st: 7am et: 8am sd: 11-12-2016 ed: 12-12-2016 p: 2\n";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Original Task: %1$s \n\nUpdated Task: %2$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";
    public static final String TASK_NOT_FOUND = "The target task is missing";
    public static final String MESSAGE_TIME_CONSTRAINTS = "Start time must be before end time";
    public static final String MESSAGE_DATE_CONSTRAINTS = "Start date must be before end date";

    private final int targetIndex;

    private Description description;
    private TaskDate startDate;
    private TaskDate endDate;
    private TaskTime startTime;
    private TaskTime endTime;
    private TaskPriority taskPriority;

    private boolean[] hasComponentArray = new boolean[Task.NUM_BOOLEAN_TASK_COMPONENT];

    public EditCommand(int targetIndex, Description newDescription, TaskDate newStartDate, TaskDate newEndDate,
            TaskTime newStartTime, TaskTime newEndTime, TaskPriority newPriority, boolean[] hasComponentArray)
            throws IllegalValueException {

        this.targetIndex = targetIndex;

        description = newDescription;
        startTime = newStartTime;
        endTime = newEndTime;
        startDate = newStartDate;
        endDate = newEndDate;
        taskPriority = newPriority;

        this.hasComponentArray = hasComponentArray;
    }

    public void getEditInformation(ReadOnlyTask taskToEdit) {

        if (hasComponentArray[Task.DESCRIPTION_COMPONENT] == false) {
            description = taskToEdit.getDescription();
        }
        if (hasComponentArray[Task.START_TIME_COMPONENT] == false) {
            startTime = taskToEdit.getStartTime();
        }
        if (hasComponentArray[Task.END_TIME_COMPONENT] == false) {
            endTime = taskToEdit.getEndTime();
        }
        if (hasComponentArray[Task.START_DATE_COMPONENT] == false) {
            startDate = taskToEdit.getStartDate();
        }
        if (hasComponentArray[Task.END_DATE_COMPONENT] == false) {
            endDate = taskToEdit.getEndDate();
        }
        if (hasComponentArray[Task.PRIORITY_COMPONENT] == false) {
            taskPriority = taskToEdit.getTaskPriority();
        }
    }

    private boolean isValidDate(ReadOnlyTask taskToEdit) {
        if (taskToEdit.getTaskType().equals(Task.EVENT_TASK)) {
            if (endDate.isBefore(startDate)) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    private boolean ableToAdjustTime(ReadOnlyTask taskToEdit) {
        TaskTime currentTime = TaskTime.getTimeNow();
        if (taskToEdit.getTaskType().equals(Task.EVENT_TASK)) {
            if (endDate.equals(startDate) && endTime.isBefore(startTime)) {
                try {
                    endDate = endDate.getNextDay();
                } catch (IllegalValueException e) {
                    return false;
                }
                return true;
            } else if (endDate.equals(startDate) && endTime.isBefore(currentTime)) {
                endTime = currentTime;
                return true;
            } else if (endDate.equals(startDate) && startTime.isBefore(currentTime)) {
                startTime = currentTime;
                return true;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    private void jumpToNewTaskIndex() {
        jumpToIndex(targetIndex - 1);
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            history.deleteLatestCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);
        getEditInformation(taskToEdit);

        if (taskToEdit.getTaskType().equals(Task.FLOATING_TASK) && (hasComponentArray[Task.START_TIME_COMPONENT] == true
                || hasComponentArray[Task.END_TIME_COMPONENT] == true
                || hasComponentArray[Task.START_DATE_COMPONENT] == true
                || hasComponentArray[Task.END_DATE_COMPONENT] == true)) {
            return new CommandResult(FloatingTask.EDIT_FLOATING_NOT_ALLOWED);
        }

        if (!ableToAdjustTime(taskToEdit)) {
            return new CommandResult(MESSAGE_INVALID_COMMAND_FORMAT);
        }

        if (!isValidDate(taskToEdit)) {
            return new CommandResult(MESSAGE_DATE_CONSTRAINTS);
        }

        Task newTask = new Task(description, taskToEdit.getTaskType(), startDate, endDate, startTime, endTime,
                taskPriority, taskToEdit.getRecurringType(), taskToEdit.getTaskStatus(), taskToEdit.getTags());

        try {
            model.editTask(taskToEdit, newTask);

            jumpToNewTaskIndex();
            history.addTask(newTask);
            history.addOldTask((Task) taskToEdit);
            
        } catch (DuplicateTaskException pnfe) {
            history.deleteLatestCommand();
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException pnfe) {
            history.deleteLatestCommand();
            return new CommandResult(TASK_NOT_FOUND);
        }
        
        history.updateHistory();

        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit, newTask));
    }
}

// @@author