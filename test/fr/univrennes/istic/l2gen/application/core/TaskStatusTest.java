package fr.univrennes.istic.l2gen.application.core;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests pour TaskStatus enum
 */
public class TaskStatusTest {

    /**
     * Verifie que TaskStatus est un enum
     */
    @Test
    public void testTaskStatusIsEnum() {
        Class<?> taskStatusClass = TaskStatus.class;
        assertTrue("TaskStatus must be an enum", taskStatusClass.isEnum());
    }

    /**
     * Verifie que TaskStatus.RUNNING existe
     */
    @Test
    public void testTaskStatusHasRunning() {
        TaskStatus status = TaskStatus.RUNNING;
        assertNotNull("TaskStatus.RUNNING must exist", status);
        assertEquals("TaskStatus.RUNNING should have correct name", "RUNNING", status.name());
    }

    /**
     * Verifie que TaskStatus.PENDING existe
     */
    @Test
    public void testTaskStatusHasPending() {
        TaskStatus status = TaskStatus.PENDING;
        assertNotNull("TaskStatus.PENDING must exist", status);
        assertEquals("TaskStatus.PENDING should have correct name", "PENDING", status.name());
    }

    /**
     * Verifie que TaskStatus.SUCCESS existe
     */
    @Test
    public void testTaskStatusHasSuccess() {
        TaskStatus status = TaskStatus.SUCCESS;
        assertNotNull("TaskStatus.SUCCESS must exist", status);
        assertEquals("TaskStatus.SUCCESS should have correct name", "SUCCESS", status.name());
    }

    /**
     * Verifie que TaskStatus.FAILED existe
     */
    @Test
    public void testTaskStatusHasFailed() {
        TaskStatus status = TaskStatus.FAILED;
        assertNotNull("TaskStatus.FAILED must exist", status);
        assertEquals("TaskStatus.FAILED should have correct name", "FAILED", status.name());
    }

}
