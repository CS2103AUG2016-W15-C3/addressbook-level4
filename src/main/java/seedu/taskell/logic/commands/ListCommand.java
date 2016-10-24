package seedu.taskell.logic.commands;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ListCommand extends Command {
    
    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks need to be done";

    private Set<String> keywordSet = new HashSet<>(Arrays.asList("not"));

    public ListCommand() {
       
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredtaskListCompleted(keywordSet);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }
}
