# User Guide
* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

Step 1: Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This application will not work with earlier versions of Java 8.
   
Step 2: Download the latest `taskell.jar` from <a href="https://github.com/CS2103AUG2016-W15-C3/main/releases">here</a>.<br>
	<br> <img src="images/Icon.png" width="100"></br>
Step 3: Copy the file to the folder you want to use as the home folder for your Task Manager.<br>
Step 4: Double-click the file to start the application. The GUI should appear in a few seconds. <br>
 <br><img src="images/GUI.png" width="600"><br>
Picture 1: A screenshot of the Graphical User Interface (GUI)<br>
<br>Step 5:	Type the relevant command in the command box and press <kbd>Enter</kbd> to execute it.<br>
Step 6: Some example commands you can try:<br>
   * **`list`** : displays all contacts
   * **`add`** buy MA1101R textbook today : adds a task called buy MA1101R textbook to be done by today.
   * **`delete`** 3 : deletes the 3rd task shown in the current list
   * **`exit`** : exits the application <br>
Refer to the [Features](#features) section below for details of each command.<br>


## Features

**Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * The parameters have to be in the given order below.

#### Viewing list of commands : `help`

Type help and pressing <kbd>Enter</kbd> to open the help window. If you enter an incorrect command, eg. happy, the instruction for using each command will be displayed as well.<br>
 Format: `help`
 
#### Adding a task: `add`
To add a new task to Taskell, use the add command.<br>

Formats: 
- `add` TASK ITEM <br>
This format can be used to add floating tasks without any deadlines.<br>
> Example: `add` read Harry Potter Book <br>
- `add` TASK ITEM <strong>by</strong> [DATE] <br>
- `add` TASK ITEM <strong>by</strong> [TIME] <br>
These 2 formats can be used to add events held on a certain day or time.<br>
> Example: `add` buy MA1101R textbook <strong>by</strong> today <br>
> Example: `add` do CS2100 assignment <strong>by</strong> 10th-August<br>
> Example: `add` go shopping <strong>by</strong> 3pm<br>
- `add `TASK ITEM <strong>by</strong> [DATE][TIME] <br>
This format can be used to add tasks with a stipulated deadline.<br>
> Example: `add` do lab homework <strong>by</strong> Friday.7pm <br>
-  `add` TASK ITEM <strong>on</strong> [DATE] <br>
The use of word <strong>"on"</strong> gives more flexibility for you.<br>
> Example: `add` schedule meeting <strong>on</strong> Thursday <br>
- `add` TASK ITEM <strong>on</strong> [DATE][TIME] <br>
> Example: `add` meet teacher <strong>on</strong> 28-09-16 7pm <br>

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

Supported Time Format |   Example  
-------- | :-------- 
12hour |1pm
12hour |12am
12hour |111.45pm

#### Listing all tasks : `list`
To view a list of all the tasks, <kbd>Enter</kbd> list.<br>
<br>
Formats: 
- `list` <br>
Prints a list of all the uncompleted tasks.<br>
- `list` DATE <br> 
Prints a list of all the tasks due on the specific date.<br>
- `list` DONE <br>
Prints a list of all the completed tasks.<br>
<br><img src="images/ListToday.png" width="600"></br>
Picture 2: List of tasks due today printed when `list` today is keyed in.
   
#### Finding tasks: `find`
To view tasks with similar keyword, use the find command.<br>
Formats: <br>
-`find` KEYWORD [MORE_KEYWORDS]<br>
Prints a list of activities that match the keywords.<br>
Example: `find` banana milk essay:<br>
This returns any task description having keywords banana, milk, essay. <br>
-`find-tag` KEYWORD <br>
Prints list of activities with the same tag.<br>
 <br><img src="images/findReport.png" width="600"></br>
 <br><img src="images/findReportResult.png" width="600"></br>

Picture 3 and 4: Keying in `find` report displays list of tasks with report as one of the keywords in task description

> Take Note! <br>
> * The order of the keywords does not matter. e.g. `chicken egg` will match `egg chicken`.
> * Partial words will be matched e.g. `chicken` will match `chickens`.
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `chicken` will match `chicken duck`

#### Reverting previous action : `undo`
If you wish to undo your previous action, <kbd>Enter</kbd> `undo`.<br>
> Take Note! <br>
> You are only allowed to undo the last 1 command.
Format: `undo`

#### Deleting a task : `delete`
To delete a task, use the `delete` command. This command deletes the task at a specified INDEX. The index refers to the index number shown in the most recent listing.<br>
Format: `delete` INDEX<br>
 <br><img src="images/delete1.png" width="600"></br>
 <br><img src="images/delete1Result.png" width="600"></br>
Picture 5 and 6: Entering `delete` 1 will delete "Arrange meeting with XYZ company".<br>

Example: `find` violin, then `delete` 1<br>
This deletes the 1st task in the results of the find command.

#### Marking a task as completed: `done`
To mark a task as done, use the `done` command. This command removes the task from uncompleted list and adds to completed tasks.<br>
Format: `done` INDEX<br>

Example: `done` 1<br>
This adds the 1st task as completed.<br>

#### Editing a task : 
`edit`
To edit a task, use the `edit` command. This command edits the task at a specified INDEX. The index refers to the index number shown in the most recent listing.<br>
Format: `edit` INDEX NEWTASK<br>
 <br> <img src="images/editCmd.png" width="600"> </br>
Picture 7: `edit` 2 schedule meeting on wednesday: edits the 2nd task in Taskell to "schedule meeting on wednesday."

#### Saving the information in cloud syncing folder

Specify the path of a folder to store the data file of Taskell. The user should have permissions to access the folder.<br>
Format: `save` /FOLDERPATH<br>
Example: `save` f/Dropbox

#### Clearing all entries : 
`clear`
To clear all tasks, <kbd>Enter</kbd> `clear`.<br>
Format: `clear`  

#### Exiting the program : 
`exit`
To close Taskell, <kbd>Enter</kbd> exit.<br>
Format: `exit`  

>  Done already? So where are all these information saved? No need to worry, Taskell will 
>  have them saved it for you!
<br>

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
Find Tag | `find-tag` KEYWORD
List | `list`
List Given Day | `list` [DATE]
List Tasks Done | `list` [DONE]
Help | `help`
Undo | `undo`
