package com.fooberticus.steamtools.gui.frames;

import java.awt.*;

import com.fooberticus.steamtools.models.*;
import com.fooberticus.steamtools.utils.CustomRestClient;
import com.fooberticus.steamtools.utils.GuiUtil;
import com.fooberticus.steamtools.utils.SteamUtils;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.GroupLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

        List<Long> userIds = SteamUtils.getUserIdsFromText( statusTextArea.getText() );
        if (userIds.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No user IDs found in text.");
            return;
        }

        disableButtons();

        SourceBanResponse steamHistoryResponse = client.getSourceBans( userIds );
        SteamPlayerBansResponse steamPlayerBansResponse = client.getSteamPlayerBans( userIds );
        SteamPlayerSummaryResponse steamPlayerSummaryResponse = client.getSteamPlayerSummaries( userIds );

        Map<Long, List<SourceBan>> sourceBanMap = new HashMap<>();
        Map<Long, SteamPlayerBan> steamPlayerBanMap = new HashMap<>();
        Map<Long, SteamPlayerSummary> steamPlayerSummaryMap = new HashMap<>();

        if (steamHistoryResponse != null
                && steamHistoryResponse.getResponse() != null
                && !steamHistoryResponse.getResponse().isEmpty()) {
            steamHistoryResponse.getResponse().forEach(response -> {
                Long id = Long.valueOf(response.getSteamID());
                sourceBanMap.computeIfAbsent(id, k -> new ArrayList<>());
                sourceBanMap.get(id).add(response);
            });
        }

        if (steamPlayerBansResponse != null
                && steamPlayerBansResponse.getPlayers() != null
                && !steamPlayerBansResponse.getPlayers().isEmpty()) {
            steamPlayerBansResponse.getPlayers().forEach(response -> {
                Long id = Long.valueOf( response.getSteamId() );
                if (response.getVACBanned() || response.getNumberOfGameBans() > 0) {
                    steamPlayerBanMap.put(id, response);
                }
            });
        }

        if (steamPlayerSummaryResponse != null
                && steamPlayerSummaryResponse.getResponse() != null
                && !steamPlayerSummaryResponse.getResponse().players().isEmpty()) {
            steamPlayerSummaryResponse.getResponse().players().forEach(response ->
                    steamPlayerSummaryMap.put( Long.valueOf( response.getSteamid() ), response ) );
        }

        ResultsWindow.startResultsWindow(sourceBanMap, steamPlayerBanMap, steamPlayerSummaryMap);

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
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(0, 271, Short.MAX_VALUE)
                            .addComponent(checkUsersButton)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(clearButton)))
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
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
    private JScrollPane scrollPane1;
    private JTextArea statusTextArea;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
