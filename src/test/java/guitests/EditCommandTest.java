package guitests;

import org.junit.Test;

import seedu.taskell.commons.core.Messages;
import seedu.taskell.logic.commands.EditCommand;
import seedu.taskell.model.task.Description;
import seedu.taskell.testutil.TestTask;
import seedu.taskell.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.taskell.logic.commands.EditCommand.MESSAGE_EDIT_TASK_SUCCESS;

public class EditCommandTest extends TaskManagerGuiTest {

    @Test
    public void edit() {

        TestTask[] currentList = td.getTypicalTasks();
        
        //edit the first in the list
        String newDescription = "buy noodle";
        int idxOfFirstItem = 1;
        commandBox.runCommand("edit 1 " + newDescription);
        assertResultMessage("Old Task: " + currentList[idxOfFirstItem-1].getDescription() + " Tags: [personal] \nNewTask: " + newDescription + " Tags: ");

        //edit the last in the list
        int idxOfLastItem = currentList.length;
        commandBox.runCommand("edit " + idxOfLastItem + " " + newDescription);
        assertResultMessage("Old Task: " + newDescription + " Tags:  \nNewTask: " + newDescription + " Tags: ");

        //invalid index
        commandBox.runCommand("edit " + (currentList.length + 1) + " buy noodle");
        assertResultMessage("The task index provided is invalid");

    }
    
}
