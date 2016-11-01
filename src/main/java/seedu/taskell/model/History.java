/** @@author A0142130A **/
package seedu.taskell.model;

import java.util.ArrayList;

import seedu.taskell.model.task.Task;

/** API of History, holds command history available for undo
 */
public interface History {
    
    /** Returns list of command history */
    ArrayList<CommandHistory> getList();
    
    /** Returns list of command history text form*/
    ArrayList<String> getListCommandText();
    
    /** Clears internal history */
    void clear();
    
    /** Adds new command to history */
    void addCommand(String commandText, String commandType);
    
    /** Updates most recent command history with the relevant Task created */
    void addTask(Task task);
    
    /** Updates most recent command history with the relevant old Task (for edit commands only)*/
    void addOldTask(Task task);
    
    /** Checks if task is present in internal model */
    boolean isTaskPresent(Task task);
    
    /** Most recent command is incorrect, needs to be deleted */
    void deleteLatestCommand();

    /** Deletes a given CommandHistory */
    void deleteCommandHistory(CommandHistory commandHistory);

    /** removes Tasks no longer in system (i.e. manually deleted/cleared) */
    void updateList();
    
}
