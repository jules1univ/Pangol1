package fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings.pages;

import javax.swing.JTextField;

import fr.univrennes.istic.l2gen.application.core.lang.Lang;
import fr.univrennes.istic.l2gen.application.core.notebook.NoteBookValue;
import fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings.SettingControlBuilder;
import fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings.SettingRowPanel;
import fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings.SettingSectionPanel;

public final class TextSettingsPanel extends SettingSectionPanel implements IReportSettingPanel {

    private final JTextField textField;

    public TextSettingsPanel() {
        super(Lang.get("report.setting.text"));
        textField = SettingControlBuilder.textField("");
        addRow(new SettingRowPanel("Contenu", textField));
    }

    public String getText() {
        return textField.getText();
    }

    @Override
    public NoteBookValue createNoteBook() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createNoteBook'");
    }
}
