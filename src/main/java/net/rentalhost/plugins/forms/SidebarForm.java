package net.rentalhost.plugins.forms;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.project.Project;

import javax.swing.*;

import net.rentalhost.plugins.services.SettingsService;
import net.rentalhost.plugins.services.UrlService;

public final class SidebarForm {
    private JPanel panel;

    private JLabel labelTitle;
    private JLabel labelVersion;

    private JButton buttonHome;
    private JButton buttonInspections;
    private JButton buttonChangelog;
    private JButton buttonReview;

    private JLabel labelInspections;
    private JLabel labelFixes;

    private final String labelInspectionsText;
    private final String labelFixesText;

    public SidebarForm(final Project project) {
        final SettingsService.Companion.State state = SettingsService.Companion.getInstance().getState();

        labelVersion.setText(state.getPluginVersion());

        buttonHome.addActionListener(e -> BrowserUtil.open(UrlService.homeUrl));
        buttonInspections.addActionListener(e -> BrowserUtil.open(UrlService.inspectionsUrl));
        buttonChangelog.addActionListener(e -> BrowserUtil.open(UrlService.changelogUrl));
        buttonReview.addActionListener(e -> BrowserUtil.open(UrlService.reviewsUrl));

        labelInspectionsText = labelInspections.getText();
        labelFixesText = labelFixes.getText();

        updateCounters(state);

        final Timer timer = new Timer(5000, e -> updateCounters(state));
        timer.start();
    }

    public JPanel getPanel() {
        return panel;
    }

    private void updateCounters(final SettingsService.Companion.State state) {
        labelInspections.setText(labelInspectionsText + state.getCountInspections());
        labelFixes.setText(labelFixesText + state.getCountFixes());
    }
}
