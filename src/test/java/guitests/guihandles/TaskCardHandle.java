package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.taskell.model.task.ReadOnlyTask;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String TASK_DATE_FIELD_ID = "#taskDate";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getDescription() {
        return getTextFromLabel(DESCRIPTION_FIELD_ID);
    }

    public String getTaskDate() {
        return getTextFromLabel(TASK_DATE_FIELD_ID);
    }

    public boolean isSameTask(ReadOnlyTask task){
        return getDescription().equals(task.getDescription().description)
                && getTaskDate().equals(task.getTaskDate().taskDateStandardFormat);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getDescription().equals(handle.getDescription());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
