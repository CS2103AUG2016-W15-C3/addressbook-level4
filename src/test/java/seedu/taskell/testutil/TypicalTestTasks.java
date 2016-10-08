package seedu.taskell.testutil;

import seedu.taskell.commons.exceptions.IllegalValueException;
import seedu.taskell.model.TaskManager;
import seedu.taskell.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask archivePastEmails, borrowBooks, collectParcel, discardBooks, editPowerpoint, fixbugs, getNewUniform, holdMeeting, inspectWarehouse;

    public TypicalTestTasks() {
        try {
            archivePastEmails =  new TaskBuilder().withDescription("Archive past emails")
                    .withTags("personal").build();
            borrowBooks = new TaskBuilder().withDescription("Borrow books")
                    .withTags("academic", "personal").build();
            collectParcel = new TaskBuilder().withDescription("Collect parcel").build();
            discardBooks = new TaskBuilder().withDescription("Discard books").build();
            editPowerpoint = new TaskBuilder().withDescription("Edit powerpoint").build();
            fixbugs = new TaskBuilder().withDescription("Fix bugs").build();
            getNewUniform= new TaskBuilder().withDescription("Get new uniform").build();

            //Manually added
            holdMeeting = new TaskBuilder().withDescription("Hold meeting").build();
            inspectWarehouse = new TaskBuilder().withDescription("Inspect warehouse").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {

        try {
            ab.addTask(new Task(archivePastEmails));
            ab.addTask(new Task(borrowBooks));
            ab.addTask(new Task(collectParcel));
            ab.addTask(new Task(discardBooks));
            ab.addTask(new Task(editPowerpoint));
            ab.addTask(new Task(fixbugs));
            ab.addTask(new Task(getNewUniform));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{archivePastEmails, borrowBooks, collectParcel, discardBooks, editPowerpoint, fixbugs, getNewUniform};
    }

    public TaskManager getTypicalTaskManager(){
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
