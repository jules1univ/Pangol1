package fr.univrennes.istic.l2gen.application.gui.dialog.task;

import org.junit.Test;

import fr.univrennes.istic.l2gen.application.core.TaskStatus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests pour TaskEntry
 */
public class TaskEntryTest {

    @Test
    public void testRecordAccessorsAndEquality() {
        TaskEntry entry = new TaskEntry("1", "Chargement", TaskStatus.RUNNING);
        TaskEntry same = new TaskEntry("1", "Chargement", TaskStatus.RUNNING);
        TaskEntry other = new TaskEntry("2", "Termine", TaskStatus.SUCCESS);

        assertEquals("1", entry.id());
        assertEquals("Chargement", entry.name());
        assertEquals(TaskStatus.RUNNING, entry.status());
        assertEquals(entry, same);
        assertNotEquals(entry, other);
    }
}
