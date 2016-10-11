package seedu.taskell.storage;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.taskell.commons.events.model.AddressBookChangedEvent;
import seedu.taskell.commons.events.storage.DataSavingExceptionEvent;
import seedu.taskell.model.TaskManager;
import seedu.taskell.model.ReadOnlyTaskManager;
import seedu.taskell.model.UserPrefs;
import seedu.taskell.storage.JsonUserPrefsStorage;
import seedu.taskell.storage.Storage;
import seedu.taskell.storage.StorageManager;
import seedu.taskell.storage.XmlAddressBookStorage;
import seedu.taskell.testutil.EventsCollector;
import seedu.taskell.testutil.TypicalTestPersons;

import java.io.IOException;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StorageManagerTest {

    private StorageManager storageManager;

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();


    @Before
    public void setup() {
        storageManager = new StorageManager(getTempFilePath("ab"), getTempFilePath("prefs"));
    }


    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + fileName;
    }


    /*
     * Note: This is an integration test that verifies the StorageManager is properly wired to the
     * {@link JsonUserPrefsStorage} class.
     * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
     */

    @Test
    public void prefsReadSave() throws Exception {
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void addressBookReadSave() throws Exception {
        TaskManager original = new TypicalTestPersons().getTypicalAddressBook();
        storageManager.saveAddressBook(original);
        ReadOnlyTaskManager retrieved = storageManager.readAddressBook().get();
        assertEquals(original, new TaskManager(retrieved));
        //More extensive testing of TaskManager saving/reading is done in XmlAddressBookStorageTest
    }

    @Test
    public void getAddressBookFilePath(){
        assertNotNull(storageManager.getAddressBookFilePath());
    }

    @Test
    public void handleAddressBookChangedEvent_exceptionThrown_eventRaised() throws IOException {
        //Create a StorageManager while injecting a stub that throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub("dummy"), new JsonUserPrefsStorage("dummy"));
        EventsCollector eventCollector = new EventsCollector();
        storage.handleAddressBookChangedEvent(new AddressBookChangedEvent(new TaskManager()));
        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlAddressBookStorageExceptionThrowingStub extends XmlAddressBookStorage{

        public XmlAddressBookStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveAddressBook(ReadOnlyTaskManager addressBook, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
