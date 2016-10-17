package seedu.taskell.testutil;

import seedu.taskell.commons.exceptions.IllegalValueException;
import seedu.taskell.model.TaskManager;
import seedu.taskell.model.task.*;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice =  new TaskBuilder().withDescription("Alice Pauline").withTaskType(Task.EVENT_TASK).withTaskPriority("123, Jurong West Ave 6, #08-111")
                    .withStartTime("start@time.com").withEndTime("start@end.com").withTaskDate("85355255")
                    .withTags("friends").build();
            benson = new TaskBuilder().withDescription("Benson Meier").withTaskType(Task.EVENT_TASK).withTaskPriority("311, Clementi Ave 2, #02-25")
                    .withStartTime("start@time.com").withEndTime("start@end.com").withTaskDate("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withDescription("Carl Kurz").withTaskType(Task.EVENT_TASK).withTaskDate("95352563").withStartTime("start@time.com").withEndTime("start@end.com").withTaskPriority("wall street").build();
            daniel = new TaskBuilder().withDescription("Daniel Meier").withTaskType(Task.EVENT_TASK).withTaskDate("87652533").withStartTime("start@time.com").withEndTime("start@end.com").withTaskPriority("10th street").build();
            elle = new TaskBuilder().withDescription("Elle Meyer").withTaskType(Task.EVENT_TASK).withTaskDate("9482224").withStartTime("start@time.com").withEndTime("start@end.com").withTaskPriority("michegan ave").build();
            fiona = new TaskBuilder().withDescription("Fiona Kunz").withTaskType(Task.EVENT_TASK).withTaskDate("9482427").withStartTime("start@time.com").withEndTime("start@end.com").withTaskPriority("little tokyo").build();
            george = new TaskBuilder().withDescription("George Best").withTaskType(Task.EVENT_TASK).withTaskDate("9482442").withStartTime("start@time.com").withEndTime("start@end.com").withTaskPriority("4th street").build();

            //Manually added
            hoon = new TaskBuilder().withDescription("Hoon Meier").withTaskType(Task.EVENT_TASK).withTaskDate("8482424").withStartTime("start@time.com").withEndTime("start@end.com").withTaskPriority("little india").build();
            ida = new TaskBuilder().withDescription("Ida Mueller").withTaskType(Task.EVENT_TASK).withTaskDate("8482131").withStartTime("start@time.com").withEndTime("start@end.com").withTaskPriority("chicago ave").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {

        try {
            ab.addTask(new Task(alice));
            ab.addTask(new Task(benson));
            ab.addTask(new Task(carl));
            ab.addTask(new Task(daniel));
            ab.addTask(new Task(elle));
            ab.addTask(new Task(fiona));
            ab.addTask(new Task(george));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskManager getTypicalTaskManager(){
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
