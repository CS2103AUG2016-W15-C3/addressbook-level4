package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.taskell.model.task.ReadOnlyTask;

public class TaskCardHandle extends GuiHandle {
    private static final String DESCRIPTION_FIELD_ID = "#description";

    private static final String TASK_PRIORITY_FIELD_ID = "#taskPriority";
    private static final String TASK_DATE_FIELD_ID = "#taskDate";
    private static final String TASK_START_TIME_FIELD_ID = "#startTime";
    private static final String TASK_END_TIME_FIELD_ID = "#endTime";

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

    public String getTaskPriority() {
        return getTextFromLabel(TASK_PRIORITY_FIELD_ID);
    }

    public String getTaskDate() {
        return getTextFromLabel(TASK_DATE_FIELD_ID);
    }

    public String getStartTime() {
        return getTextFromLabel(TASK_START_TIME_FIELD_ID);
    }
    
    public String getEndTime() {
        return getTextFromLabel(TASK_END_TIME_FIELD_ID);
    }

    public boolean isSameTask(ReadOnlyTask task){
        return getDescription().equals(task.getDescription().description) && getTaskDate().equals(task.getTaskDate().taskDate)
                && getStartTime().equals(task.getStartTime().taskTime) && getEndTime().equals(task.getEndTime().taskTime) 
                && getTaskPriority().equals(task.getTaskPriority().taskPriority);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getDescription().equals(handle.getDescription())
                    && getTaskPriority().equals(handle.getTaskPriority()); //TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getDescription() + " " + getTaskPriority();
    }
}
