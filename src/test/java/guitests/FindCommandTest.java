package guitests;

import org.junit.Test;

import seedu.taskell.commons.core.Messages;
import seedu.taskell.testutil.TestTask;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends TaskManagerGuiTest {

    @Test
    public void find_nonEmptyList_success() {
        assertFindResult("find Mark"); //no results
        assertFindResult("find Books", td.borrowBooks, td.discardBooks); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Books",td.discardBooks);
    }
    
    /** @@author A0142130A **/
    
    @Test
    public void find_nonEmptyList_byTag_success() {
        assertFindResult("find chicken"); //no results
        assertFindResult("find friends", td.archivePastEmails, td.borrowBooks); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find friends", td.borrowBooks);
    }
    
    @Test
    public void find_byMoreThanOneTags_success() {
        assertFindResult("find friends owesMoney", td.borrowBooks);
    }
    
    /** @@author **/

    @Test
    public void find_emptyList_noResults() {
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
