package seedu.taskell.logic.commands;

public class ListdoneCommand extends Command {
    
    public static final String COMMAND_WORD = "listdone";

    public static final String MESSAGE_SUCCESS = "Listed all completed tasks";

    public ListdoneCommand() {}

    @Override
    public CommandResult execute() {
        model.updateFilteredDoneListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
    
}
