package seedu.taskell.logic.parser;

import static seedu.taskell.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskell.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.taskell.commons.core.Messages;
import seedu.taskell.commons.core.UnmodifiableObservableList;
import seedu.taskell.commons.exceptions.IllegalValueException;
import seedu.taskell.commons.util.StringUtil;
import seedu.taskell.logic.commands.*;
import seedu.taskell.model.Model;
import seedu.taskell.model.task.ReadOnlyTask;
import seedu.taskell.model.tag.Tag;
import seedu.taskell.model.task.Task;
import seedu.taskell.model.task.TaskDate;
import seedu.taskell.model.task.TaskPriority;
import seedu.taskell.model.task.TaskTime;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace
    
    private static final Pattern TASK_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<description>[^/]+)"
                    + " (?<isTaskTypePrivate>p?)p/(?<taskType>[^/]+)"
                    + " (?<isTaskDatePrivate>p?)p/(?<taskDate>[^/]+)"
                    + " (?<isStartPrivate>p?)e/(?<startTime>[^/]+)"
                    + " (?<isEndPrivate>p?)e/(?<endTime>[^/]+)"
                    + " (?<isTaskPriorityPrivate>p?)a/(?<taskPriority>[^/]+)"
                    + " (?<isTaskCompletePrivate>p?)a/(?<taskComplete>[^/]+)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags

    private static final String BY = "by";
    private static final String ON = "on";
    private static final String AT = "at";
    private static final String STARTAT = "startat";
    private static final String ENDAT = "endat";
    
    public Parser() {}

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return prepareAdd(arguments);

        case SelectCommand.COMMAND_WORD:
            return prepareSelect(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);
            
        case EditDateCommand.COMMAND_WORD:
            return prepareEditDate(arguments);
            
        case EditDescriptionCommand.COMMAND_WORD:
            return prepareEditDescription(arguments);
            
        case EditStartTimeCommand.COMMAND_WORD:
            return prepareEditStart(arguments);
        
        case EditEndTimeCommand.COMMAND_WORD:
            return prepareEditEnd(arguments);
            
        case EditPriorityCommand.COMMAND_WORD:
            return prepareEditPriority(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case ListallCommand.COMMAND_WORD:
            return new ListallCommand();
        
        case ListDoneCommand.COMMAND_WORD:
            return new ListDoneCommand();
        
        case ListCommand.COMMAND_WORD:
            return new ListCommand();
        
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();
            
        case DoneCommand.COMMAND_WORD:
            return prepareDone(arguments);
        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses arguments in the context of the edit task date command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareEditDate(String args) {
        String arguments = "";
        if (args.isEmpty()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditDateCommand.MESSAGE_USAGE));
        }
        StringTokenizer st = new StringTokenizer(args.trim(), " ");
        int targetIdx = Integer.valueOf(st.nextToken());
        while (st.hasMoreTokens()) {
            arguments += st.nextToken() + " ";
        }
        if (!TaskDate.isValidDate(arguments)) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditDateCommand.MESSAGE_USAGE));
        }
        
        try{
        return new EditDateCommand(targetIdx, arguments);
        }catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    /**
     * Parses arguments in the context of the edit task description command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareEditDescription(String args) {
        String arguments = "";
        if (args.isEmpty()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditDescriptionCommand.MESSAGE_USAGE));
        }
        StringTokenizer st = new StringTokenizer(args.trim(), " ");
        int targetIdx = Integer.valueOf(st.nextToken());
        while (st.hasMoreTokens()) {
            arguments += st.nextToken() + " ";
        }
        arguments = arguments.trim();
        try{
        return new EditDescriptionCommand(targetIdx, arguments);
        }catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    /**
     * Parses arguments in the context of the edit start time command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareEditStart(String args) {
        String arguments = "";
        if (args.isEmpty()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditStartTimeCommand.MESSAGE_USAGE));
        }
        StringTokenizer st = new StringTokenizer(args.trim(), " ");
        int targetIdx = Integer.valueOf(st.nextToken());
        while (st.hasMoreTokens()) {
            arguments += st.nextToken() + " ";
        }
        arguments = arguments.trim();
        if (!TaskTime.isValidTime(arguments)) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditStartTimeCommand.MESSAGE_USAGE));
        }
        
        try{
        return new EditStartTimeCommand(targetIdx, arguments);
        }catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    /**
     * Parses arguments in the context of the edit task end time command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareEditEnd(String args) {
        String arguments = "";
        if (args.isEmpty()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEndTimeCommand.MESSAGE_USAGE));
        }
        StringTokenizer st = new StringTokenizer(args.trim(), " ");
        int targetIdx = Integer.valueOf(st.nextToken());
        while (st.hasMoreTokens()) {
            arguments += st.nextToken() + " ";
        }
        arguments = arguments.trim();
        if (!TaskTime.isValidTime(arguments)) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEndTimeCommand.MESSAGE_USAGE));
        }
        
        try{
        return new EditEndTimeCommand(targetIdx, arguments);
        }catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    /**
     * Parses arguments in the context of the edit task priority command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareEditPriority(String args) {
        String arguments = "";
        if (args.isEmpty()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPriorityCommand.MESSAGE_USAGE));
        }
        StringTokenizer st = new StringTokenizer(args.trim(), " ");
        int targetIdx = Integer.valueOf(st.nextToken());
        while (st.hasMoreTokens()) {
            arguments += st.nextToken() + " ";
        }
        arguments = arguments.trim();
        if (!TaskPriority.isValidPriority(arguments)) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPriorityCommand.MESSAGE_USAGE));
        }
        
        try{
        return new EditPriorityCommand(targetIdx, arguments);
        }catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    
    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args){
        if (args.isEmpty()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        
        ArrayList<String> argsList = tokenizeArguments(args);
        Queue<String> initialQueue = initialiseArgQueue(argsList);
        Queue<String> descriptionQueue = new LinkedList<String>();
        Queue<String> byQueue = new LinkedList<String>();
        Queue<String> onQueue = new LinkedList<String>();
        Queue<String> atQueue = new LinkedList<String>();
        Queue<String> startatQueue = new LinkedList<String>();
        Queue<String> endatQueue = new LinkedList<String>();
        Queue<String> dateTimeQueue = new LinkedList<String>();
        
        String description = "";
        String date = TaskDate.DEFAULT_DATE;
        String startTime = TaskTime.DEFAULT_START_TIME;
        String endTime = TaskTime.DEFAULT_END_TIME;
        String token = "";
        String taskPriority = TaskPriority.DEFAULT_PRIORITY;
        String tagString = "";
        
        int priorityCount = 0;
        int byCount = 0;
        int onCount = 0;
        int atCount = 0;
        int startatCount = 0;
        int endatCount = 0;
        int dateCount = 0;
        int timeCount = 0;
        
        boolean isFloating = false;
        boolean isDeadline = false;
        boolean isEvent = false;
        
        while (!initialQueue.isEmpty()) {
            token = initialQueue.poll().trim();
            String tempToken = "";
            
            if(!token.equals(BY) &&!token.equals(ON) &&!token.equals(AT)
                    && !token.equals(STARTAT) && !token.equals(ENDAT)
                    && !TaskDate.isValidDate(token) && !TaskTime.isValidTime(token)
                    && !token.startsWith(Tag.PREFIX) && !token.startsWith(TaskPriority.PREFIX)) {
                tempToken = flushQueue(byQueue, onQueue, atQueue, startatQueue, endatQueue);
                if (!tempToken.isEmpty()) {
                    descriptionQueue.offer(tempToken);
                }
                descriptionQueue.offer(token);
                continue;
            } else if (token.equals(BY)) {
                tempToken = flushQueue(byQueue, onQueue, atQueue, startatQueue, endatQueue);
                if (!tempToken.isEmpty()) {
                    descriptionQueue.offer(tempToken);
                }
                byQueue.offer(token);
                continue;
            } else if (token.equals(ON)) {
                tempToken = flushQueue(byQueue, onQueue, atQueue, startatQueue, endatQueue);
                if (!tempToken.isEmpty()) {
                    descriptionQueue.offer(tempToken);
                }
                onQueue.offer(token);
                continue;
            } else if (token.equals(AT)) {
                tempToken = flushQueue(byQueue, onQueue, atQueue, startatQueue, endatQueue);
                if (!tempToken.isEmpty()) {
                    descriptionQueue.offer(tempToken);
                }
                atQueue.offer(token);
                continue;
            } else if (token.equals(STARTAT)) {
                tempToken = flushQueue(byQueue, onQueue, atQueue, startatQueue, endatQueue);
                if (!tempToken.isEmpty()) {
                    descriptionQueue.offer(tempToken);
                }
                startatQueue.offer(token);
                continue;
            } else if (token.equals(ENDAT)) {
                tempToken = flushQueue(byQueue, onQueue, atQueue, startatQueue, endatQueue);
                if (!tempToken.isEmpty()) {
                    descriptionQueue.offer(tempToken);
                }
                endatQueue.offer(token);
                continue;
            } else if (token.startsWith(Tag.PREFIX)) {
                tempToken = flushQueue(byQueue, onQueue, atQueue, startatQueue, endatQueue);
                if (!tempToken.isEmpty()) {
                    descriptionQueue.offer(tempToken);
                }
                tagString += " " + token;
                continue;
            } else if (token.startsWith(TaskPriority.PREFIX)) {
                tempToken = flushQueue(byQueue, onQueue, atQueue, startatQueue, endatQueue);
                if (!tempToken.isEmpty()) {
                    descriptionQueue.offer(tempToken);
                }
                if (priorityCount > 0) {
                    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                } else {
                    taskPriority = token.substring(token.indexOf(TaskPriority.PREFIX)+2);
                    priorityCount++;
                }
                continue;
            } else if (TaskDate.isValidDate(token)) {
                if (byQueue.isEmpty() && onQueue.isEmpty() && atQueue.isEmpty()
                        && startatQueue.isEmpty() && endatQueue.isEmpty()) {
                    descriptionQueue.offer(token);  //because maybe people wants to add task with serial number that has format date
                } else if (!onQueue.isEmpty()) {
                    dateTimeQueue.offer(onQueue.poll());
                    dateTimeQueue.offer(token);
                } else if (!byQueue.isEmpty()) {
                    dateTimeQueue.offer(byQueue.poll());
                    dateTimeQueue.offer(token);
                } else if (!atQueue.isEmpty()) {
                    descriptionQueue.offer(atQueue.poll());
                    descriptionQueue.offer(token);
                } else if (!startatQueue.isEmpty()) {
                    descriptionQueue.offer(startatQueue.poll());
                    descriptionQueue.offer(token);
                } else if (!endatQueue.isEmpty()) {
                    descriptionQueue.offer(startatQueue.poll());
                    descriptionQueue.offer(token);
                }
            } else if (TaskTime.isValidTime(token)) {
                if (byQueue.isEmpty() && onQueue.isEmpty() && atQueue.isEmpty()
                        && startatQueue.isEmpty() && endatQueue.isEmpty()) {
                    descriptionQueue.offer(token);  //because maybe people wants to add task with serial number that has format date
                } else if (!byQueue.isEmpty()) {
                    dateTimeQueue.offer(byQueue.poll());
                    dateTimeQueue.offer(token);
                } else if (!atQueue.isEmpty()) {
                    dateTimeQueue.offer(atQueue.poll());
                    dateTimeQueue.offer(token);
                } else if (!startatQueue.isEmpty()) {
                    dateTimeQueue.offer(startatQueue.poll());
                    dateTimeQueue.offer(token);
                } else if (!endatQueue.isEmpty()) {
                    dateTimeQueue.offer(endatQueue.poll());
                    dateTimeQueue.offer(token);
                } else if (!onQueue.isEmpty()) {
                    descriptionQueue.offer(onQueue.poll());
                    descriptionQueue.offer(token);
                }
            }
        }
        
        //Takes care of trailing keywords at end of input not accompanied by date/time
        if (!byQueue.isEmpty()) {
            descriptionQueue.offer(byQueue.poll());
        }
        if(!onQueue.isEmpty()) {
            descriptionQueue.offer(onQueue.poll());
        }
        if(!startatQueue.isEmpty()) {
            descriptionQueue.offer(startatQueue.poll());
        }
        if(!endatQueue.isEmpty()) {
            descriptionQueue.offer(endatQueue.poll());
        }
        
        while (!descriptionQueue.isEmpty()) {
            description += descriptionQueue.poll() + " ";
        }
        description.trim();
        
        if (dateTimeQueue.isEmpty()) {
            isFloating = true;
            try {
                return new AddCommand(
                        description,
                        Task.FLOATING_TASK,
                        TaskDate.DEFAULT_DATE,
                        TaskTime.DEFAULT_START_TIME,
                        TaskTime.DEFAULT_END_TIME,
                        taskPriority,
                        getTagsFromArgs(tagString));
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        }
        
        String dateTimeDelimiter = "";
        while (!dateTimeQueue.isEmpty()) {
            String tempToken = dateTimeQueue.poll();
            
            if (tempToken.equals(BY)) {
                byCount++;
                dateTimeDelimiter = BY;
            } else if (tempToken.equals(ON)) {
                onCount++;
                dateTimeDelimiter = ON;
            } else if (tempToken.equals(AT)) {
                atCount++;
                dateTimeDelimiter = AT;
            } else if (tempToken.equals(STARTAT)) {
                startatCount++;
                dateTimeDelimiter = STARTAT;
            } else if (tempToken.equals(ENDAT)) {
                endatCount++;
                dateTimeDelimiter = ENDAT;
            } else if (TaskDate.isValidDate(tempToken)) {
                dateCount++;
                date = tempToken;
            } else if (TaskTime.isValidTime(tempToken)) {
                timeCount++;
                
                if (dateTimeDelimiter.equals(BY)) {
                    endTime = tempToken;
                } else if (dateTimeDelimiter.equals(AT)) {
                    endTime = tempToken;
                } else if (dateTimeDelimiter.equals(STARTAT)) {
                    startTime = tempToken;
                } else if (dateTimeDelimiter.equals(ENDAT)) {
                    endTime = tempToken;
                }
            }
        }
        
        if (onCount > 1 || atCount > 1 || startatCount > 1 || endatCount > 1 || dateCount > 1 || timeCount > 2
                || (byCount > 0 && timeCount >1)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        
        if (startatCount == 1 || endatCount == 1) {
            isEvent = true;
            try {
                return new AddCommand(
                        description,
                        Task.EVENT_TASK,
                        date,
                        startTime,
                        endTime,
                        taskPriority,
                        getTagsFromArgs(tagString));
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        } else {
            isDeadline = true;
            try {
                return new AddCommand(
                        description,
                        Task.DEADLINE_TASK,
                        date,
                        startTime,
                        endTime,
                        taskPriority,
                        getTagsFromArgs(tagString));
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        }
    }
    
    //At any one point, at most one of these queue can have at most one token
    //Flush to descriptionQueue
    private String flushQueue(Queue<String> byQueue, Queue<String> onQueue, Queue<String> atQueue,
            Queue<String> startatQueue, Queue<String> endatQueue) {
        String token = "";
        
        if (!byQueue.isEmpty()) {
            token = byQueue.poll();
        } else if (!onQueue.isEmpty()) {
            token = onQueue.poll();
        } else if (!atQueue.isEmpty()) {
            token = atQueue.poll();
        } else if (!startatQueue.isEmpty()) {
            token = startatQueue.poll();
        } else if (!endatQueue.isEmpty()) {
            token = endatQueue.poll();
        }
        
        return token;
    }
    
    private Queue<String> initialiseArgQueue(ArrayList<String> argsList) {
        Queue<String> argsQueue = new LinkedList<String>();
        for (String arg: argsList) {
            argsQueue.offer(arg);
        }
        return argsQueue;
    }
    
    private ArrayList<String> tokenizeArguments(String args) {
        ArrayList<String> argsList = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(args, " ");
        while (st.hasMoreTokens()) {
            argsList.add(st.nextToken());
        }
        return argsList;
    }
    
    /**
     * Extracts the new task's tags from the add command's tag arguments string.
     * Merges duplicate tag strings.
     */
    private static Set<String> getTagsFromArgs(String tagArguments) throws IllegalValueException {
        // no tags
        if (tagArguments.isEmpty()) {
            return Collections.emptySet();
        }
        // replace first delimiter prefix, then split
        final Collection<String> tagStrings = Arrays.asList(tagArguments.replaceFirst(" " + Tag.PREFIX, "").split(" " + Tag.PREFIX));
        return new HashSet<>(tagStrings);
    }

    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {

        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        return new DeleteCommand(index.get());
    }

    /**
     * Parses arguments in the context of the select task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareSelect(String args) {
        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        return new SelectCommand(index.get());
    }

    /**
     * Returns the specified index in the {@code command} IF a positive unsigned integer is given as the index.
     *   Returns an {@code Optional.empty()} otherwise.
     */
    private Optional<Integer> parseIndex(String command) {
        final Matcher matcher = TASK_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if(!StringUtil.isUnsignedInteger(index)){
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }

    /**
     * Parses arguments in the context of the find task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareFind(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(keywordSet);
    }
    
        
    /**
     * Parses arguments in the context of the done task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDone(String args){
        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }

        return new DoneCommand(index.get());
    }

}