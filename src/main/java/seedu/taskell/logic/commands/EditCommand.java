package seedu.taskell.logic.commands;

import seedu.taskell.commons.core.Messages;
import seedu.taskell.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.taskell.commons.core.UnmodifiableObservableList;
import seedu.taskell.commons.exceptions.IllegalValueException;
import seedu.taskell.model.task.Description;
import seedu.taskell.model.task.ReadOnlyTask;
import seedu.taskell.model.task.Task;
import seedu.taskell.model.task.TaskDate;
import seedu.taskell.model.task.TaskTime;
import seedu.taskell.logic.commands.*;
/**
 * Edit a task identified by the index number used in the last task listing..
 */
public class EditCommand extends Command{
    
    public static final String COMMAND_WORD = "edit";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD 
            + ": Edit a task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 Jogging with Peter";
    
    public static final String MESSAGE_SUCCESS = "Task updated: %1$s";
   
    
    public final int targetIndex;
    public final String newInfo;
    public EditCommand(int targetIndex, String newInfo){
        this.targetIndex = targetIndex;
        this.newInfo = newInfo;
    }

    
    @Override
    public CommandResult execute(){
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        
        ReadOnlyTask editTask = lastShownList.get(targetIndex - 1);
        Task toEdit = null;
        if(Description.isValidDescription(newInfo)){
            try {
                toEdit = new Task(new Description(newInfo), editTask.getTaskDate(), editTask.getTags());
                try {
                    model.deleteTask(editTask);
                } catch (TaskNotFoundException e) {
                    assert false : "The target task cannot be missing";
                }
                model.addTask(toEdit);
            } catch (IllegalValueException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }else if(TaskDate.isValidDate(newInfo)){
            try {
                toEdit = new Task(editTask.getDescription(), new TaskDate(newInfo), editTask.getTags());
                try {
                    model.deleteTask(editTask);
                } catch (TaskNotFoundException e) {
                    assert false : "The target task cannot be missing";
                }
                model.addTask(toEdit);
            } catch (IllegalValueException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }

        
        return new CommandResult(String.format(MESSAGE_SUCCESS, toEdit));
    }
    
}
