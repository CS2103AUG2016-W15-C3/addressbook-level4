package seedu.taskell.testutil;

import seedu.taskell.commons.exceptions.IllegalValueException;
import seedu.taskell.model.TaskManager;
import seedu.taskell.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask archivePastEmails, borrowBooks, collectParcel, discardBooks, editPowerpoint, fixbugs, getNewUniform, holdMeeting, inspectWarehouse, 
            floatingTask_Valid, floatingTask_NonIntuitiveDescription;

    public TypicalTestTasks() {
        try {
            archivePastEmails =  new TaskBuilder().withDescription("Archieve past emails").withTaskType(Task.EVENT_TASK).withTaskPriority(TaskPriority.LOW_PRIORITY)
                    .withStartTime("12:30AM").withEndTime("12:45AM").withStartDate("1-1-2100").withEndDate("1-12-2100")
                    .withTags("friends").build();
            borrowBooks = new TaskBuilder().withDescription("Borrow books").withTaskType(Task.EVENT_TASK).withTaskPriority(TaskPriority.LOW_PRIORITY)
                    .withStartTime("12:30AM").withEndTime("12:45AM").withStartDate("1-1-2100").withEndDate("1-12-2100")
                    .withTags("owesMoney", "friends").build();
            collectParcel = new TaskBuilder().withDescription("Collect parcel").withTaskType(Task.EVENT_TASK).withStartDate("1-1-2100").withEndDate("1-12-2100").withStartTime("12:30AM").withEndTime("12:45AM").withTaskPriority(TaskPriority.LOW_PRIORITY).withTags("owesMoney").build();
            discardBooks = new TaskBuilder().withDescription("Discard books").withTaskType(Task.EVENT_TASK).withStartDate("1-1-2100").withEndDate("1-12-2100").withStartTime("12:30AM").withEndTime("12:45AM").withTaskPriority(TaskPriority.LOW_PRIORITY).build();
            editPowerpoint = new TaskBuilder().withDescription("Edit powerpoint").withTaskType(Task.EVENT_TASK).withStartDate("1-1-2100").withEndDate("1-12-2100").withStartTime("12:30AM").withEndTime("12:45AM").withTaskPriority(TaskPriority.LOW_PRIORITY).build();
            fixbugs = new TaskBuilder().withDescription("Fix bugs").withTaskType(Task.EVENT_TASK).withStartDate("1-1-2100").withEndDate("1-12-2100").withStartTime("12:30AM").withEndTime("12:45AM").withTaskPriority(TaskPriority.MEDIUM_PRIORITY).build();
            getNewUniform = new TaskBuilder().withDescription("Get new uniform").withTaskType(Task.EVENT_TASK).withStartDate("1-1-2100").withEndDate("1-12-2100").withStartTime("12:30AM").withEndTime("12:45AM").withTaskPriority(TaskPriority.MEDIUM_PRIORITY).build();

            //Manually added
            holdMeeting = new TaskBuilder().withDescription("Hold meeting").withTaskType(Task.EVENT_TASK).withStartDate("1-1-2100").withEndDate("1-12-2100").withStartTime("12:30AM").withEndTime("12:45AM").withTaskPriority(TaskPriority.MEDIUM_PRIORITY).build();
            inspectWarehouse = new TaskBuilder().withDescription("Inspect warehouse").withTaskType(Task.EVENT_TASK).withStartDate("1-1-2100").withEndDate("1-12-2100").withStartTime("12:30AM").withEndTime("12:45AM").withTaskPriority(TaskPriority.MEDIUM_PRIORITY).build();
            
            floatingTask_Valid = new TaskBuilder().withDescription("floating task").withTaskType(Task.FLOATING_TASK).withStartDate(TaskDate.DEFAULT_DATE).withEndDate(TaskDate.DEFAULT_DATE).withStartTime(TaskTime.DEFAULT_START_TIME).withEndTime(TaskTime.DEFAULT_END_TIME).withTaskPriority(TaskPriority.DEFAULT_PRIORITY).build();
            floatingTask_NonIntuitiveDescription = new TaskBuilder().withDescription("76@#$5632 on by at on").withTaskType(Task.FLOATING_TASK).withStartDate(TaskDate.DEFAULT_DATE).withEndDate(TaskDate.DEFAULT_DATE).withStartTime(TaskTime.DEFAULT_START_TIME).withEndTime(TaskTime.DEFAULT_END_TIME).withTaskPriority(TaskPriority.DEFAULT_PRIORITY).build();
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
