package guitests;

import org.junit.Test;

import seedu.taskell.commons.exceptions.IllegalValueException;
import seedu.taskell.testutil.TestTask;
import seedu.taskell.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.taskell.logic.commands.EditDescriptionCommand.MESSAGE_EDIT_TASK_SUCCESS;

public class EditDescriptionCommandTest extends TaskManagerGuiTest {

    @Test
    public void edit() throws IllegalValueException {

        // edit the first in the list
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        String newDescription = "make tea";
        asserteditSuccess(targetIndex, currentList, newDescription);

        // edit the last in the list
        targetIndex = currentList.length;
        newDescription = "get ready for software demo";
        asserteditSuccess(targetIndex, currentList, newDescription);

        // invalid index
        commandBox.runCommand("edit-name " + currentList.length + 1 + " go shopping");
        assertResultMessage("The task index provided is invalid");

        // invalid command
        commandBox.runCommand("edit-desc ");
        assertResultMessage("Invalid command format! \n" + "edit-desc" + "/" + "edit-name"
                + ": Edits the description task identified by the index number used in the last task listing.\n"
                + "Parameters: INDEX (must be a positive integer) NEW_DESCRIPTION\n" + "Example: " + "edit-desc"
                + " 1 buy cake\n" + "Example: " + "edit-name" + " 1 buy cake ");
    }

    /**
     * Runs the edit command to edit the description of a task at specified
     * index and confirms the result is correct.
     * 
     * @param targetIndexOneIndexed
     *            e.g. to edit the description of first task in the list, 1
     *            should be given as the target index.
     * @param currentList
     *            A copy of the current list of tasks (before deletion).
     * @throws IllegalValueException
     */
    private void asserteditSuccess(int targetIndexOneIndexed, final TestTask[] currentList, String newDescription)
            throws IllegalValueException {
        TestTask taskToedit = currentList[targetIndexOneIndexed - 1]; // -1
                                                                      // because
                                                                      // array
                                                                      // uses
                                                                      // zero
                                                                      // indexing
        TestTask newTask = TestUtil.editTaskDescription(taskToedit, newDescription);

        commandBox.runCommand("edit-desc " + targetIndexOneIndexed + " " + newDescription);
        TestTask[] current = TestUtil.replaceTaskFromList(currentList, newTask, targetIndexOneIndexed - 1);
        // confirm the list now contains all previous tasks except the edited
        // task
        assertTrue(taskListPanel.isListMatching(current));

        // confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToedit, newTask));
    }

}
