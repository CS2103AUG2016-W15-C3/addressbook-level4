package seedu.taskell.testutil;

import seedu.taskell.commons.exceptions.IllegalValueException;
import seedu.taskell.model.TaskManager;
import seedu.taskell.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask archivePastEmails, borrowBooks, collectParcel, discardBooks, editPowerpoint, fixbugs, getNewUniform, holdMeeting, inspectWarehouse, 
            floatingTask_Valid, floatingTask_NonIntuitiveDescription,
            deadlineTask_Valid;

    public TypicalTestTasks() {
        try {
            archivePastEmails =  new TaskBuilder().withDescription("Archieve past emails").withTaskType(Task.EVENT_TASK).withTaskPriority("0").withComplete("ture")
                    .withStartTime("12:30AM").withEndTime("12:45AM").withTaskDate("1-1-2015")
                    .withTags("friends").build();
            borrowBooks = new TaskBuilder().withDescription("Borrow books").withTaskType(Task.EVENT_TASK).withTaskPriority("0").withComplete("true")
                    .withStartTime("12:30AM").withEndTime("12:45AM").withTaskDate("1-1-2015")
                    .withTags("owesMoney", "friends").build();
            collectParcel = new TaskBuilder().withDescription("Collect parcel").withTaskType(Task.EVENT_TASK).withTaskDate("1-1-2015").withStartTime("12:30AM").withEndTime("12:45AM").withTaskPriority("0").withComplete("true").build();
            discardBooks = new TaskBuilder().withDescription("Discard books").withTaskType(Task.EVENT_TASK).withTaskDate("1-1-2015").withStartTime("12:30AM").withEndTime("12:45AM").withTaskPriority("0").withComplete("true").build();
            editPowerpoint = new TaskBuilder().withDescription("Edit powerpoint").withTaskType(Task.EVENT_TASK).withTaskDate("1-1-2015").withStartTime("12:30AM").withEndTime("12:45AM").withTaskPriority("0").withComplete("true").build();
            fixbugs = new TaskBuilder().withDescription("Fix bugs").withTaskType(Task.EVENT_TASK).withTaskDate("1-1-2015").withStartTime("12:30AM").withEndTime("12:45AM").withTaskPriority("0").withComplete("true").build();
            getNewUniform = new TaskBuilder().withDescription("Get new uniform").withTaskType(Task.EVENT_TASK).withTaskDate("1-1-2015").withStartTime("12:30AM").withEndTime("12:45AM").withTaskPriority("0").withComplete("true").build();

            //Manually added
            holdMeeting = new TaskBuilder().withDescription("Hold meeting").withTaskType(Task.EVENT_TASK).withTaskDate("1-1-2015").withStartTime("12:30AM").withEndTime("12:45AM").withTaskPriority("0").withComplete("true").build();
            inspectWarehouse = new TaskBuilder().withDescription("Inspect warehouse").withTaskType(Task.EVENT_TASK).withTaskDate("1-1-2015").withStartTime("12:30AM").withEndTime("12:45AM").withTaskPriority("0").withComplete("true").build();
            floatingTask_Valid = new TaskBuilder().withDescription("floating task").withTaskType(Task.FLOATING_TASK).withTaskDate(TaskDate.DEFAULT_DATE).withStartTime(TaskTime.DEFAULT_START_TIME).withEndTime(TaskTime.DEFAULT_END_TIME).withTaskPriority(TaskPriority.DEFAULT_PRIORITY).withComplete("true").build();
            floatingTask_NonIntuitiveDescription = new TaskBuilder().withDescription("76@#$5632 on by at on").withTaskType(Task.FLOATING_TASK).withTaskDate(TaskDate.DEFAULT_DATE).withStartTime(TaskTime.DEFAULT_START_TIME).withEndTime(TaskTime.DEFAULT_END_TIME).withTaskPriority(TaskPriority.DEFAULT_PRIORITY).withComplete("true").build();
            deadlineTask_Valid = new TaskBuilder().withDescription("deadline task").withTaskType(Task.DEADLINE_TASK).withTaskDate(TaskDate.DEFAULT_DATE).withStartTime(TaskTime.DEFAULT_START_TIME).withEndTime(TaskTime.DEFAULT_END_TIME).withTaskPriority(TaskPriority.LOW_PRIORITY).withComplete("true").build();
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
