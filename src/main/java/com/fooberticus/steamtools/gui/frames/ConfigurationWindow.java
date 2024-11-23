package com.fooberticus.steamtools.gui.frames;

import com.fooberticus.steamtools.utils.GuiUtil;

import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author Fooberticus
 */
public class ConfigurationWindow extends JFrame {
    public ConfigurationWindow() {
        initComponents();
        steamHistoryApiKeyField.setText(GuiUtil.getSavedSteamHistoryApiKey());
        steamApiKeyField.setText(GuiUtil.getSavedSteamApiKey());
        checkFields();
    }

    public static void startConfigurationWindow() {
        GuiUtil.initWindow(new ConfigurationWindow(), "Configuration");
    }

    private boolean checkFields() {
        boolean isValid = true;

        if (steamApiKeyField.getText() == null || steamApiKeyField.getText().isEmpty()) {
            steamApiKeyField.putClientProperty("JComponent.outline", "error");
            isValid = false;
        } else {
            steamApiKeyField.putClientProperty("JComponent.outline", "");
        }

        if (steamHistoryApiKeyField.getText() == null || steamHistoryApiKeyField.getText().isEmpty()) {
            steamHistoryApiKeyField.putClientProperty("JComponent.outline", "error");
            isValid = false;
        } else {
            steamHistoryApiKeyField.putClientProperty("JComponent.outline", "");
        }

        return isValid;
    }

    private void saveSettings() {
        GuiUtil.saveSteamHistoryApiKey(steamHistoryApiKeyField.getText());
        GuiUtil.saveSteamApiKey(steamApiKeyField.getText());

        if (checkFields()) {
            JOptionPane.showMessageDialog(null, "Settings saved.");
        } else {
            JOptionPane.showMessageDialog(null, "API Keys are required for the program to work correctly!");
        }
    }

    private void close() {
        dispose();
    }

    private void increaseFontSize() {
        GuiUtil.increaseGlobalFontSize();
    }

    private void decreaseFontSize() {
        GuiUtil.decreaseGlobalFontSize();
    }

    private void changeTheme() {
        GuiUtil.showThemeChangeDialog();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner non-commercial license
        label1 = new JLabel();
        steamHistoryApiKeyField = new JTextField();
        label3 = new JLabel();
        label4 = new JLabel();
        increaseFontButton = new JButton();
        decreaseFontButton = new JButton();
        label5 = new JLabel();
        changeThemeButton = new JButton();
        closeButton = new JButton();
        saveButton = new JButton();
        steamApiKeyField = new JTextField();
        label2 = new JLabel();
        label6 = new JLabel();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        setTitle("Settings"); //NON-NLS
        var contentPane = getContentPane();

        //---- label1 ----
        label1.setText("API Keys"); //NON-NLS
        label1.putClientProperty("FlatLaf.styleClass", "large"); //NON-NLS

        //---- label3 ----
        label3.setText("Appearance"); //NON-NLS
        label3.putClientProperty("FlatLaf.styleClass", "large"); //NON-NLS

        //---- label4 ----
        label4.setText("Font Size"); //NON-NLS

        //---- increaseFontButton ----
        increaseFontButton.setText("+"); //NON-NLS
        increaseFontButton.addActionListener(e -> increaseFontSize());

        //---- decreaseFontButton ----
        decreaseFontButton.setText("-"); //NON-NLS
        decreaseFontButton.addActionListener(e -> decreaseFontSize());

        //---- label5 ----
        label5.setText("Theme"); //NON-NLS

        //---- changeThemeButton ----
        changeThemeButton.setText("Change Theme"); //NON-NLS
        changeThemeButton.addActionListener(e -> changeTheme());

        //---- closeButton ----
        closeButton.setText("Close"); //NON-NLS
        closeButton.addActionListener(e -> close());

        //---- saveButton ----
        saveButton.setText("Save"); //NON-NLS
        saveButton.addActionListener(e -> saveSettings());

        //---- label2 ----
        label2.setText("Steam API"); //NON-NLS

        //---- label6 ----
        label6.setText("SteamHistory API"); //NON-NLS

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(12, 12, 12)
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addComponent(steamApiKeyField)
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addGroup(contentPaneLayout.createParallelGroup()
                                        .addComponent(label6)
                                        .addComponent(label2))
                                    .addGap(0, 0, Short.MAX_VALUE))
                                .addComponent(steamHistoryApiKeyField)))
                        .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(label1)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 265, Short.MAX_VALUE)
                            .addComponent(saveButton)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(closeButton)))
                    .addContainerGap())
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(label3)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addComponent(label4)
                                .addComponent(label5))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addComponent(increaseFontButton)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(decreaseFontButton))
                                .addComponent(changeThemeButton))))
                    .addContainerGap(340, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(label1)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(label2)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(steamApiKeyField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(label6)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(steamHistoryApiKeyField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(label3)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label4)
                        .addComponent(increaseFontButton)
                        .addComponent(decreaseFontButton))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label5, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
                        .addComponent(changeThemeButton))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(closeButton)
                        .addComponent(saveButton))
                    .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner non-commercial license
    private JLabel label1;
    private JTextField steamHistoryApiKeyField;
    private JLabel label3;
    private JLabel label4;
    private JButton increaseFontButton;
    private JButton decreaseFontButton;
    private JLabel label5;
    private JButton changeThemeButton;
    private JButton closeButton;
    private JButton saveButton;
    private JTextField steamApiKeyField;
    private JLabel label2;
    private JLabel label6;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
