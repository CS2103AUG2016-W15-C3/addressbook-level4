package seedu.taskell.ui;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.taskell.MainApp;
import seedu.taskell.commons.core.ComponentManager;
import seedu.taskell.commons.core.Config;
import seedu.taskell.commons.core.LogsCenter;
import seedu.taskell.commons.events.storage.DataSavingExceptionEvent;
import seedu.taskell.commons.events.ui.ClearCommandInputEvent;
import seedu.taskell.commons.events.ui.DisplayCalendarViewEvent;
import seedu.taskell.commons.events.ui.DisplayListChangedEvent;
import seedu.taskell.commons.events.ui.JumpToListRequestEvent;
import seedu.taskell.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.taskell.commons.events.ui.ShowHelpRequestEvent;
import seedu.taskell.commons.util.StringUtil;
import seedu.taskell.logic.Logic;
import seedu.taskell.logic.commands.ClearCommand;
import seedu.taskell.model.UserPrefs;

import java.util.logging.Logger;

/**
 * The manager of the UI component.
 */
public class UiManager extends ComponentManager implements Ui {
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/cyan_folder_icon.png";

    private Logic logic;
    private Config config;
    private UserPrefs prefs;
    private MainWindow mainWindow;

    public UiManager(Logic logic, Config config, UserPrefs prefs) {
        super();
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");
        primaryStage.setTitle(config.getAppTitle());

        //Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        try {
            mainWindow = MainWindow.load(primaryStage, config, prefs, logic);
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillInnerParts();

        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    @Override
    public void stop() {
        prefs.updateLastUsedGuiSetting(mainWindow.getCurrentGuiSetting());
        mainWindow.hide();
    }

    private void showFileOperationAlertAndWait(String description, String details, Throwable cause) {
        final String content = details + ":\n" + cause.toString();
        showAlertDialogAndWait(AlertType.ERROR, "File Op Error", description, content);
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(mainWindow.getPrimaryStage(), type, title, headerText, contentText);
    }
    
    /** @@author A0142130A-reused **/
    
    Alert showAlertDialogAndWaitForConfirm(Alert.AlertType type, String title, String headerText, 
            String contentText) {
        return showAlertDialogAndWaitForConfirm(mainWindow.getPrimaryStage(), type, title, 
                headerText, contentText);
    }
    
    /** @@author **/

    private static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
                                               String contentText) {
        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add("view/TaskellCyanTheme.css");
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.showAndWait();
    }
    
    /** @@author A0142130A-reused **/
    
    private static Alert showAlertDialogAndWaitForConfirm(Stage owner, AlertType type, 
            String title, String headerText, String contentText) {
        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add("view/TaskellCyanTheme.css");
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.showAndWait();
        
        return alert;
    }
    
    /** @@author **/

    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + " " + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }
    
    /** @@author A0142130A **/
    
    private Alert showConfirmClearAlertDialogAndWait() {
        return showAlertDialogAndWaitForConfirm(Alert.AlertType.CONFIRMATION, "Clear all tasks?", 
                "Clear all tasks?", "Are you sure you wish to clear all data?");
    }
    
    /** @@author **/
    private Alert showConfirmHelpAlertDialogAndWait() {
        return showAlertDialogAndWaitForConfirm(Alert.AlertType.CONFIRMATION, "Help Command", 
                "Help", "BLALALALALALALALAL");
    }
    //==================== Event Handling Code =================================================================

    @Subscribe
    private void handleDataSavingExceptionEvent(DataSavingExceptionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showFileOperationAlertAndWait("Could not save data", "Could not save data to file", event.exception);
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Alert alert = showConfirmHelpAlertDialogAndWait();
        if (alert.getResult() == ButtonType.OK) {
            logger.info("Help");
            HelpCommand.getInstance().executeClear();
        }
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.getTaskListPanel().scrollTo(event.targetIndex);
    }
    
    /** @@author A0142130A **/
    
    @Subscribe
    private void handleDisplayList(DisplayListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        logger.info("Displaying list...");
        mainWindow.loadList(event.getList());
    }
    
    @Subscribe
    private void handleShowCalendarView(DisplayCalendarViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        logger.info("Displaying calendar view...");
        mainWindow.loadCalendarView();
    }
    
    @Subscribe
    private void handleClearCommandInput(ClearCommandInputEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Alert alert = showConfirmClearAlertDialogAndWait();
        
        if (alert.getResult() == ButtonType.OK) {
            logger.info("clearing");
            ClearCommand.getInstance().executeClear();
        }
    }
    
    /** @@author **/

}
