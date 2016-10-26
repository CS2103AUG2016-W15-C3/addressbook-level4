# User Guide
* [Introduction](#introduction)
* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)
* [Appendix A](#appendix-a)
* [Appendix B](#appendix-b)

## Introduction
Are you having a hard time remembering all the work you have to do? Do you have trouble finding a task manager that suits your preference for keyboard input? Well, worry no more, Taskell is here for you! <br>
Taskell will be your personal secretary. It will keep track of your daily tasks and remind you of any important dates and deadlines. What distinguishes Taskell from other task managers is that Taskell only requires a single line of command for every task input. This means that you can record each one of your tasks with just a single statement. You will no longer have to use a mouse if you do not wish to. <br>
Ready to begin life anew with a more efficient task manager? Read on to find out more!
 
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

#### Listing tasks : `list`
Formats: 
- `list` <br>
Displays a list of uncompleted tasks.<br>
- `list-date` DATE <br> 
Displays a list of all the tasks due on the specific date..<br>
- `list-done` <br>
Displays a list of completed tasks.<br>
<br><img src="images/ListToday.png" width="600"></br>
Diagram 2: Displays all the tasks to be done by today after typing list-date.
   
#### Finding tasks: `find`
Formats: <br>
-`find` KEYWORD [MORE_KEYWORDS]<br>
Displays a list of tasks with description or tag matching the keyword(s)<br>
Example: find banana milk essay<br>
-`find-tag` KEYWORD <br>
Displays list of activities with the same tag.<br>
 <br><img src="images/findReportResult.png" width="600"></br>

Diagram 3: Keying in `find` report `displays a list of tasks with description or tags that contain 'report'`

> Take Note! <br>
> The order of the keywords does not matter. e.g. chicken egg will match egg chicken.

#### Reverting to your previous action : `undo`
Format: `undo INDEX`<br> 
Example: `list-undo`, then `undo 3`, will undo third command in command history.

#### Deleting a task : `delete`
Format: `delete` INDEX<br>
 <br><img src="images/delete1.png" width="800"></br>
Diagram 4: Deletes the first task in the list.<br>

#### Marking a task as completed: `done`
Format: `done` INDEX<br>
Example: `done` 1<br>
This adds the 1st task as completed and moves it to the completed list.<br>

#### Editing a task : `edit`
Format: `edit` INDEX NEWTASK<br>
 <br> <img src="images/editCmd.png" width="600"> </br>
Diagram 5: Edits the 2nd task on the list

#### Saving Taskell Data in a Different Folder : `save` 

Format: `save FOLDERPATH`<br>
Example: `save C:/Users/Jim/Documents`

#### Clearing all entries :  `clear`
Format: `clear`  <br>

#### Exiting the program : `exit`
Format: `exit`  

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the application in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Taskell folder.
       
## Command Summary

Command | Format  
-------- | :-------- 
Add Floating Task | `add` TASK ITEM 
Add Event | `add` TASK ITEM <strong>by</strong> [DATE]
Add Event | `add` TASK ITEM <strong>by</strong> [TIME]
Add Event With Deadline | `add` TASK ITEM <strong>by</strong> [DATE][TIME]
Clear | `clear`
Delete | `delete` INDEX
Edit | `edit` INDEX NEWTASK
Find | `find` KEYWORD [MORE_KEYWORDS]
Find Tag | `find-tag` KEYWORD [MORE_KEYWORDS]
Help | `help`
List | `list`
List Given Day | `list-date` [DATE]
List Tasks Done | `list-done` [DONE]
Undo | `undo`

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
