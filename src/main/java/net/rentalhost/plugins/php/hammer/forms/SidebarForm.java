package net.rentalhost.plugins.php.hammer.forms;

import com.intellij.ide.BrowserUtil;

import javax.swing.*;

import net.rentalhost.plugins.php.hammer.services.ProjectService;
import net.rentalhost.plugins.php.hammer.services.SettingsService;

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

  private JCheckBox checkboxMissingSemicolon;

  private final String labelInspectionsText;
  private final String labelFixesText;

  public SidebarForm() {
    final var projectService = ProjectService.Companion.getInstance();
    final var projectState   = projectService.getSettings().getServiceInstance().getState();
    final var projectUrls    = projectService.getUrls();

    labelVersion.setText(projectState.getPluginVersion());

    buttonHome.addActionListener(e -> BrowserUtil.open(projectUrls.getHomeUrl()));
    buttonInspections.addActionListener(e -> BrowserUtil.open(projectUrls.getInspectionsUrl()));
    buttonChangelog.addActionListener(e -> BrowserUtil.open(projectUrls.getChangelogUrl()));
    buttonReview.addActionListener(e -> BrowserUtil.open(projectUrls.getReviewsUrl()));

    labelInspectionsText = labelInspections.getText();
    labelFixesText = labelFixes.getText();

    checkboxMissingSemicolon.setEnabled(projectState.getOptionMissingSemicolon());
    checkboxMissingSemicolon.addItemListener(
      e -> projectState.setOptionMissingSemicolon(checkboxMissingSemicolon.isSelected())
    );

    updateCounters(projectState);

    final Timer timer = new Timer(5000, e -> updateCounters(projectState));
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
