package seedu.taskell.logic.parser;

import static seedu.taskell.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskell.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.taskell.commons.exceptions.IllegalValueException;
import seedu.taskell.commons.util.StringUtil;
import seedu.taskell.logic.commands.*;
import seedu.taskell.model.task.Task;
import seedu.taskell.model.task.TaskDate;

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

    public Parser() {
    }

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

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command
     * @throws IllegalValueException
     */
    private Command prepareAdd(String argsOriginal) {
        if (argsOriginal == null) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        String HASHTAG = "t/";
        String DATETIME_DELIMITER_BY = "by";
        String stringOfTags = "";
        String description = "";
        String argsNew;
        ArrayList<String> argsArr = partitionArg(argsOriginal);
        TaskDate taskDate = null;

        try {
            if (Task.isValidTask(argsArr)) {
                if (argsArr.contains(Task.DATE_TIME_DELIMITER)) {
                    int idxOfBy = argsArr.indexOf(Task.DATE_TIME_DELIMITER);

                    for (int i = argsArr.indexOf(Task.DATE_TIME_DELIMITER) + 1; i < argsArr.size(); i++) {
                        if (TaskDate.isValidDate(argsArr.get(i))) {
                            taskDate = extractDate(argsArr, idxOfBy + 1);
                            break;
                        }
                    }

                } else {
                    String today = TaskDate.getTodayDate();
                    taskDate = new TaskDate(today, TaskDate.FULL_DATE_DISPLAY);
                }
            } else {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }

            for (String token : argsArr) {
                if (TaskDate.isValidDate(token)) {
                    return new IncorrectCommand(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, Task.DEADlINE_CONSTRAINTS));
                } else if (token.startsWith(HASHTAG)) {
                    stringOfTags += " " + token;
                } else if (!token.equals(DATETIME_DELIMITER_BY)) {
                    description += " " + token;
                }
            }

            if (description.isEmpty()) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }
            description = description.replaceAll("\\s+", " ").trim();

            return new AddCommand(description, taskDate.taskDateStandardFormat, getTagsFromArgs(stringOfTags));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Get tokens of a given string delimited by whitespaces
     */
    private ArrayList<String> partitionArg(String args) {
        ArrayList<String> argsArr = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(args, " ");
        while (st.hasMoreTokens()) {
            argsArr.add(st.nextToken());
        }
        return argsArr;
    }

    /**
     * Get date from an arrayList of tokens and remove it from the list
     */
    private TaskDate extractDate(ArrayList<String> argsArr, int i) throws IllegalValueException {
        TaskDate taskDate = null;
        String token = argsArr.get(i).trim();

        if (TaskDate.isValidFullDate(token)) {
            taskDate = new TaskDate(token);
            argsArr.remove(i);
        } else if (TaskDate.isValidDayAndMonth(token)) {
            taskDate = new TaskDate(token, TaskDate.ONLY_CONTAIN_DAY_AND_MONTH);
            argsArr.remove(i);
        } else if (TaskDate.isValidMonthAndYear(token)) {
            taskDate = new TaskDate(token, TaskDate.ONLY_CONTAIN_MONTH_AND_YEAR);
            argsArr.remove(i);
        } else if (TaskDate.isValidMonth(token)) {
            taskDate = new TaskDate(token, TaskDate.ONLY_CONTAIN_MONTH);
            argsArr.remove(i);
        } else if (TaskDate.isValidDayOfWeek(token)) {
            taskDate = new TaskDate(token, TaskDate.ONLY_CONTAIN_DAY_NAME_IN_WEEK);
            argsArr.remove(i);
        } else if (TaskDate.isValidTomorrow(token)) {
            taskDate = new TaskDate(token, TaskDate.TOMORROW);
            argsArr.remove(i);
        } else if (TaskDate.isValidToday(token)) {
            taskDate = new TaskDate(token, TaskDate.TODAY);
            argsArr.remove(i);
        }

        return taskDate;
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
        final Collection<String> tagStrings = Arrays.asList(tagArguments.replaceFirst(" t/", "").split(" t/"));
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
     *            full command args string
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

}