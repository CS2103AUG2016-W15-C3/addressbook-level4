/** @@author A0142130A **/
package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.taskell.commons.core.Messages;
import seedu.taskell.testutil.TestTask;

public class FindTagCommandTest extends TaskManagerGuiTest {
    @Test
    public void findTag_nonEmptyList_success() {
        assertFindResult("find-tag chicken"); //no results
        assertFindResult("find-tag friends", td.archivePastEmails, td.borrowBooks); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find-tag friends",td.borrowBooks);
    }
    
    @Test
    public void findTag_multipleKeywords_success() {
        assertFindResult("find-tag friends owesMoney", td.archivePastEmails, td.borrowBooks, td.collectParcel); //multiple results
    } 

    @Test
    public void findTag_emptyList_noResults(){
        commandBox.runCommand("clear");
        assertFindResult("find-tag friends"); //no results
    }

    @Test
    public void findTag_invalidCommand_fail() {
        commandBox.runCommand("find-tagfriends");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
