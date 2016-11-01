/** @@author A0142130A **/
package seedu.taskell.ui;

import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import jfxtras.scene.control.agenda.Agenda;
import seedu.taskell.commons.core.LogsCenter;
import seedu.taskell.commons.util.FxViewUtil;
import seedu.taskell.logic.commands.AddCommand;
import seedu.taskell.logic.commands.HelpCommand;
import seedu.taskell.logic.commands.ViewCalendarCommand;
import seedu.taskell.logic.commands.ViewHistoryCommand;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * The Display Panel of the App, on right side of screen
 * shows history or calendar view
 * calendar view is default view
 */
public class DisplayPanel extends UiPart {

    public static final String MESSAGE_NO_HISTORY = "No commands available for undo.";
    public static final String MESSAGE_DISPLAY_HISTORY = "List of command history available for undo:\n";

    private static final String WELCOME_MESSAGE = "Welcome to Taskell!\n"
            + "Enter '" + AddCommand.COMMAND_WORD + "' in command box to add a task.\n"
            + "Enter '" + ViewHistoryCommand.COMMAND_WORD_1 + "' for a list of commands to undo.\n"
            + "Enter '" + ViewCalendarCommand.COMMAND_WORD_1 + "' to view calendar.\n"
            + "Enter '" + HelpCommand.COMMAND_WORD + "' for more information about commands.";

    private static Logger logger = LogsCenter.getLogger(DisplayPanel.class);
    
    public static final String DISPLAY_PANEL_ID = "displayPanel";
    private static final String STATUS_BAR_STYLE_SHEET = "result-display";
    
    private TextArea displayTextArea;
    private CalendarView calendarView;
    private Agenda agenda;

    /**
     * Constructor is kept private as {@link #load(AnchorPane)} is the only way to create a DisplayPanel.
     */
    private DisplayPanel() {
        calendarView = new CalendarView();
        displayTextArea = new TextArea();
        agenda = calendarView.getAgenda();
    }

    @Override
    public void setNode(Node node) {
        //not applicable
    }

    @Override
    public String getFxmlPath() {
        return null; //not applicable
    }

    /**
     * This method should be called after the FX runtime is initialized and in FX application thread.
     * @param placeholder The AnchorPane where the DisplayPanel must be inserted
     */
    public static DisplayPanel load(AnchorPane placeholder){
        logger.info("Initializing displayTextArea panel");
        DisplayPanel displayPanel = new DisplayPanel();
        
        displayPanel.displayTextArea.setEditable(false);
        displayPanel.displayTextArea.setId(DISPLAY_PANEL_ID);
        displayPanel.displayTextArea.getStyleClass().removeAll();
        displayPanel.displayTextArea.getStyleClass().add(STATUS_BAR_STYLE_SHEET);
        
        FxViewUtil.applyAnchorBoundaryParameters(displayPanel.displayTextArea, 0.0, 0.0, 0.0, 0.0);
        placeholder.getChildren().add(displayPanel.displayTextArea);
        
        displayPanel.displayTextArea.setText(WELCOME_MESSAGE);
       
        return displayPanel;
    }
    
    /** called whenever history list needs to be shown on display panel
     * */
    public void loadList(AnchorPane placeholder, ArrayList<String> list) {
        placeholder.getChildren().clear();
        placeholder.getChildren().add(displayTextArea);
        
        displayTextArea.setText("");
        if (list.isEmpty()) {
            displayTextArea.setText(MESSAGE_NO_HISTORY);
        }
        else {
            displayTextArea.setText(MESSAGE_DISPLAY_HISTORY);
            for (int i=0; i<list.size(); i++) {
                int index = i+1;
                displayTextArea.appendText(index + ". " + list.get(i) + "\n");
            }
        }
    }
    
    /** default view
     *  clears panel if displayTextArea is showing
     *  fills panel with Agenda for events for the week
     * */
    public void loadCalendar(AnchorPane placeholder) {
               
        placeholder.getChildren().remove(displayTextArea);
        
        agenda = calendarView.getAgenda();
        FxViewUtil.applyAnchorBoundaryParameters(agenda, 0.0, 0.0, 0.0, 0.0);
        placeholder.getChildren().add(agenda);
             
    }
    
    /** @@author **/

}
