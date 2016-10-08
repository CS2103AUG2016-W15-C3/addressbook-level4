package guitests;

import org.junit.Test;

import seedu.taskell.commons.core.Messages;
import seedu.taskell.testutil.TestTask;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends TaskManagerGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Mark"); //no results
        assertFindResult("find books", td.borrowBooks, td.discardBooks); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find books",td.discardBooks);
    }
    
    @Test
    public void find_nonEmptyList_byTag() {
        assertFindResult("find ACADemic", td.borrowBooks);  //check words with capitals
        assertFindResult("find personal", td.archivePastEmails, td.borrowBooks); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find personal", td.borrowBooks);
    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find Jean"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
