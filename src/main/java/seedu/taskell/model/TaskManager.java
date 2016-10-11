package seedu.taskell.model;

import javafx.collections.ObservableList;
import seedu.taskell.model.tag.Tag;
import seedu.taskell.model.tag.UniqueTagList;
import seedu.taskell.model.task.Person;
import seedu.taskell.model.task.ReadOnlyPerson;
import seedu.taskell.model.task.UniquePersonList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskManager implements ReadOnlyTaskManager {

    private final UniquePersonList persons;
    private final UniqueTagList tags;

    {
        persons = new UniquePersonList();
        tags = new UniqueTagList();
    }

    public TaskManager() {}

    /**
     * Persons and Tags are copied into this addressbook
     */
    public TaskManager(ReadOnlyTaskManager toBeCopied) {
        this(toBeCopied.getUniquePersonList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Persons and Tags are copied into this addressbook
     */
    public TaskManager(UniquePersonList persons, UniqueTagList tags) {
        resetData(persons.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyTaskManager getEmptyAddressBook() {
        return new TaskManager();
    }

//// list overwrite operations

    public ObservableList<Person> getPersons() {
        return persons.getInternalList();
    }

    public void setPersons(List<Person> persons) {
        this.persons.getInternalList().setAll(persons);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    public void resetData(Collection<? extends ReadOnlyPerson> newPersons, Collection<Tag> newTags) {
        setPersons(newPersons.stream().map(Person::new).collect(Collectors.toList()));
        setTags(newTags);
    }

    public void resetData(ReadOnlyTaskManager newData) {
        resetData(newData.getPersonList(), newData.getTagList());
    }

//// person-level operations

    /**
     * Adds a person to the address book.
     * Also checks the new person's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     *
     * @throws UniquePersonList.DuplicatePersonException if an equivalent person already exists.
     */
    public void addPerson(Person p) throws UniquePersonList.DuplicatePersonException {
        syncTagsWithMasterList(p);
        persons.add(p);
    }

    /**
     * Ensures that every tag in this person:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncTagsWithMasterList(Person person) {
        final UniqueTagList personTags = person.getTags();
        tags.mergeFrom(personTags);

        // Create map with values = tag object references in the master list
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        for (Tag tag : tags) {
            masterTagObjects.put(tag, tag);
        }

        // Rebuild the list of person tags using references from the master list
        final Set<Tag> commonTagReferences = new HashSet<>();
        for (Tag tag : personTags) {
            commonTagReferences.add(masterTagObjects.get(tag));
        }
        person.setTags(new UniqueTagList(commonTagReferences));
    }

    public boolean removePerson(ReadOnlyPerson key) throws UniquePersonList.PersonNotFoundException {
        if (persons.remove(key)) {
            return true;
        } else {
            throw new UniquePersonList.PersonNotFoundException();
        }
    }

//// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return persons.getInternalList().size() + " persons, " + tags.getInternalList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyPerson> getPersonList() {
        return Collections.unmodifiableList(persons.getInternalList());
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniquePersonList getUniquePersonList() {
        return this.persons;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskManager // instanceof handles nulls
                && this.persons.equals(((TaskManager) other).persons)
                && this.tags.equals(((TaskManager) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags);
    }
}
