package com.fooberticus.steamtools.gui.frames;

import com.fooberticus.steamtools.gui.panels.AllUsersPanel;
import com.fooberticus.steamtools.gui.panels.CommunityBanPanel;
import com.fooberticus.steamtools.gui.panels.VACBanPanel;
import com.fooberticus.steamtools.models.*;
import com.fooberticus.steamtools.utils.GuiUtil;

import javax.swing.*;
import javax.swing.GroupLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Fooberticus
 */
public class ResultsWindow extends JFrame {

    public ResultsWindow(SourceBanResponse steamHistoryResponse, SteamPlayerBansResponse steamPlayerBansResponse, SteamPlayerSummaryResponse steamPlayerSummaryResponse, Map<Long, String> userMap) {
        initComponents();

        Map<Long, SteamPlayerSummary> playerSummaryMap = new HashMap<>();
        if (steamPlayerSummaryResponse != null
                && steamPlayerSummaryResponse.getResponse() != null
                && steamPlayerSummaryResponse.getResponse().players() != null
                && !steamPlayerSummaryResponse.getResponse().players().isEmpty()) {
            List<SteamPlayerSummary> playerSummaries = steamPlayerSummaryResponse.getResponse().players();
            for (SteamPlayerSummary playerSummary : playerSummaries) {
                playerSummaryMap.put( Long.valueOf(playerSummary.getSteamid()), playerSummary );
            }
        }

        Map<Long, SteamPlayerBan> vacBannedPlayersMap  = new HashMap<>();
        if (steamPlayerBansResponse != null
                && steamPlayerBansResponse.getPlayers() != null
                && !steamPlayerBansResponse.getPlayers().isEmpty()) {
            List<SteamPlayerBan> playerBansList = steamPlayerBansResponse.getPlayers();
            for (SteamPlayerBan playerBan : playerBansList) {
                if (playerBan.getVACBanned() || playerBan.getNumberOfGameBans() > 0) {
                    vacBannedPlayersMap.put( Long.valueOf( playerBan.getSteamId() ), playerBan );
                }
            }
        }

        if (!vacBannedPlayersMap.isEmpty()) {
            resultsTabbedPane.add( "Steam Bans", new VACBanPanel( vacBannedPlayersMap, userMap ) );
        }

        if (steamHistoryResponse != null && steamHistoryResponse.getResponse() != null && !steamHistoryResponse.getResponse().isEmpty()) {
            resultsTabbedPane.add("Community Bans", new CommunityBanPanel(steamHistoryResponse, userMap));
        }

        resultsTabbedPane.add("All Players", new AllUsersPanel(userMap, playerSummaryMap));
    }

    public static void startResultsWindow(SourceBanResponse steamHistoryResponse, SteamPlayerBansResponse steamPlayerBansResponse, SteamPlayerSummaryResponse steamPlayerSummaryResponse, Map<Long, String> userMap) {
        GuiUtil.initWindow( new ResultsWindow(steamHistoryResponse, steamPlayerBansResponse, steamPlayerSummaryResponse, userMap), "Results" );
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner non-commercial license
        resultsTabbedPane = new JTabbedPane();

        //======== this ========
        setVisible(true);
        setTitle("Results"); //NON-NLS
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        var contentPane = getContentPane();

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(resultsTabbedPane, GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE)
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(resultsTabbedPane, GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
                    .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner non-commercial license
    private JTabbedPane resultsTabbedPane;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
