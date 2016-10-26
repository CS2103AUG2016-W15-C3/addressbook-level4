//@@author A0142073R
package seedu.taskell.logic.commands;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.taskell.model.task.TaskPriority;

public class ListPriorityCommand extends Command {

    public static final String COMMAND_WORD = "list-priority";

    public static final String MESSAGE_SUCCESS = "Listed all completed tasks in descending priority";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": List the task with the specified priority. "
            + "Parameters: INDEX (must be between 0 and 3 inclusive).\n"
            + "Example: " + COMMAND_WORD + " 1";
    
    private Set<String> mostImportant = new HashSet<>(Arrays.asList(TaskPriority.HIGH_PRIORITY));
    private Set<String> important = new HashSet<>(Arrays.asList(TaskPriority.MEDIUM_PRIORITY));
    private Set<String> lessImportant = new HashSet<>(Arrays.asList(TaskPriority.LOW_PRIORITY));
    private Set<String> leastImportant = new HashSet<>(Arrays.asList(TaskPriority.NO_PRIORITY));
    
    private Set<String> keyword;
    public ListPriorityCommand(String priority) {
        if(priority.equals(TaskPriority.HIGH_PRIORITY))
            keyword = mostImportant;
        else if(priority.equals(TaskPriority.MEDIUM_PRIORITY))
            keyword = important;
        else if(priority.equals(TaskPriority.LOW_PRIORITY))
            keyword = lessImportant;
        else
            keyword = leastImportant;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskListPriority(keyword);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }
}
//@@author A0142073R