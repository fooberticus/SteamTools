package com.fooberticus.steamtools.gui.panels;

import com.fooberticus.steamtools.models.SteamPlayerSummary;
import com.fooberticus.steamtools.utils.SteamUtils;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class AllUsersPanel extends BaseResultsPanel {

    public static final String STEAM_COMMUNITY_URI = "https://steamcommunity.com/profiles/";

    private static final String[] HEADER_ROW = {"User Name", "Steam32 ID", "Steam64 ID", "Profile Visibility", "Profile Created", "Profile URL"};

    private final Map<Long, String> userMap;
    private final Map<Long, SteamPlayerSummary> playerSummaryMap;

    public AllUsersPanel(Map<Long, String> userMap, Map<Long, SteamPlayerSummary> playerSummaryMap) {
        super();
        this.userMap = userMap;
        this.playerSummaryMap = playerSummaryMap;
        formatResults();
    }

    private void formatResults() {
        String[][] tableContents = new String[userMap.size()][HEADER_ROW.length];

        List<Long> ids = userMap.keySet().stream().toList();

        for (int i = 0; i < ids.size(); i++) {
            Long id = ids.get(i);
            Long timeCreated = playerSummaryMap.get(id).getTimecreated();
            LocalDate createdDate = SteamUtils.getLocalDateFromTimestamp(timeCreated);
            String[] values = { userMap.get(id),
                    SteamUtils.getSteamID32FromSteamID64(id),
                    id.toString(),
                    playerSummaryMap.get(id).getCommunityvisibilitystate() == 3 ? "public" : "PRIVATE",
                    createdDate == null ? "unknown" : createdDate.toString(),
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
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);

        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                JTable jTable = (JTable) event.getSource();
                Point point = event.getPoint();
                int row = jTable.rowAtPoint(point);
                if (event.getClickCount() == 2 && jTable.getSelectedRow() != -1) {
                    String url = (String) table.getValueAt(row, 5);
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
