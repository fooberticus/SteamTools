package com.fooberticus.steamtools.gui.panels;

import com.fooberticus.steamtools.models.SourceBan;
import com.fooberticus.steamtools.models.SourceBanResponse;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fooberticus.steamtools.utils.BanStates.*;

@Slf4j
public class CommunityBanPanel extends BaseResultsPanel {

    private static final String STEAM_HISTORY_URI = "https://steamhistory.net/id/";

    private static final String[] HEADER_ROW = {"User Name", "Steam64 ID", "Active Bans", "Total Bans", "Cheating Found", "Last Ban Date", "Last Ban Reason", "Steam History"};

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

        Map<Long, CommunityUser> communityUserMap = new HashMap<>();

        Map<Long, SourceBan> latestBanMap = new HashMap<>();

        banList.forEach(ban -> {
            Long id = Long.parseLong( ban.getSteamID() );

            communityUserMap.computeIfAbsent(id,
                    k -> new CommunityUser(
                            userMap.get(id),
                            id,
                            0,
                            0,
                            false,
                            null,
                            null,
                            STEAM_HISTORY_URI + id
                    )
            );

            switch (ban.getCurrentState()) {
                case PERMANENT:
                case TEMP_BAN: communityUserMap.get(id).activeBans++; break;
            }

            communityUserMap.get(id).totalBans++;

            if ( SteamUtils.isBanReasonCheating(ban.getBanReason() ) ) {
                communityUserMap.get(id).cheatingFound = true;
            }

            LocalDate banDate = SteamUtils.getLocalDateFromTimestamp(ban.getBanTimestamp());
            if (!latestBanMap.containsKey(id) || SteamUtils.getLocalDateFromTimestamp( latestBanMap.get(id).getBanTimestamp() ).isBefore(banDate) ) {
                latestBanMap.put(id, ban);
            }
        });

        String[][] tableContents = new String[communityUserMap.size()][HEADER_ROW.length];

        List<Long> ids = communityUserMap.keySet().stream().toList();

        for (int i = 0; i < ids.size(); i++) {
            Long id = ids.get(i);
            String[] values = { userMap.get(id),
                    id.toString(),
                    String.valueOf( communityUserMap.get(id).activeBans ),
                    String.valueOf( communityUserMap.get(id).totalBans ),
                    communityUserMap.get(id).cheatingFound ? "Yes" : "No",
                    SteamUtils.getLocalDateFromTimestamp( latestBanMap.get(id).getBanTimestamp() ).toString(),
                    latestBanMap.get(id).getBanReason(),
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
                    String url = (String) table.getValueAt(row, 7);
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

    private static class CommunityUser {
        String userName;
        Long steam64Id;
        Integer activeBans;
        Integer totalBans;
        Boolean cheatingFound;
        LocalDate lastBanDate;
        String lastBanReason;
        String steamHistoryUrl;

        CommunityUser(String userName, Long steam64Id, Integer activeBans, Integer totalBans, Boolean cheatingFound, LocalDate lastBanDate, String lastBanReason, String steamHistoryUrl) {
            this.userName = userName;
            this.steam64Id = steam64Id;
            this.activeBans = activeBans;
            this.totalBans = totalBans;
            this.cheatingFound = cheatingFound;
            this.lastBanDate = lastBanDate;
            this.lastBanReason = lastBanReason;
            this.steamHistoryUrl = steamHistoryUrl;
        }
    }
}
