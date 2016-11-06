package seedu.taskell.logic.commands;


import seedu.taskell.commons.core.EventsCenter;

import seedu.taskell.commons.events.ui.ShowHelpRequestEvent;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
   
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;
    
    private static HelpCommand self;
    public static final String MESSAGE_SUCCESS = "Opened help window.";

    public HelpCommand() {}

    public static HelpCommand getInstance() {
        if (self == null) {
            self = new HelpCommand();
        }
        
        return self;
    }
    @Override
    public CommandResult execute() {
        raiseHelpCommandInputEvent();
        return new CommandResult(MESSAGE_SUCCESS);
    }
    private void raiseHelpCommandInputEvent() {
        EventsCenter.getInstance().post(new ShowHelpRequestEvent());      
    }
}
