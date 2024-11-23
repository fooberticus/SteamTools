package com.fooberticus.steamtools.gui.panels;

import com.fooberticus.steamtools.models.SteamPlayerBan;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.fooberticus.steamtools.gui.panels.AllUsersPanel.STEAM_COMMUNITY_URI;

@Slf4j
public class VACBanPanel extends BaseResultsPanel {

    private Map<Long, SteamPlayerBan> playerBanMap;
    private Map<Long, String> userMap;

    private static final String[] HEADER_ROW = {"User Name", "Steam64 ID", "VAC Banned", "VAC Bans", "Days Since Last", "Game Bans", "Profile URL"};

    public VACBanPanel(Map<Long, SteamPlayerBan> playerBanMap, Map<Long, String> userMap) {
        super();
        this.playerBanMap = playerBanMap;
        this.userMap = userMap;
        formatResults();
    }

    private void formatResults() {
        String[][] tableContents = new String[playerBanMap.size()][HEADER_ROW.length];

        List<Long> ids = playerBanMap.keySet().stream().toList();

        for (int i = 0; i < ids.size(); i++) {
            Long id = ids.get(i);
            String[] values = { userMap.get(id),
                    id.toString(),
                    playerBanMap.get(id).getVACBanned().toString(),
                    playerBanMap.get(id).getNumberOfVACBans().toString(),
                    playerBanMap.get(id).getDaysSinceLastBan().toString(),
                    playerBanMap.get(id).getNumberOfGameBans().toString(),
                    STEAM_COMMUNITY_URI + id };
            System.arraycopy( values, 0, tableContents[i], 0, HEADER_ROW.length );
        }

        JTable table = new JTable(tableContents, HEADER_ROW);
        table.setEnabled(true);
        table.setDefaultEditor(Object.class, null);
        table.setShowGrid(true);

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(3, SortOrder.DESCENDING));
        sortKeys.add(new RowSorter.SortKey(5, SortOrder.DESCENDING));
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);

        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                JTable jTable = (JTable) event.getSource();
                Point point = event.getPoint();
                int row = jTable.rowAtPoint(point);
                if (event.getClickCount() == 2 && jTable.getSelectedRow() != -1) {
                    String url = (String) table.getValueAt(row, 6);
                    try {
                        Desktop.getDesktop().browse(new URI( url ) );
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
        });

        scrollPane.setViewportView(table);
    }
}
