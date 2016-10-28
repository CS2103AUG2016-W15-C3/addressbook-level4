# User Guide
* [Introduction](#introduction)
* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)
* [Appendix A](#appendix-a)
* [Appendix B](#appendix-b)

<!--- @@author A0142073R ---> 

## Introduction
Are you having a hard time remembering all the work you have to do? Do you have trouble finding a task manager that suits your preference for keyboard input? Well, worry no more, Taskell is here for you! <br>
Taskell will be your personal secretary. It will keep track of your daily tasks and remind you of any important dates and deadlines. What distinguishes Taskell from other task managers is that Taskell only requires a single line of command for every task input. This means that you can record each one of your tasks with just a single statement. You will no longer have to use a mouse if you do not wish to. <br>
Ready to begin life anew with a more efficient task manager? Read on to find out more!

<!--- @@author --> 

## Quick Start

Step 1: Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

  > Having any Java 8 version is not enough. <br>
    This application will not work with earlier versions of Java 8.

Step 2: Download the latest `taskell.jar` from <a href="https://github.com/CS2103AUG2016-W15-C3/main/releases">here</a>.<br>
	<br> <img src="images/Icon.png" width="100"></br>
Step 3: Copy the file to the folder you want to use as the home folder for your Task Manager.<br>
Step 4: Double-click the file to start the application. The GUI should appear in a few seconds. <br>
 <br><img src="images/GUI.png" width="600"><br>
Diagram 1: A screenshot of the Graphical User Interface (GUI)<br>
<br>Step 5:	Type the relevant command in the command box and press <kbd>Enter</kbd> to execute it.<br>
Step 6: Some example commands you can try:<br>
   * **`list`** : displays all contacts
   * **`add`** buy MA1101R textbook today : adds a task called buy MA1101R textbook to be done by today.
   * **`delete`** 3 : deletes the 3rd task shown in the current list
   * **`exit`** : exits the application <br>
Refer to the [Features](#features) section below for details of each command.<br>


## Features

This section shows the different commands that you can use in Taskell. Words that are in UPPER_CASE are parameters. These parameters have to be in the order stated below. Words that are in italics are used to identify the parameters while words enclosed in SQUARE_BRACKETS are optional. INDEX refers to the index number shown in the most recent listing.

#### Viewing list of commands : `help`

To open the help window<br>
 Format: `help`
 
<!--- @@author A0139257X --->

#### Adding a task: `add`
To add a floating task<br>
Format: 
- `add` TASK <br>
Example: `add` read Harry Potter Book <br>

To add a deadline task<br>
> Please refer to Appendix A and B for date and time format respectively that Taskell supports. <br>

Formats:
- `add` TASK <i>by</i> [DATE] <br>
Example: add buy textbook <i>by</i> today<br>
- `add` TASK <i>by</i> [TIME]<br>
Example: add visit Sandy at her house by the seaside <i>by</i>  3.35pm<br>
- `add` TASK <i>by</i>  [DATE] <i>by</i>  [TIME] <br>
Example: `add` do lab homework <i>by</i> Friday <i>by</i> 7pm

To have a greater flexibility in the command format, Taskell supports a few natural variation such as <i>on</i> and <i>at</i>.<br>

- `add ` TASK <i>on</i> [DATE]<br>
Example: `add ` go for meeting <i>on</i> monday <br>
- `add ` TASK <i>at</i> [TIME] <br>
Example: `add ` go for meeting <i>at</i> 3pm <br>
- `add ` TASK <i>on</i> [DATE] <i>at</i> [TIME] <br>
Example: `add ` go for meeting <i>on</i> Sunday <i>at</i> 3pm <br>
- `add ` TASK <i>on</i> [DATE] <i>by</i>[TIME] <br>
Example: `add ` go for meeting <i>on</i> 1-jan <i>by</i> 3pm <br>

To add an event task<br>
Formats:
- `add ` TASK <i>on</i> [DATE] <i>startat</i> [TIME] <i>endat</i> [TIME]<br>
Example: `add ` schedule meeting <i>on</i> Thursday <i>startat</i> 1pm <i>endat</i> 9pm<br>

- `add ` TASK <i>startat</i>  [TIME]<br>
Example: `add ` concert by 2am band <i>startat</i> 7pm<br>

- `add ` TASK <i>endat</i>  [TIME]<br>
Example: `add ` netball training <i>endat</i> 7pm<br>

<!--- @@author -->

<!--- @@author A0148004Rz --->

#### Listing tasks : `list`
Formats: 
- `list` <br>
Displays a list of uncompleted tasks.<br>
- `list-all` <br>
Displays a list of all tasks.<br>
- `list-date` DATE <br> 
Displays a list of all the tasks due on the specific date.<br>
- `list-done` <br>
Displays a list of completed tasks.<br>
- `list-priority` PRIORITY<br>
Displays a list of tasks with given priority.<br>
<br><img src="images/ListToday.png" width="600"></br>
Diagram 2: Displays all the tasks to be done by today after typing list-date.

<!-- @@author -->
   
<!--- @@author A0142130A --->  

#### Finding tasks: `find`
You can use the find command to view tasks with specific keywords.<br>
Formats: <br>
-`find KEYWORD [MORE_KEYWORDS]`<br>
Displays a list of tasks with description or tags that match all the keywords.<br>
Example: `find banana milk essay`<br>
This returns all tasks with description or tags that match all keywords `banana`, `milk`, and `essay`. <br>

-`find-tag TAG [MORE_TAGS]`<br>
Displays list of tasks with the same tags.<br>
Example: `find homework essay cs2103`<br>
This returns any task with either tag `homework`, `essay`, or `cs2103`.<br>
 <br><img src="images/findReport.png" width="600"></br>
 <br><img src="images/findReportResult.png" width="600"></br>
 
 <!--- @@author ---> 

Diagram 3 and 4: Keying in `find report` displays list of tasks with report as one of the keywords in task description

> Take Note! <br>
> * The order of the keywords does not matter. e.g. `chicken egg` will match `egg chicken`.
> * Full words will be matched e.g. `chicken` will match `chickens`.
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `chicken` will match `chicken duck`

<!--- @@author A0142130A ---> 

#### Reverting previous action : `undo`
If you wish to undo your previous actions, <kbd>Enter</kbd> list-undo to see a list of 
previous commands that can be undone.<br>
Then use undo command at the specified INDEX in the list.<br>
Format: `undo INDEX`<br> 
Example: `list-undo`, then `undo 3`, will undo third command in command history.

<!--- @@author ---> 

#### Deleting a task : `delete`
To delete a task, use the delete command. This command deletes the task at a specified INDEX. The index refers to the index number shown in the most recent listing.<br>
Format: `delete INDEX`<br>
 <br><img src="images/delete1.png" width="600"></br>
Picture 5 and 6: Entering `delete 1 will delete "Arrange meeting with XYZ company".`<br>

Example: `find violin, then delete 1<br>
This deletes the 1st task in the results of the find command.

Format: `delete` INDEX<br>
 <br><img src="images/delete1.png" width="800"></br>
Diagram 4: Deletes the first task in the list.<br>

<!--- @@author A0148004Rz --->

#### Marking a task as completed: `done`
Format: `done` INDEX<br>
Example: `done` 1<br>
This adds the 1st task as completed and moves it to the completed list.<br>

<!--- @@author --->

<!--- @@author A0142073R --->

#### Editing a task : `edit`
To edit the description of a task<br>
Formats: 
- `edit-desc` INDEX NEWDESCRIPTION<br>
- `edit-name` INDEX NEWDESCRIPTION<br>
 <br> <img src="images/editCmd.png" width="600"> </br>
Diagram 5: Edits the 2nd task on the list<br>

To edit the time of a task<br>
Formats: 
- `edit-startTime` INDEX NEWTIME<br>
- `edit-endTime` INDEX NEWTIME<br>

To edit the date of a task<br>
Formats: 
- `edit-startDate` INDEX NEWDATE<br>
- `edit-endDate` INDEX NEWDATE<br>

To edit the priority of a task<br>
Formats: 
- `edit-priority` INDEX NEWDATE<br>

<!--- @@author --->

<!--- @@author A0142130A ---> 

#### Showing calendar view : `calendar` or `cal`
You can view the calendar for the week on right panel to refer to the dates when adding tasks and scheduling events.<br>
To save time, Taskell has a short form command `cal`. <br>
Format: `calendar`<br>

#### Saving the information in Taskell : `save`
You can specify the path of a folder to store Taskell's data file. Please note that you should have permissions to access the folder. <br>
If the specified directory is valid but the file is missing, for example if command is `save C:/Users/Jim/Documents/chicken`,  and `C:/Users/Jim/Documents/chicken` is valid
but `chicken` file is not created, Taskell will create the file for you.<br>
Format: `save FOLDERPATH`<br>
Example: `save C:/Users/Jim/Documents`

<!--- @@author ---> 

#### Clearing all entries :  `clear`
Format: `clear`  <br>

#### Exiting the program : `exit`
Format: `exit`  

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the application in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Taskell folder.

<!- @@author A0142073R ---> 
     
## Command Summary

Command | Format  
-------- | :-------- 
Add Floating Task | `add` TASK ITEM 
Add Event | `add` TASK ITEM <strong>by</strong> [DATE]
Add Event | `add` TASK ITEM <strong>by</strong> [TIME]
Add Event With Deadline | `add` TASK ITEM <strong>by</strong> [DATE][TIME]
Calendar View | `calendar` or `cal`
Clear | `clear`
Complete | `done` INDEX
Delete | `delete` INDEX
Edit | `edit` INDEX NEWTASK
Find | `find` KEYWORD [MORE_KEYWORDS]
Find Tag | `find-tag` KEYWORD [MORE_KEYWORDS]
Help | `help`
List Incomplete Tasks| `list`
List All Tasks | `list-all`
List Given Day | `list-date` [DATE]
List Tasks Done | `list-done` [DONE]
List Undo | `list-undo`
Undo | `undo INDEX`
Calendar View | `calendar` or `cal`

## Appendix A

Supported Date Format |   Example  
-------- | :-------- 
DD-MM-YY |1-1-16 
DD-MM-YY  | 1-1-2016 
DD-MM-YY  | 1-Jan-2016
DD-MM-YY  | 1-January-2016  
DD-MM-YY  | 1.Jan.2016
DD-MM-YY  | 1.January.2016  
MM-YY  | july-16
MM  | july
day  | today
day  | tdy
day  | tmr
day  | tomorrow
day  | thursday

## Appendix B

Supported Time Format |   Example  
-------- | :-------- 
12hour |1pm
12hour |12am
12hour |111.45pm

<!-- @@author -->