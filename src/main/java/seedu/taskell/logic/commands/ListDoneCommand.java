package seedu.taskell.logic.commands;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ListDoneCommand extends Command {
    
    public static final String COMMAND_WORD = "listdone";

    public static final String MESSAGE_SUCCESS = "Listed all completed tasks";

    private Set<String> keywordSet = new HashSet<>(Arrays.asList("true"));

    public ListDoneCommand() {
       
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(keywordSet);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }
}
