package seedu.taskell.ui;

import java.time.LocalDate;
import java.time.LocalTime;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import seedu.taskell.model.task.ReadOnlyTask;
import seedu.taskell.model.task.RecurringType;
import seedu.taskell.model.task.Task;
import seedu.taskell.model.task.TaskDate;
import seedu.taskell.model.task.TaskPriority;
import seedu.taskell.model.task.TaskStatus;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label description;
    @FXML
    private Label id;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private Label recurringType;
    @FXML
    private Label taskStatus;   
    @FXML
    private Label tags;
    @FXML
    private Pane priorityColour;

    private ReadOnlyTask task;
    private int displayedIndex;

    public TaskCard(){

    }

    public static TaskCard load(ReadOnlyTask task, int displayedIndex){
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

//@@author A0139257X
    @FXML
    public void initialize() {
        id.setText(displayedIndex + ". ");
        description.setText(task.getDescription().description);
        tags.setText(task.tagsString());
        recurringType.setText(task.getRecurringType().recurringType);
        taskStatus.setText(task.getTaskStatus().taskStatus);
        
        setDateTime();
        setDateTimeVisibility();
        setPriorityPaneColour();
        setBackgroundColour();
    }

    private void setDateTime() {
        startDate.setText(task.getStartDate().getDisplayDate());
        endDate.setText(task.getEndDate().getDisplayDate());
        startTime.setText(task.getStartTime().taskTime);
        endTime.setText(task.getEndTime().taskTime);
    }
    
    private void setDateTimeVisibility() {
        if (task.getTaskType().equals(Task.FLOATING_TASK)) {
            startDate.setVisible(false);
            endDate.setVisible(false);
            startTime.setVisible(false);
            endTime.setVisible(false);
        }
        
        if (task.getRecurringType().recurringType.equals(RecurringType.NO_RECURRING)) {
            recurringType.setVisible(false);
        }
    }
    
    private void setPriorityPaneColour() {
        if (task.getTaskPriority().taskPriority.equals(TaskPriority.HIGH_PRIORITY)) {
            priorityColour.setStyle(TaskPriority.HIGH_PRIORITY_BACKGROUND);
        } else if (task.getTaskPriority().taskPriority.equals(TaskPriority.MEDIUM_PRIORITY)) {
            priorityColour.setStyle(TaskPriority.MEDIUM_PRIORITY_BACKGROUND);
        } else if (task.getTaskPriority().taskPriority.equals(TaskPriority.LOW_PRIORITY)) {
            priorityColour.setStyle(TaskPriority.LOW_PRIORITY_BACKGROUND);
        }
    }
//@@author
    
    /** @@author A0142130A **/
    /** determines if task is complete, to set to darker colour
     *  determines if task is overdue, to change to black
     * */
    private void setBackgroundColour() {
        if (task.getTaskStatus().toString().equals(TaskStatus.FINISHED)) {
            cardPane.setStyle("-fx-background-color: #4DD0E1"); //cyan 300
        } else if (isOverdue()) {
            cardPane.setStyle("-fx-background-color: #212121"); //grey 900
            
            description.setStyle("-fx-text-fill: white");
            id.setStyle("-fx-text-fill: white");
            startDate.setStyle("-fx-text-fill: white");
            startTime.setStyle("-fx-text-fill: white");
            endDate.setStyle("-fx-text-fill: white");
            endTime.setStyle("-fx-text-fill: white");
            tags.setStyle("-fx-text-fill: white");
            
            //means priority pane not coloured, and will be black
            if (task.getTaskPriority().toString().equals(TaskPriority.NO_PRIORITY)) {
                recurringType.setStyle("-fx-text-fill: white");
                taskStatus.setStyle("-fx-text-fill: white");
            }
        }
    }

    /** checks if end date is before now OR
     *  end date and now is same, but end time is before now
     * */
    private boolean isOverdue() {
        return task.getEndDate().getLocalDate().isBefore(LocalDate.now()) 
                || (task.getEndDate().getLocalDate().isEqual(LocalDate.now()) 
                        && task.getEndTime().getLocalTime().isBefore(LocalTime.now()));
    }
    /** @@author **/
    
    public HBox getLayout() {
        return cardPane;
    }

    @Override
    public void setNode(Node node) {
        cardPane = (HBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
