package net.rentalhost.plugins.php.hammer.forms;

import com.intellij.ide.BrowserUtil;
import net.rentalhost.plugins.php.hammer.services.ProjectService;
import net.rentalhost.plugins.php.hammer.services.SettingsService;

import javax.swing.*;

public final class SidebarForm {
    private final String  labelInspectionsText;
    private final String  labelFixesText;
    private       JPanel  panel;
    private       JLabel  labelTitle;
    private       JLabel  labelVersion;
    private       JButton buttonHome;
    private       JButton buttonInspections;
    private       JButton buttonChangelog;
    private       JButton buttonReview;
    private       JLabel  labelInspections;
    private       JLabel  labelFixes;

    public SidebarForm() {
        var projectService = ProjectService.Companion.getInstance();
        var projectState   = projectService.getSettings().getServiceInstance().getState();
        var projectUrls    = projectService.getUrls();

        labelVersion.setText(projectState.getPluginVersion());

        buttonHome.addActionListener(e -> BrowserUtil.open(projectUrls.getHomeUrl()));
        buttonInspections.addActionListener(e -> BrowserUtil.open(projectUrls.getInspectionsUrl()));
        buttonChangelog.addActionListener(e -> BrowserUtil.open(projectUrls.getChangelogUrl()));
        buttonReview.addActionListener(e -> BrowserUtil.open(projectUrls.getReviewsUrl()));

        labelInspectionsText = labelInspections.getText();
        labelFixesText = labelFixes.getText();

        updateCounters(projectState);

        Timer timer = new Timer(5000, e -> updateCounters(projectState));
        timer.start();
    }

    public JPanel getPanel() {
        return panel;
    }

    private void updateCounters(final SettingsService.Companion.State state) {
        labelInspections.setText(labelInspectionsText.replace("0", Long.toString(state.getCountInspections())));
        labelFixes.setText(labelFixesText.replace("0", Long.toString(state.getCountFixes())));
    }
}
