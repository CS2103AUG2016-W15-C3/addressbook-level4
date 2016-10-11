package seedu.taskell.commons.events.model;

import seedu.taskell.commons.events.BaseEvent;
import seedu.taskell.model.ReadOnlyTaskManager;

/** Indicates the TaskManager in the model has changed*/
public class AddressBookChangedEvent extends BaseEvent {

    public final ReadOnlyTaskManager data;

    public AddressBookChangedEvent(ReadOnlyTaskManager data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size() + ", number of tags " + data.getTagList().size();
    }
}