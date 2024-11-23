package com.fooberticus.steamtools.gui.frames;

import java.awt.*;

import com.fooberticus.steamtools.models.SourceBanResponse;
import com.fooberticus.steamtools.models.SteamPlayerBansResponse;
import com.fooberticus.steamtools.models.SteamPlayerSummaryResponse;
import com.fooberticus.steamtools.utils.CustomRestClient;
import com.fooberticus.steamtools.utils.GuiUtil;
import com.fooberticus.steamtools.utils.SteamIDUtils;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.GroupLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

/**
 * @author Fooberticus
 */
@Slf4j
public class MainWindow extends JFrame {

    private final CustomRestClient client = new CustomRestClient();

    public MainWindow() {
        initComponents();
        statusTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (e.getButton() == MouseEvent.BUTTON3) {
                    statusTextArea.requestFocusInWindow();
                    statusTextArea.setText(GuiUtil.getClipboardText());
                }
            }
        });
    }

    public static void startMainWindow() {
        GuiUtil.initWindow(new MainWindow(), "Main");

        if (GuiUtil.getSavedSteamHistoryApiKey() == null || GuiUtil.getSavedSteamHistoryApiKey().isEmpty()
            || GuiUtil.getSavedSteamApiKey() == null || GuiUtil.getSavedSteamApiKey().isEmpty()) {
            ConfigurationWindow.startConfigurationWindow();
        }
    }

    private void openSettings() {
        ConfigurationWindow.startConfigurationWindow();
    }

    private void clear() {
        statusTextArea.setText("");
        statusTextArea.requestFocusInWindow();
    }

    private void checkUsers() {
        if (statusTextArea.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "While in game in TF2, open the console and type 'status'. " +
                            "Copy the text that prints in the console, then paste it into " +
                            "this text box to analyze users for cheating.");
            return;
        }

        Map<Long, String> userMap = SteamIDUtils.getUserMapFromStatusText( statusTextArea.getText() );
        if (userMap.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No user IDs found in text.");
            return;
        }

        disableButtons();

        SourceBanResponse steamHistoryResponse = client.getSourceBans( userMap.keySet() );
        SteamPlayerBansResponse steamPlayerBansResponse = client.getSteamPlayerBans( userMap.keySet() );
        SteamPlayerSummaryResponse steamPlayerSummaryResponse = client.getSteamPlayerSummaries( userMap.keySet() );

        ResultsWindow.startResultsWindow(steamHistoryResponse, steamPlayerBansResponse, steamPlayerSummaryResponse, userMap);

        enableButtons();
    }

    private void enableButtons() {
        checkUsersButton.setEnabled(true);
        clearButton.setEnabled(true);
    }

    private void disableButtons() {
        checkUsersButton.setEnabled(false);
        clearButton.setEnabled(false);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner non-commercial license
        menuBar1 = new JMenuBar();
        menu1 = new JMenu();
        menuItem1 = new JMenuItem();
        clearButton = new JButton();
        checkUsersButton = new JButton();
        label1 = new JLabel();
        scrollPane1 = new JScrollPane();
        statusTextArea = new JTextArea();

        //======== this ========
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Steam Tools"); //NON-NLS
        var contentPane = getContentPane();

        //======== menuBar1 ========
        {

            //======== menu1 ========
            {
                menu1.setText("File"); //NON-NLS

                //---- menuItem1 ----
                menuItem1.setText("Settings"); //NON-NLS
                menuItem1.addActionListener(e -> openSettings());
                menu1.add(menuItem1);
            }
            menuBar1.add(menu1);
        }
        setJMenuBar(menuBar1);

        //---- clearButton ----
        clearButton.setText("Clear"); //NON-NLS
        clearButton.addActionListener(e -> clear());

        //---- checkUsersButton ----
        checkUsersButton.setText("Check Users"); //NON-NLS
        checkUsersButton.addActionListener(e -> checkUsers());

        //---- label1 ----
        label1.setText("Status text"); //NON-NLS
        label1.putClientProperty("FlatLaf.styleClass", "medium"); //NON-NLS

        //======== scrollPane1 ========
        {

            //---- statusTextArea ----
            statusTextArea.setFont(new Font("Lucida Console", Font.PLAIN, 14)); //NON-NLS
            scrollPane1.setViewportView(statusTextArea);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                            .addGap(0, 271, Short.MAX_VALUE)
                            .addComponent(checkUsersButton)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(clearButton))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(label1)
                            .addGap(0, 410, Short.MAX_VALUE))
                        .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE))
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(label1)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(clearButton)
                        .addComponent(checkUsersButton))
                    .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner non-commercial license
    private JMenuBar menuBar1;
    private JMenu menu1;
    private JMenuItem menuItem1;
    private JButton clearButton;
    private JButton checkUsersButton;
    private JLabel label1;
    private JScrollPane scrollPane1;
    private JTextArea statusTextArea;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
