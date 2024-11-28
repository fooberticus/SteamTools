package com.fooberticus.steamtools.gui.panels;

import com.fooberticus.steamtools.models.steam.SteamPlayerBan;
import com.fooberticus.steamtools.models.steam.SteamPlayerSummary;
import com.fooberticus.steamtools.models.steamhistory.SourceBan;
import com.fooberticus.steamtools.utils.GuiUtil;
import com.fooberticus.steamtools.utils.SteamUtils;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class AllUsersPanel extends BaseResultsPanel {

    public static final String STEAM_COMMUNITY_URI = "https://steamcommunity.com/profiles/";

    private static final String[] HEADER_ROW = {"User Name", "Steam64 ID", "Steam32 ID", "Profile Visibility", "Profile Created", "Profile URL"};

    private final Map<Long, SteamPlayerSummary> playerSummaryMap;
    private final Map<Long, SteamPlayerBan> steamPlayerBanMap;
    private final Map<Long, List<SourceBan>> sourceBanMap;

    public AllUsersPanel (final Map<Long, SteamPlayerSummary> playerSummaryMap, final Map<Long, SteamPlayerBan> steamPlayerBanMap, final Map<Long, List<SourceBan>> sourceBanMap) {
        super();
        this.playerSummaryMap = playerSummaryMap;
        this.steamPlayerBanMap = steamPlayerBanMap;
        this.sourceBanMap = sourceBanMap;
        formatResults();
    }

    private void formatResults() {
        String[][] tableContents = new String[playerSummaryMap.size()][HEADER_ROW.length];

        List<Long> ids = playerSummaryMap.keySet().stream().toList();

        for (int i = 0; i < ids.size(); i++) {
            Long id = ids.get(i);
            Long timeCreated = playerSummaryMap.get(id).getTimecreated();
            LocalDate createdDate = SteamUtils.getLocalDateFromTimestamp(timeCreated);
            String[] values = { playerSummaryMap.get(id).getPersonaname(),
                    id.toString(),
                    SteamUtils.getSteamID32FromSteamID64(id),
                    playerSummaryMap.get(id).getCommunityvisibilitystate() == 3 ? "public" : "PRIVATE",
                    createdDate == null ? "--" : createdDate.toString(),
                    STEAM_COMMUNITY_URI + id };
            System.arraycopy( values, 0, tableContents[i], 0, HEADER_ROW.length );
        }

        JTable table = new JTable(tableContents, HEADER_ROW);
        table.setEnabled(true);
        table.setDefaultEditor(Object.class, null);
        table.setShowGrid(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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
                    GuiUtil.openURLInBrowser(url);
                }
            }
        });

        table.addMouseListener(new PopUpMenuClickListener(playerSummaryMap, steamPlayerBanMap, sourceBanMap));

        scrollPane.setViewportView(table);
    }
}
