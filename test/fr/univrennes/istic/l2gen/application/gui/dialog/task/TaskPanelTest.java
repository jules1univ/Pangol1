package fr.univrennes.istic.l2gen.application.gui.dialog.task;

import org.junit.Test;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import fr.univrennes.istic.l2gen.application.core.TaskStatus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Tests pour TaskPanel
 */
public class TaskPanelTest {

    @Test
    public void testFixedSizeAndRefresh() {
        List<String> removedIds = new ArrayList<>();
        TaskPanel panel = new TaskPanel(removedIds::add);

        assertEquals(new Dimension(320, 79), panel.getFixedSize(0));
        assertEquals(new Dimension(320, 79), panel.getFixedSize(1));
        assertEquals(new Dimension(320, 314), panel.getFixedSize(10));

        List<TaskEntry> entries = List.of(
                new TaskEntry("1", "Telechargement", TaskStatus.PENDING),
                new TaskEntry("2", "Calcul", TaskStatus.RUNNING));

        panel.refresh(entries);

        assertEquals(3, panel.getComponentCount());

        JPanel header = (JPanel) panel.getComponent(0);
        JLabel countLabel = (JLabel) header.getComponent(1);
        assertEquals("2 tasks", countLabel.getText());

        JScrollPane scrollPane = (JScrollPane) panel.getComponent(2);
        JPanel taskList = (JPanel) scrollPane.getViewport().getView();
        assertEquals(3, taskList.getComponentCount());
        assertSame(taskList, scrollPane.getViewport().getView());
    }
}
