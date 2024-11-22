package com.fooberticus.steamtools.gui.frames;

import java.awt.*;
import com.fooberticus.steamtools.utils.GuiUtil;

import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author Fooberticus
 */
public class ConfigurationWindow extends JFrame {
    public ConfigurationWindow() {
        initComponents();
        apiKeyField.setText(GuiUtil.getSavedSteamHistoryKey());
    }

    public static void startConfigurationWindow() {
        new ConfigurationWindow();
    }

    private void saveSettings() {
        GuiUtil.saveSteamHistoryKey(apiKeyField.getText());
        JOptionPane.showMessageDialog(null, "Settings saved.");
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
        label2 = new JLabel();
        apiKeyField = new JTextField();
        label3 = new JLabel();
        label4 = new JLabel();
        increaseFontButton = new JButton();
        decreaseFontButton = new JButton();
        label5 = new JLabel();
        changeThemeButton = new JButton();
        closeButton = new JButton();
        saveButton = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        setTitle("Settings"); //NON-NLS
        var contentPane = getContentPane();

        //---- label1 ----
        label1.setText("Steam History API Settings"); //NON-NLS
        label1.putClientProperty("FlatLaf.styleClass", "large"); //NON-NLS

        //---- label2 ----
        label2.setText("API Key"); //NON-NLS
        label2.putClientProperty("FlatLaf.styleClass", "small"); //NON-NLS
        label2.setFont(new Font("Segoe UI", Font.PLAIN, 12)); //NON-NLS

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

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addGap(6, 6, 6)
                                    .addComponent(label2)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(apiKeyField, GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE))
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addGroup(contentPaneLayout.createParallelGroup()
                                        .addComponent(label1)
                                        .addComponent(label3))
                                    .addGap(0, 310, Short.MAX_VALUE)))
                            .addContainerGap())
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
                                .addComponent(changeThemeButton))
                            .addContainerGap(303, Short.MAX_VALUE))))
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addContainerGap(290, Short.MAX_VALUE)
                    .addComponent(saveButton)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(closeButton)
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(label1)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label2)
                        .addComponent(apiKeyField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addComponent(label3)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label4)
                        .addComponent(increaseFontButton)
                        .addComponent(decreaseFontButton))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label5)
                        .addComponent(changeThemeButton))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 109, Short.MAX_VALUE)
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
    private JLabel label2;
    private JTextField apiKeyField;
    private JLabel label3;
    private JLabel label4;
    private JButton increaseFontButton;
    private JButton decreaseFontButton;
    private JLabel label5;
    private JButton changeThemeButton;
    private JButton closeButton;
    private JButton saveButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
