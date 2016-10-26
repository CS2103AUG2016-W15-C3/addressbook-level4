package seedu.taskell.logic.parser;

import static seedu.taskell.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskell.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.taskell.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.taskell.commons.exceptions.IllegalValueException;
import seedu.taskell.commons.util.StringUtil;
import seedu.taskell.logic.commands.*;
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

    private static final Pattern KEYWORDS_ARGS_FORMAT = Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one

    private static final Pattern TASK_DATA_ARGS_FORMAT = // '/' forward slashes
                                                         // are reserved for
                                                         // delimiter prefixes
            Pattern.compile("(?<description>[^/]+)" + " (?<isTaskTypePrivate>p?)p/(?<taskType>[^/]+)"
                    + " (?<isTaskDatePrivate>p?)p/(?<startDate>[^/]+)" + " (?<isStartPrivate>p?)e/(?<startTime>[^/]+)"
                    + " (?<isEndPrivate>p?)e/(?<endTime>[^/]+)"
                    + " (?<isTaskPriorityPrivate>p?)a/(?<taskPriority>[^/]+)" + "(?<tagArguments>(?: t/[^/]+)*)"); // variable
                                                                                                                   // number
    private static final String BY = "by";
    private static final String ON = "on";
    private static final String AT = "at";
    private static final String FROM = "from";
    private static final String TO = "to";

    public Parser() {
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput
     *            full user input string
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
            UndoCommand.addCommandToHistory(userInput, commandWord);
            return prepareAdd(arguments);

        case SelectCommand.COMMAND_WORD:
            return prepareSelect(arguments);

        case DeleteCommand.COMMAND_WORD:
            UndoCommand.addCommandToHistory(userInput, commandWord);
            return prepareDelete(arguments);

        case EditStartDateCommand.COMMAND_WORD:
            UndoCommand.addCommandToHistory(userInput, commandWord);
            return prepareEditStartDate(arguments);

        case EditEndDateCommand.COMMAND_WORD:
            UndoCommand.addCommandToHistory(userInput, commandWord);
            return prepareEditEndDate(arguments);

        case EditDescriptionCommand.COMMAND_WORD_1:
            UndoCommand.addCommandToHistory(userInput, commandWord);
            return prepareEditDescription(arguments);

        case EditDescriptionCommand.COMMAND_WORD_2:
            UndoCommand.addCommandToHistory(userInput, commandWord);
            return prepareEditDescription(arguments);

        case EditStartTimeCommand.COMMAND_WORD:
            UndoCommand.addCommandToHistory(userInput, commandWord);
            return prepareEditStartTime(arguments);

        case EditEndTimeCommand.COMMAND_WORD:
            UndoCommand.addCommandToHistory(userInput, commandWord);
            return prepareEditEndTime(arguments);

        case EditPriorityCommand.COMMAND_WORD:
            UndoCommand.addCommandToHistory(userInput, commandWord);
            return prepareEditPriority(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case FindTagCommand.COMMAND_WORD:
            return prepareFindByTag(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ListPriorityCommand.COMMAND_WORD:
            return prepareListPriority(arguments);

        case UndoCommand.COMMAND_WORD:
            return prepareUndo(arguments);

        case SaveStorageLocationCommand.COMMAND_WORD:
            return prepareSaveStorageLocation(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ListUndoCommand.COMMAND_WORD:
            return new ListUndoCommand();

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    private Command prepareListPriority(String args) {
        if (args.isEmpty()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListPriorityCommand.MESSAGE_USAGE));
        }
        StringTokenizer st = new StringTokenizer(args.trim(), " ");
        String intValue = st.nextToken();
        if (st.hasMoreTokens()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListPriorityCommand.MESSAGE_USAGE));
        }
        if (!isInt(intValue)) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEndDateCommand.MESSAGE_USAGE));
        }
        int targetIdx = Integer.valueOf(intValue);
        if (targetIdx < 0 || targetIdx > 3) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_TASK_DISPLAYED_INDEX, ListPriorityCommand.MESSAGE_USAGE));
        } else
            return new ListPriorityCommand(intValue);
    }

    /**
     * Parses arguments in the context of the edit task startDate command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareEditStartDate(String args) {
        String arguments = "";
        if (args.isEmpty()) {
            UndoCommand.deletePreviousCommand();
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditStartDateCommand.MESSAGE_USAGE));
        }
        StringTokenizer st = new StringTokenizer(args.trim(), " ");
        String intValue = st.nextToken();
        if (!isInt(intValue)) {
            UndoCommand.deletePreviousCommand();
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_TASK_DISPLAYED_INDEX, EditEndDateCommand.MESSAGE_USAGE));
        }
        int targetIdx = Integer.valueOf(intValue);
        arguments = st.nextToken();
        if (st.hasMoreTokens()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditStartDateCommand.MESSAGE_USAGE));
        }
        if (!TaskDate.isValidDate(arguments)) {
            UndoCommand.deletePreviousCommand();
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditStartDateCommand.MESSAGE_USAGE));
        }

        try {
            return new EditStartDateCommand(targetIdx, arguments);
        } catch (IllegalValueException ive) {
            UndoCommand.deletePreviousCommand();
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Parses arguments in the context of the edit task description command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareEditDescription(String args) {
        String arguments = "";
        if (args.isEmpty()) {
            UndoCommand.deletePreviousCommand();
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditDescriptionCommand.MESSAGE_USAGE));
        }
        StringTokenizer st = new StringTokenizer(args.trim(), " ");
        String intValue = st.nextToken();
        if (!isInt(intValue)) {
            UndoCommand.deletePreviousCommand();
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_TASK_DISPLAYED_INDEX, EditDescriptionCommand.MESSAGE_USAGE));
        }
        int targetIdx = Integer.valueOf(intValue);
        while (st.hasMoreTokens()) {
            arguments += st.nextToken() + " ";
        }
        arguments = arguments.trim();
        try {
            return new EditDescriptionCommand(targetIdx, arguments);
        } catch (IllegalValueException ive) {
            UndoCommand.deletePreviousCommand();
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Parses arguments in the context of the edit start time command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareEditStartTime(String args) {
        String arguments = "";
        if (args.isEmpty()) {
            UndoCommand.deletePreviousCommand();
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditStartTimeCommand.MESSAGE_USAGE));
        }
        StringTokenizer st = new StringTokenizer(args.trim(), " ");
        String intValue = st.nextToken();
        if (!isInt(intValue)) {
            UndoCommand.deletePreviousCommand();
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_TASK_DISPLAYED_INDEX, EditStartTimeCommand.MESSAGE_USAGE));
        }

        int targetIdx = Integer.valueOf(intValue);
        arguments = st.nextToken();
        if (st.hasMoreTokens()) {
            UndoCommand.deletePreviousCommand();
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditStartTimeCommand.MESSAGE_USAGE));
        }

        if (!TaskTime.isValidTime(arguments)) {
            UndoCommand.deletePreviousCommand();
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditStartTimeCommand.MESSAGE_USAGE));
        }

        try {
            return new EditStartTimeCommand(targetIdx, arguments);
        } catch (IllegalValueException ive) {
            UndoCommand.deletePreviousCommand();
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Parses arguments in the context of the edit task endDate command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareEditEndDate(String args) {
        String arguments = "";
        if (args.isEmpty()) {
            UndoCommand.deletePreviousCommand();
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEndDateCommand.MESSAGE_USAGE));
        }
        StringTokenizer st = new StringTokenizer(args.trim(), " ");
        String intValue = st.nextToken();
        if (!isInt(intValue)) {
            UndoCommand.deletePreviousCommand();
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEndDateCommand.MESSAGE_USAGE));
        }
        int targetIdx = Integer.valueOf(intValue);
        arguments = st.nextToken();
        if (st.hasMoreTokens() || !TaskDate.isValidDate(arguments)) {
            UndoCommand.deletePreviousCommand();
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEndDateCommand.MESSAGE_USAGE));
        }

        try {
            return new EditEndDateCommand(targetIdx, arguments);
        } catch (IllegalValueException ive) {
            UndoCommand.deletePreviousCommand();
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Parses arguments in the context of the edit task end time command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareEditEndTime(String args) {
        String arguments = "";
        if (args.isEmpty()) {
            UndoCommand.deletePreviousCommand();
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEndTimeCommand.MESSAGE_USAGE));
        }
        StringTokenizer st = new StringTokenizer(args.trim(), " ");
        String intValue = st.nextToken();
        if (!isInt(intValue)) {
            UndoCommand.deletePreviousCommand();
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEndTimeCommand.MESSAGE_USAGE));
        }
        int targetIdx = Integer.valueOf(intValue);
        arguments = st.nextToken();
        if (st.hasMoreTokens() || !TaskTime.isValidTime(arguments)) {
            UndoCommand.deletePreviousCommand();
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEndTimeCommand.MESSAGE_USAGE));
        }

        try {
            return new EditEndTimeCommand(targetIdx, arguments);
        } catch (IllegalValueException ive) {
            UndoCommand.deletePreviousCommand();
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Parses arguments in the context of the edit task priority command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareEditPriority(String args) {
        String arguments = "";
        if (args.isEmpty()) {
            UndoCommand.deletePreviousCommand();
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPriorityCommand.MESSAGE_USAGE));
        }
        StringTokenizer st = new StringTokenizer(args.trim(), " ");
        int targetIdx = Integer.valueOf(st.nextToken());
        arguments = st.nextToken();
        if (st.hasMoreTokens() || !TaskPriority.isValidPriority(arguments)) {
            UndoCommand.deletePreviousCommand();
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPriorityCommand.MESSAGE_USAGE));
        }
        try {
            return new EditPriorityCommand(targetIdx, arguments);
        } catch (IllegalValueException ive) {
            UndoCommand.deletePreviousCommand();
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args) {
        if (args.isEmpty()) {
            UndoCommand.deletePreviousCommand();
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        ArrayList<String> argsList = tokenizeArguments(args);
        Queue<String> initialQueue = initialiseArgQueue(argsList);
        Queue<String> descriptionQueue = new LinkedList<String>();
        Queue<String> byQueue = new LinkedList<String>();
        Queue<String> onQueue = new LinkedList<String>();
        Queue<String> atQueue = new LinkedList<String>();
        Queue<String> fromQueue = new LinkedList<String>();
        Queue<String> toQueue = new LinkedList<String>();

        String description = "";
        String startDate = TaskDate.DEFAULT_DATE;
        String endDate = startDate;
        String startTime = TaskTime.DEFAULT_START_TIME;
        String endTime = TaskTime.DEFAULT_END_TIME;
        String token = "";
        String taskPriority = TaskPriority.DEFAULT_PRIORITY;
        String tagString = "";

        int priorityCount = 0;

        boolean hasStartDate = false;
        boolean hasEndDate = false;
        boolean hasStartTime = false;
        boolean hasEndTime = false;

        while (!initialQueue.isEmpty()) {
            token = initialQueue.poll().trim();
            String tempToken = "";

            if (!token.equals(BY) && !token.equals(ON) && !token.equals(AT) && !token.equals(FROM) && !token.equals(TO)
                    && !TaskDate.isValidDate(token) && !TaskTime.isValidTime(token) && !token.startsWith(Tag.PREFIX)
                    && !token.startsWith(TaskPriority.PREFIX)) {
                tempToken = flushQueue(byQueue, onQueue, atQueue, fromQueue, toQueue);
                if (!tempToken.isEmpty()) {
                    descriptionQueue.offer(tempToken);
                }
                descriptionQueue.offer(token);
                continue;
            } else if (token.equals(BY)) {
                tempToken = flushQueue(byQueue, onQueue, atQueue, fromQueue, toQueue);
                if (!tempToken.isEmpty()) {
                    descriptionQueue.offer(tempToken);
                }
                byQueue.offer(token);
                continue;
            } else if (token.equals(ON)) {
                tempToken = flushQueue(byQueue, onQueue, atQueue, fromQueue, toQueue);
                if (!tempToken.isEmpty()) {
                    descriptionQueue.offer(tempToken);
                }
                onQueue.offer(token);
                continue;
            } else if (token.equals(AT)) {
                tempToken = flushQueue(byQueue, onQueue, atQueue, fromQueue, toQueue);
                if (!tempToken.isEmpty()) {
                    descriptionQueue.offer(tempToken);
                }
                atQueue.offer(token);
                continue;
            } else if (token.equals(FROM)) {
                tempToken = flushQueue(byQueue, onQueue, atQueue, fromQueue, toQueue);
                if (!tempToken.isEmpty()) {
                    descriptionQueue.offer(tempToken);
                }
                fromQueue.offer(token);
                continue;
            } else if (token.equals(TO)) {
                tempToken = flushQueue(byQueue, onQueue, atQueue, fromQueue, toQueue);
                if (!tempToken.isEmpty()) {
                    descriptionQueue.offer(tempToken);
                }
                toQueue.offer(token);
                continue;
            } else if (token.startsWith(Tag.PREFIX)) {
                tempToken = flushQueue(byQueue, onQueue, atQueue, fromQueue, toQueue);
                if (!tempToken.isEmpty()) {
                    descriptionQueue.offer(tempToken);
                }
                tagString += " " + token;
                continue;
            } else if (token.startsWith(TaskPriority.PREFIX)) {
                tempToken = flushQueue(byQueue, onQueue, atQueue, fromQueue, toQueue);
                if (!tempToken.isEmpty()) {
                    descriptionQueue.offer(tempToken);
                }
                if (priorityCount > 0) {
                    return new IncorrectCommand(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                } else {
                    taskPriority = token.substring(token.indexOf(TaskPriority.PREFIX) + 2);
                    priorityCount++;
                }
                continue;
            } else if (TaskDate.isValidDate(token)) {
                if (byQueue.isEmpty() && onQueue.isEmpty() && atQueue.isEmpty() && fromQueue.isEmpty()
                        && toQueue.isEmpty()) {
                    descriptionQueue.offer(token);
                } else if (!onQueue.isEmpty()) {
                    if (!hasStartDate) {
                        onQueue.poll();
                        startDate = token;
                        hasStartDate = true;
                    } else {
                        descriptionQueue.offer(onQueue.poll());
                        descriptionQueue.offer(token);
                    }
                } else if (!byQueue.isEmpty()) {
                    if (!hasEndDate) {
                        byQueue.poll();
                        endDate = token;
                        hasEndDate = true;
                    } else {
                        descriptionQueue.offer(byQueue.poll());
                        descriptionQueue.offer(token);
                    }
                } else if (!atQueue.isEmpty()) {
                    descriptionQueue.offer(atQueue.poll());
                    descriptionQueue.offer(token);
                } else if (!fromQueue.isEmpty()) {
                    if (!hasStartDate) {
                        fromQueue.poll();
                        startDate = token;
                        hasStartDate = true;
                    } else {
                        descriptionQueue.offer(fromQueue.poll());
                        descriptionQueue.offer(token);
                    }
                } else if (!toQueue.isEmpty()) {
                    if (!hasEndDate) {
                        toQueue.poll();
                        endDate = token;
                        hasEndDate = true;
                    } else {
                        descriptionQueue.offer(toQueue.poll());
                        descriptionQueue.offer(token);
                    }
                }
            } else if (TaskTime.isValidTime(token)) {
                if (byQueue.isEmpty() && onQueue.isEmpty() && atQueue.isEmpty() && fromQueue.isEmpty()
                        && toQueue.isEmpty()) {
                    descriptionQueue.offer(token);
                } else if (!byQueue.isEmpty()) {
                    if (!hasEndTime) {
                        byQueue.poll();
                        endTime = token;
                        hasEndTime = true;
                    } else {
                        descriptionQueue.offer(byQueue.poll());
                        descriptionQueue.offer(token);
                    }
                } else if (!atQueue.isEmpty()) {
                    if (!hasStartTime) {
                        atQueue.poll();
                        startTime = token;
                        hasStartTime = true;
                    } else {
                        descriptionQueue.offer(atQueue.poll());
                        descriptionQueue.offer(token);
                    }
                } else if (!fromQueue.isEmpty()) {
                    if (!hasStartTime) {
                        fromQueue.poll();
                        startTime = token;
                        hasStartTime = true;
                    } else {
                        descriptionQueue.offer(fromQueue.poll());
                        descriptionQueue.offer(token);
                    }
                } else if (!toQueue.isEmpty()) {
                    if (!hasEndTime) {
                        toQueue.poll();
                        endTime = token;
                        hasEndTime = true;
                    } else {
                        descriptionQueue.offer(toQueue.poll());
                        descriptionQueue.offer(token);
                    }
                } else if (!onQueue.isEmpty()) {
                    descriptionQueue.offer(onQueue.poll());
                    descriptionQueue.offer(token);
                }
            }
        }

        String tempToken = flushQueue(byQueue, onQueue, atQueue, fromQueue, toQueue);
        if (!tempToken.isEmpty()) {
            descriptionQueue.offer(tempToken);
        }

        while (!descriptionQueue.isEmpty()) {
            description += descriptionQueue.poll() + " ";
        }
        description.trim();

        if (!hasEndDate) {
            endDate = startDate;
        }

        if ((TaskDate.isValidToday(startDate) && !hasStartTime)
                || startDate.equals(TaskDate.DEFAULT_DATE) && !hasStartTime) {
            startTime = TaskTime.getTimeNow();
        }

        if (hasStartDate || hasEndDate || hasStartTime || hasEndTime) {
            try {
                return new AddCommand(description, Task.EVENT_TASK, startDate, endDate, startTime, endTime,
                        taskPriority, getTagsFromArgs(tagString));
            } catch (IllegalValueException ive) {
                UndoCommand.deletePreviousCommand();
                return new IncorrectCommand(ive.getMessage());
            }
        } else {
            try {
                return new AddCommand(description, Task.FLOATING_TASK, startDate, endDate, startTime, endTime,
                        taskPriority, getTagsFromArgs(tagString));
            } catch (IllegalValueException ive) {
                UndoCommand.deletePreviousCommand();
                return new IncorrectCommand(ive.getMessage());
            }
        }

    }

    private String flushQueue(Queue<String> byQueue, Queue<String> onQueue, Queue<String> atQueue,
            Queue<String> fromQueue, Queue<String> toQueue) {
        String token = "";

        if (!byQueue.isEmpty()) {
            token = byQueue.poll();
        } else if (!onQueue.isEmpty()) {
            token = onQueue.poll();
        } else if (!atQueue.isEmpty()) {
            token = atQueue.poll();
        } else if (!fromQueue.isEmpty()) {
            token = fromQueue.poll();
        } else if (!toQueue.isEmpty()) {
            token = toQueue.poll();
        }

        return token;
    }

    private Queue<String> initialiseArgQueue(ArrayList<String> argsList) {
        Queue<String> argsQueue = new LinkedList<String>();
        for (String arg : argsList) {
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
        final Collection<String> tagStrings = Arrays
                .asList(tagArguments.replaceFirst(" " + Tag.PREFIX, "").split(" " + Tag.PREFIX));
        return new HashSet<>(tagStrings);
    }

    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {

        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
            UndoCommand.deletePreviousCommand();
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        return new DeleteCommand(index.get());
    }

    /**
     * Parses arguments in the context of the select task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareSelect(String args) {
        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        return new SelectCommand(index.get());
    }

    /**
     * Returns the specified index in the {@code command} IF a positive unsigned
     * integer is given as the index. Returns an {@code Optional.empty()}
     * otherwise.
     */
    private Optional<Integer> parseIndex(String command) {
        final Matcher matcher = TASK_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if (!StringUtil.isUnsignedInteger(index)) {
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }

    /**
     * Parses arguments in the context of the find task command.
     *
     * @param args
     *            string
     * @return the prepared command
     */
    private Command prepareFind(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(keywordSet);
    }

    /** @@author A0142130A **/

    /**
     * Parses arguments in the context of undo command.
     * 
     */
    private Command prepareUndo(String args) {
        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE));
        }
        return new UndoCommand(index.get());
    }

    /**
     * Parses arguments in the context of the find task by tags command.
     * 
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareFindByTag(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindTagCommand(keywordSet);

    }

    /**
     * Parses arguments in the context of the save storage location command.
     * 
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareSaveStorageLocation(String args) {
        if (args.isEmpty()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveStorageLocationCommand.MESSAGE_USAGE));
        }
        return new SaveStorageLocationCommand(args);
    }

    /** @@author **/

    private static boolean isInt(String s) {
        try {
            int i = Integer.parseInt(s);
            return true;
        }

        catch (NumberFormatException er) {
            return false;
        }
    }
}