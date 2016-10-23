package seedu.taskell.logic.commands;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ListDoCommand extends Command {
    
    public static final String COMMAND_WORD = "listdo";

    public static final String MESSAGE_SUCCESS = "Listed all tasks need to be done";

    private Set<String> keywordSet = new HashSet<>(Arrays.asList("true"));

    public ListDoCommand() {
        
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(keywordSet);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }
}
