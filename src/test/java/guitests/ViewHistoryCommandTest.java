/** @@author A0142130A **/
package guitests;

import java.util.ArrayList;

import org.junit.Test;

import seedu.taskell.logic.commands.ViewHistoryCommand;
import seedu.taskell.model.HistoryManager;
import seedu.taskell.ui.DisplayPanel;

public class ViewHistoryCommandTest extends TaskManagerGuiTest {
    
    @Test
    public void viewHistory_success() {
        HistoryManager.getInstance().clear();
        
        String expectedMessage = ViewHistoryCommand.MESSAGE_SUCCESS;
        
        commandBox.runCommand("add smth by tmr");
        commandBox.runCommand("history");
        assertResultMessage(expectedMessage);
        String text = displayPanel.getText();
        assertListMatch(text, HistoryManager.getInstance().getListCommandText());
        
//        commandBox.runCommand("edit 1 desc: find socks");
//        commandBox.runCommand("hist");
//        assertResultMessage(expectedMessage);
//        text = displayPanel.getText();
//        assertListMatch(text, HistoryManager.getInstance().getListCommandText());
        
        HistoryManager.getInstance().clear();
    }

    private void assertListMatch(String result, ArrayList<String> list) {
        String expected = DisplayPanel.MESSAGE_DISPLAY_HISTORY;
        
        for (int i=0; i<list.size(); i++) {
            int index = i+1;
            expected = expected + index + ". " + list.get(i) + "\n";
        }
        
        assert expected.equals(result);
    }
}
