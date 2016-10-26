/** @@author A0142130A **/
package seedu.taskell.logic.commands;

import java.util.Set;

/**
 * Finds and lists all tasks in task manager whose description contains any of the argument keywords.
 * Keyword matching is not case sensitive.
 */

public class FindTagCommand extends Command {

    public static final String COMMAND_WORD = "find-tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose description contain any of "
            + "the specified keywords (not case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " help study homework";

    private final Set<String> keywords;

    public FindTagCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskListByAnyKeyword(keywords);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }
}
