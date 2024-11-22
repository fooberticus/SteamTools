package com.fooberticus.steamtools.gui;

import com.fooberticus.steamtools.models.SourceBan;
import com.fooberticus.steamtools.models.SourceBanResponse;
import com.fooberticus.steamtools.utils.GuiUtil;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fooberticus.steamtools.utils.BanStates.PERMANENT;
import static com.fooberticus.steamtools.utils.BanStates.TEMP_BAN;

/**
 * @author Fooberticus
 */
@Slf4j
public class CheaterResultsWindow extends JFrame {

    private static final String STEAM_HISTORY_URI = "https://steamhistory.net/id/";

    private static final String[] HEADER_ROW = {"User Name", "Steam64 ID", "Active Bans", "Total Bans", "Steam History"};

    SourceBanResponse response;
    Map<Long, String> userMap;

    public CheaterResultsWindow(SourceBanResponse response, Map<Long, String> userMap) {
        this.response = response;
        this.userMap = userMap;
        initComponents();
        formatResults();
    }

    public static void startCheaterResultsWindow(SourceBanResponse response, Map<Long, String> userMap) {
        GuiUtil.initWindow(new CheaterResultsWindow(response, userMap), "Results");
    }

    private void formatResults() {
        List<SourceBan> banList = response.getResponse();
        Map<Long, List<SourceBan>> activeBanMap = new HashMap<>();
        Map<Long, List<SourceBan>> totalBanMap = new HashMap<>();

        banList.forEach(ban -> {
            Long id = Long.parseLong( ban.getSteamID() );
            activeBanMap.computeIfAbsent(id, k -> new ArrayList<>());
            totalBanMap.computeIfAbsent(id, k -> new ArrayList<>());

            List<SourceBan> activeList = activeBanMap.get(id);
            switch (ban.getCurrentState()) {
                case PERMANENT:
                case TEMP_BAN: activeList.add(ban); break;
            }

            List<SourceBan> totalList = totalBanMap.get(id);
            totalList.add(ban);
        });

        String[][] tableContents = new String[totalBanMap.size()][HEADER_ROW.length];

        List<Long> ids = totalBanMap.keySet().stream().toList();

        for (int i = 0; i < ids.size(); i++) {
            Long id = ids.get(i);
            String[] values = { userMap.get(id),
                    id.toString(),
                    String.valueOf( activeBanMap.get( id ).size() ),
                    String.valueOf( totalBanMap.get( id ).size() ),
                    STEAM_HISTORY_URI + id };
            System.arraycopy( values, 0, tableContents[i], 0, HEADER_ROW.length );
        }

        JTable table = new JTable(tableContents, HEADER_ROW);
        table.setEnabled(true);
        table.setDefaultEditor(Object.class, null);
        table.setShowGrid(true);

        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                JTable jTable = (JTable) event.getSource();
                Point point = event.getPoint();
                int row = jTable.rowAtPoint(point);
                if (event.getClickCount() == 2 && jTable.getSelectedRow() != -1) {
                    String url = tableContents[row][4];
                    try {
                        Desktop.getDesktop().browse(new URI( url ) );
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
        });

        scrollPane1.setViewportView(table);
    }

    private void close() {
        dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner non-commercial license
        closeButton = new JButton();
        scrollPane1 = new JScrollPane();

        //======== this ========
        setTitle("Results"); //NON-NLS
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        var contentPane = getContentPane();

        //---- closeButton ----
        closeButton.setText("Close"); //NON-NLS
        closeButton.addActionListener(e -> close());

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 861, Short.MAX_VALUE)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(closeButton)))
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(closeButton)
                    .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner non-commercial license
    private JButton closeButton;
    private JScrollPane scrollPane1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
