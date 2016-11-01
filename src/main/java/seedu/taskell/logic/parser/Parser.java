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
import seedu.taskell.logic.commands.EditCommand;
import seedu.taskell.logic.commands.list.ListAllCommand;
import seedu.taskell.logic.commands.list.ListCommand;
import seedu.taskell.logic.commands.list.ListDateCommand;
import seedu.taskell.logic.commands.list.ListDoneCommand;
import seedu.taskell.logic.commands.list.ListPriorityCommand;
import seedu.taskell.model.History;
import seedu.taskell.model.HistoryManager;
import seedu.taskell.model.tag.Tag;
import seedu.taskell.model.task.Description;
import seedu.taskell.model.task.FloatingTask;
import seedu.taskell.model.task.RecurringType;
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
                    + " (?<isTaskPriorityPrivate>p?)a/(?<taskPriority>[^/]+)"
                    + " (?<isTaskCompletePrivate>p?)a/(?<taskComplete>[^/]+)" + "(?<tagArguments>(?: t/[^/]+)*)"); // variable
                                                                                                                   // number
    private static final String BY = "by";
    private static final String ON = "on";
    private static final String AT = "at";
    private static final String FROM = "from";
    private static final String TO = "to";

    private static final String ST = "st:";
    private static final String ET = "et:";
    private static final String SD = "sd:";
    private static final String ED = "ed:";
    private static final String DESC = "desc:";
    private static final String P = "p:";

    private boolean hasChangedDescription = false;
    private boolean hasChangedStartDate = false;
    private boolean hasChangedEndDate = false;
    private boolean hasChangedStartTime = false;
    private boolean hasChangedEndTime = false;
    private boolean hasChangedPriority = false;

    private static History history;

    public Parser() {
        history = HistoryManager.getInstance();
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

        if (commandWord.equals(AddCommand.COMMAND_WORD) || commandWord.equals(DeleteCommand.COMMAND_WORD)
                || commandWord.contains(UndoCommand.EDIT)) {
            IncorrectCommand.setIsUndoableCommand(true);
            history.addCommand(userInput, commandWord);
        } else {
            IncorrectCommand.setIsUndoableCommand(false);
        }

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return prepareAdd(arguments);

        case SelectCommand.COMMAND_WORD:
            return prepareSelect(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);

        case EditCommand.COMMAND_WORD:
            return prepareEdit(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case FindTagCommand.COMMAND_WORD:
            return prepareFindByTag(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ListAllCommand.COMMAND_WORD:
            return new ListAllCommand();

        case ListDoneCommand.COMMAND_WORD:
            return new ListDoneCommand();

        case ListDateCommand.COMMAND_WORD:
            return prepareListDate(arguments);

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

        case DoneCommand.COMMAND_WORD:
            return prepareDone(arguments);

        case ViewHistoryCommand.COMMAND_WORD_1:
            return new ViewHistoryCommand();

        case ViewHistoryCommand.COMMAND_WORD_2:
            return new ViewHistoryCommand();

        case ViewCalendarCommand.COMMAND_WORD_1:
            return new ViewCalendarCommand();

        case ViewCalendarCommand.COMMAND_WORD_2:
            return new ViewCalendarCommand();

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    // @@author A0142073R

    private Command prepareListDate(String arguments) {
        if (arguments.isEmpty()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListDateCommand.MESSAGE_USAGE));
        }
        StringTokenizer st = new StringTokenizer(arguments.trim(), " ");
        String date = st.nextToken();
        if (!TaskDate.isValidDate(date)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListDateCommand.MESSAGE_USAGE));
        }

        return new ListDateCommand(date);
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
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListPriorityCommand.MESSAGE_USAGE));
        }
        int targetIdx = Integer.valueOf(intValue);
        if (targetIdx < 0 || targetIdx > 3) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_TASK_DISPLAYED_INDEX, ListPriorityCommand.MESSAGE_USAGE));
        } else
            return new ListPriorityCommand(intValue);
    }

    /**
     * Parses arguments in the context of the edit command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareEdit(String args) {
        String description = "default";
        String startDate = TaskDate.DEFAULT_DATE;
        String endDate = TaskDate.DEFAULT_DATE;
        String startTime = TaskTime.DEFAULT_START_TIME;
        String endTime = TaskTime.DEFAULT_END_TIME;
        String taskPriority = TaskPriority.DEFAULT_PRIORITY;

        if (args.isEmpty()) {
            // UndoCommand.deletePreviousCommand();
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        StringTokenizer st = new StringTokenizer(args.trim(), " ");
        String intValue = st.nextToken();
        if (!isInt(intValue)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_TASK_DISPLAYED_INDEX, EditCommand.MESSAGE_USAGE));
        }
        int targetIdx = Integer.valueOf(intValue);
        hasChangedDescription = false;
        hasChangedStartDate = false;
        hasChangedEndDate = false;
        hasChangedStartTime = false;
        hasChangedEndTime = false;
        hasChangedPriority = false;
        boolean lastChar = false;
        if (!st.hasMoreTokens()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        while (st.hasMoreTokens()) {
            String parts = st.nextToken();
            if (parts.equals(DESC)) {
                if (hasChangedDescription == true) {
                    return new IncorrectCommand(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
                }
                String desc = " ";
                while (!(parts.equals(ST) || parts.equals(ET) || parts.equals(SD)
                        || parts.equals("ed") | parts.equals(P)) && st.hasMoreTokens()) {
                    desc += (parts + " ");
                    parts = st.nextToken();
                    hasChangedDescription = true;
                }
                if (!(parts.equals(ST) || parts.equals(ET) || parts.equals(SD)
                        || parts.equals("ed") | parts.equals(P))) {
                    desc += parts;
                    lastChar = true;
                }
                desc = desc.trim();
                if (Description.isValidDescription(desc)) {
                    description = desc.substring(5);
                    hasChangedDescription = true;
                }
            }
            if (parts.equals(ST)) {
                if (hasChangedStartTime == true) {
                    return new IncorrectCommand(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
                }
                if (st.hasMoreTokens()) {
                    String startT = st.nextToken();
                    if (TaskTime.isValidTime(startT)) {
                        startTime = startT.trim();
                        hasChangedStartTime = true;
                    } else {
                        return new IncorrectCommand(
                                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskTime.MESSAGE_TASK_TIME_CONSTRAINTS));
                    }
                } else {
                    return new IncorrectCommand(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
                }
            }
            if (parts.equals(ET)) {
                if (hasChangedEndTime == true) {
                    return new IncorrectCommand(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
                }
                if (st.hasMoreTokens()) {
                    String endT = st.nextToken();
                    if (TaskTime.isValidTime(endT)) {
                        endTime = endT.trim();
                        hasChangedEndTime = true;
                    } else {
                        return new IncorrectCommand(
                                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskTime.MESSAGE_TASK_TIME_CONSTRAINTS));
                    }
                } else {
                    return new IncorrectCommand(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
                }
            }
            if (parts.equals(SD)) {
                if (hasChangedStartDate == true) {
                    return new IncorrectCommand(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
                }
                if (st.hasMoreTokens()) {
                    String startD = st.nextToken();
                    if (TaskDate.isValidDate(startD)) {
                        startDate = startD.trim();
                        hasChangedStartDate = true;
                    } else {
                        return new IncorrectCommand(
                                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskDate.MESSAGE_TASK_DATE_CONSTRAINTS));
                    }
                } else {
                    return new IncorrectCommand(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
                }
            }
            if (parts.equals(ED)) {
//                System.out.println("I am here to do end date");
                if (hasChangedEndDate == true) {
//                    System.out.println("I am trying to edit end date twice");
                    return new IncorrectCommand(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
                }
                if (st.hasMoreTokens()) {
                    String endD = st.nextToken();
                    //System.out.println("End date is "+endD);
                    if (TaskDate.isValidDate(endD)) {
//                        System.out.println(endD + " is valid.");
                        endDate = endD.trim();
                        hasChangedEndDate = true;
                    } else {
//                        System.out.println(endD + " is invalid.");
                        return new IncorrectCommand(
                                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TaskDate.MESSAGE_TASK_DATE_CONSTRAINTS));
                    }
                } else {
//                    System.out.println("There was not even an end date");
                    return new IncorrectCommand(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
                }
            }
            if (parts.equals(P)) {
                if (hasChangedPriority == true) {
                    return new IncorrectCommand(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
                }
                if (st.hasMoreTokens()) {
                    String p = st.nextToken();
                    if (TaskPriority.isValidPriority(p)) {
                        taskPriority = p.trim();
                        hasChangedPriority = true;
                    } else {
                        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                TaskPriority.MESSAGE_TASK_PRIORITY_CONSTRAINTS));
                    }
                } else {
                    return new IncorrectCommand(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
                }
            }
            if (!(parts.equals(DESC) || parts.equals(ST) || parts.equals(ET) || parts.equals(SD) || parts.equals(ED)
                    || parts.equals(P)) && lastChar == false) {
                //System.out.println("The thing got invalid because of "+parts);
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
            }
        }
        try {
            return new EditCommand(targetIdx, new Description(description), hasChangedDescription,
                    new TaskDate(startDate), hasChangedStartDate, new TaskDate(endDate), hasChangedEndDate,
                    new TaskTime(startTime), hasChangedStartTime, new TaskTime(endTime), hasChangedEndTime,
                    new TaskPriority(taskPriority), hasChangedPriority);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    // @@author

    // @@author A0139257X

    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args) {
        if (args.isEmpty()) {
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
        String recurringType = RecurringType.DEFAULT_RECURRING;
        String tagString = "";

        int priorityCount = 0;
        int recurrenceCount = 0;
        boolean hasStartDate = false;
        boolean hasEndDate = false;
        boolean hasStartTime = false;
        boolean hasEndTime = false;
        boolean hasRecurring = false;
        while (!initialQueue.isEmpty()) {
            token = initialQueue.poll().trim();
            String tempToken = "";

            if (!token.equals(BY) && !token.equals(ON) && !token.equals(AT) && !token.equals(FROM) && !token.equals(TO)
                    && !TaskDate.isValidDate(token) && !TaskTime.isValidTime(token) && !token.startsWith(Tag.PREFIX)
                    && !token.startsWith(TaskPriority.PREFIX) && !token.startsWith(RecurringType.PREFIX)) {
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
            } else if (token.startsWith(RecurringType.PREFIX)) {
                tempToken = flushQueue(byQueue, onQueue, atQueue, fromQueue, toQueue);
                if (!tempToken.isEmpty()) {
                    descriptionQueue.offer(tempToken);
                }
                if (recurrenceCount > 0) {
                    return new IncorrectCommand(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
                } else {

                    recurringType = token.substring(token.indexOf(RecurringType.PREFIX) + 2);
                    hasRecurring = true;
                    recurrenceCount++;
                }

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
            startTime = TaskTime.getTimeNow().toString();
        }

        if (hasStartDate || hasEndDate || hasStartTime || hasEndTime) {
            try {
                return new AddCommand(description, Task.EVENT_TASK, startDate, endDate, startTime, endTime,
                        taskPriority, recurringType, getTagsFromArgs(tagString));
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        } else {

            if (hasRecurring) {
                return new IncorrectCommand(FloatingTask.RECURRING_TYPE_NOT_ALLOWED);
            } else {
                try {
                    return new AddCommand(description, Task.FLOATING_TASK, startDate, endDate, startTime, endTime,
                            taskPriority, recurringType, getTagsFromArgs(tagString));
                } catch (IllegalValueException ive) {
                    return new IncorrectCommand(ive.getMessage());
                }
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
    // @@author

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
        if (args.isEmpty()) {
            return new UndoCommand();
        }

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

    // @@author A0148004R
    /**
     * Parses arguments in the context of the done task command.
     *
     * @param args
     *            full command args string
     * @return the prepared command
     */
    private Command prepareDone(String args) {
        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }

        return new DoneCommand(index.get());
    }
    // @@author

    // @@author A0142073R
    private static boolean isInt(String s) {
        try {
            int i = Integer.parseInt(s);
            return true;
        }

        catch (NumberFormatException er) {
            return false;
        }
    }
    // @@author
}