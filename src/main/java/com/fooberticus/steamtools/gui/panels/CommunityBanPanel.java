package com.fooberticus.steamtools.gui.panels;

import com.fooberticus.steamtools.models.SourceBan;
import com.fooberticus.steamtools.models.SourceBanResponse;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fooberticus.steamtools.utils.BanStates.*;

@Slf4j
public class CommunityBanPanel extends BaseResultsPanel {

    private static final String STEAM_HISTORY_URI = "https://steamhistory.net/id/";

    private static final String[] HEADER_ROW = {"User Name", "Steam64 ID", "Active Bans", "Total Bans", "Steam History"};

    private final SourceBanResponse response;
    private final Map<Long, String> userMap;

    public CommunityBanPanel(final SourceBanResponse response, final Map<Long, String> userMap) {
        super();
        this.response = response;
        this.userMap = userMap;
        formatResults();
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

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(2, SortOrder.DESCENDING));
        sortKeys.add(new RowSorter.SortKey(3, SortOrder.DESCENDING));
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);

        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                JTable jTable = (JTable) event.getSource();
                Point point = event.getPoint();
                int row = jTable.rowAtPoint(point);
                if (event.getClickCount() == 2 && jTable.getSelectedRow() != -1) {
                    String url = (String) table.getValueAt(row, 4);
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
