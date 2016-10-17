package seedu.taskell.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.taskell.model.task.ReadOnlyTask;
import seedu.taskell.model.task.Task;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label description;
    @FXML
    private Label taskType;
    @FXML
    private Label id;
    @FXML
    private Label taskDate;
    @FXML
    private Label taskPriority;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private Label tags;

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

    @FXML
    public void initialize() {
        taskType.setText(task.getTaskType());
        id.setText(displayedIndex + ". ");
        description.setText(task.getDescription().description);
        taskPriority.setText(task.getTaskPriority().taskPriority);
        tags.setText(task.tagsString());
        taskDate.setText(task.getTaskDate().taskDate);
        startTime.setText(task.getStartTime().taskTime);
        endTime.setText(task.getEndTime().taskTime);
    }

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
