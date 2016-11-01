package seedu.taskell.logic.commands;

import seedu.taskell.commons.core.EventsCenter;
import seedu.taskell.commons.events.undo.ExecutedIncorrectCommandEvent;

/**
 * Represents an incorrect command. Upon execution, produces some feedback to the user.
 */
public class IncorrectCommand extends Command {

    private static boolean isAddEditDeleteCommand;
    public final String feedbackToUser;

    public IncorrectCommand(String feedbackToUser){
        this.feedbackToUser = feedbackToUser;
    }

    @Override
    public CommandResult execute() {
        indicateAttemptToExecuteIncorrectCommand();
        indicateExecutedIncorrectCommand();
        return new CommandResult(feedbackToUser);
    }

    /** @@author A0142130A **/
    
    /** for setting latest command as an undoable command
     * */
    public static void setIsUndoableCommand(boolean value) {
        isAddEditDeleteCommand = value;
    }
    
    public static boolean isUndoableCommand() {
        return isAddEditDeleteCommand;
    }
    
    /** if latest command is an undoable command, need to delete its command history
     * */
    private void indicateExecutedIncorrectCommand() {
        EventsCenter.getInstance().post(new ExecutedIncorrectCommandEvent(isAddEditDeleteCommand));
    }

    /** @@author **/
}

