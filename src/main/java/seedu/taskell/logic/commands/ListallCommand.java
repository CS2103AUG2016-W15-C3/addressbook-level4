package seedu.taskell.logic.commands;


/**
 * Lists all tasks in the task manager to the user.
 */
public class ListallCommand extends Command {

    public static final String COMMAND_WORD = "listall";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    public ListallCommand() {}

    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
