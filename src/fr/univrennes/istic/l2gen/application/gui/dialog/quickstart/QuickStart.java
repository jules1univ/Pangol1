package fr.univrennes.istic.l2gen.application.gui.dialog.quickstart;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.AbstractButton;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

import fr.univrennes.istic.l2gen.application.core.config.Config;
import fr.univrennes.istic.l2gen.application.core.config.Lang;
import fr.univrennes.istic.l2gen.application.gui.main.MainView;
import fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings.SettingView;

public final class QuickStart {

    private static final AtomicBoolean ACTIVE = new AtomicBoolean(false);
    static final int HIGHLIGHT_PADDING = 8;
    static final int HIGHLIGHT_RADIUS = 12;
    static final int BUBBLE_MAX_WIDTH = 280;
    static final int BUBBLE_MARGIN = 12;

    static final Color HIGHLIGHT_COLOR = new Color(57, 183, 99);
    static final Color OVERLAY_COLOR = new Color(0, 0, 0, 170);

    private final MainView mainView;
    private final JLayeredPane layeredPane;
    private final OverlayPane overlay;
    private final List<Step> steps;

    private int stepIndex = 0;
    private ComponentListener resizeListener;
    private AbstractButton autoAdvanceButton;
    private ActionListener autoAdvanceListener;

    public static void maybeStart(MainView mainView) {
        if (mainView == null) {
            return;
        }
        if (!Config.getBoolean("settings.general.quickstart", true)) {
            return;
        }
        if (!ACTIVE.compareAndSet(false, true)) {
            return;
        }

        SwingUtilities.invokeLater(() -> new QuickStart(mainView).start());
    }

    private QuickStart(MainView mainView) {
        this.mainView = mainView;
        this.layeredPane = mainView.getRootPane().getLayeredPane();
        this.overlay = new OverlayPane(this);
        this.steps = buildSteps();
    }

    private void start() {
        if (layeredPane == null) {
            ACTIVE.set(false);
            return;
        }

        overlay.setBounds(0, 0, layeredPane.getWidth(), layeredPane.getHeight());
        layeredPane.add(overlay, JLayeredPane.POPUP_LAYER);
        layeredPane.revalidate();
        layeredPane.repaint();

        attachResizeListener();
        goToStep(0);
        overlay.requestFocusInWindow();
    }

    private void attachResizeListener() {
        resizeListener = new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent event) {
                updateOverlayBounds();
            }

            @Override
            public void componentMoved(ComponentEvent event) {
                updateOverlayBounds();
            }
        };
        layeredPane.addComponentListener(resizeListener);
    }

    private void updateOverlayBounds() {
        overlay.setBounds(0, 0, layeredPane.getWidth(), layeredPane.getHeight());
        overlay.updateLayout();
    }

    private List<Step> buildSteps() {
        SettingView settingView = mainView.getReportPanel().getSettingView();
        List<Step> stepList = new ArrayList<>();

        stepList.add(new Step(
                Lang.get("quickstart.step1.title"),
                Lang.get("quickstart.step1.body", Lang.get("report.add.chart")),
                () -> {
                    ensurePanelsVisible();
                    settingView.showBase();
                },
                settingView::getAddChartButton));

        stepList.add(new Step(
                Lang.get("quickstart.step2.title"),
                Lang.get("quickstart.step2.body", Lang.get("report.settings.data")),
                () -> {
                    ensurePanelsVisible();
                    settingView.showChartSettings();
                },
                settingView::getChartSettingsPanel));

        stepList.add(new Step(
                Lang.get("quickstart.step3.title"),
                Lang.get("quickstart.step3.body", Lang.get("report.next.add")),
                () -> {
                    ensurePanelsVisible();
                    settingView.showChartSettings();
                },
                () -> {
                    if (settingView.isChartSettingsVisible()) {
                        settingView.getChartNextButton().doClick();
                    }
                },
                settingView::getChartNextButton));

        return stepList;
    }

    private void ensurePanelsVisible() {
        mainView.getReportPanel().setVisible(true);
        mainView.getReportPanel().getSettingView().setVisible(true);
    }

    private void goToStep(int index) {
        if (index < 0 || index >= steps.size()) {
            return;
        }
        detachAutoAdvance();
        stepIndex = index;
        Step step = steps.get(stepIndex);
        step.onEnter().run();
        attachAutoAdvance(step, stepIndex);

        SwingUtilities.invokeLater(() -> {
            overlay.showStep(step, stepIndex + 1, steps.size());
            overlay.updateLayout();
        });
    }

    public void nextStep() {
        int nextIndex = stepIndex + 1;
        if (nextIndex < steps.size()) {
            goToStep(nextIndex);
        } else {
            finish();
        }
    }

    public void finish() {
        Config.putBoolean("settings.general.quickstart", false);
        dismiss();

        if (stepIndex >= 0 && stepIndex < steps.size()) {
            Step step = steps.get(stepIndex);
            if (step.onClose() != null) {
                step.onClose().run();
            }
        }
    }

    public void skip() {
        Config.putBoolean("settings.general.quickstart", false);
        dismiss();
    }

    private void dismiss() {
        detachAutoAdvance();
        if (resizeListener != null) {
            layeredPane.removeComponentListener(resizeListener);
        }
        layeredPane.remove(overlay);
        layeredPane.revalidate();
        layeredPane.repaint();
        ACTIVE.set(false);
    }

    private void attachAutoAdvance(Step step, int index) {
        Component target = step.targetSupplier().get();
        if (!(target instanceof AbstractButton button)) {
            return;
        }

        autoAdvanceButton = button;
        autoAdvanceListener = event -> {
            if (stepIndex != index) {
                return;
            }
            detachAutoAdvance();
            if (step.onClose() != null) {
                step.onClose().run();
            }
            if (index >= steps.size() - 1) {
                finish();
            } else {
                nextStep();
            }
        };
        button.addActionListener(autoAdvanceListener);
    }

    private void detachAutoAdvance() {
        if (autoAdvanceButton != null && autoAdvanceListener != null) {
            autoAdvanceButton.removeActionListener(autoAdvanceListener);
        }
        autoAdvanceButton = null;
        autoAdvanceListener = null;
    }
}
