//@@author A0142073R
package guitests;

import org.junit.Test;

import seedu.taskell.commons.exceptions.IllegalValueException;
import seedu.taskell.model.task.TaskPriority;
import seedu.taskell.testutil.TestTask;

import static org.junit.Assert.assertTrue;

public class ListPriorityCommandTest extends TaskManagerGuiTest {

    @Test
    public void listPriority() throws IllegalValueException {

        // list 2 tasks in the list
        TestTask[] currentList = td.getTypicalTasks();
        String priority = TaskPriority.LOW_PRIORITY;
        assertlistPrioritySuccess(currentList, priority, td.archivePastEmails, td.borrowBooks, td.collectParcel, td.discardBooks,td.editPowerpoint);

         //no tasks to list
         priority = TaskPriority.NO_PRIORITY;
         assertlistPrioritySuccess(currentList, priority);
        
         // invalid command format
         commandBox.runCommand("list-priority");
         assertResultMessage("Invalid command format! \n" + "list-priority" + ": List the task with the specified priority. "
                 + "Parameters: INDEX (must be between 0 and 3 inclusive).\n"
                 + "Example: list-priority 1");

    }

    /**
     * Runs the list priority command to list tasks with a specified
     * priority and confirms the result is correct.
     * 
     * @param priority
     *            e.g. to list tasks with priority 1, "1"
     *            should be given as the date.
     * @param currentList
     *            A copy of the current list of tasks (before prioritising).
     * @param 
     * @throws IllegalValueException
     */
    private void assertlistPrioritySuccess(final TestTask[] currentList, String priority, TestTask... values)
            throws IllegalValueException {

        commandBox.runCommand("list-priority " + priority);

        assertListSize(values.length);
        // confirm the result message is correct
        assertResultMessage(values.length + " tasks listed!");
        // confirm the list now contains all previous tasks except the edited
        // task
        assertTrue(taskListPanel.isListMatching(values));
    }

}
// @@author