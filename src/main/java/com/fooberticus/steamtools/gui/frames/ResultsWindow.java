package com.fooberticus.steamtools.gui.frames;

import com.fooberticus.steamtools.gui.panels.AllUsersPanel;
import com.fooberticus.steamtools.gui.panels.CommunityBanPanel;
import com.fooberticus.steamtools.gui.panels.VACBanPanel;
import com.fooberticus.steamtools.models.steam.SteamPlayerBan;
import com.fooberticus.steamtools.models.steam.SteamPlayerSummary;
import com.fooberticus.steamtools.models.steamhistory.SourceBan;
import com.fooberticus.steamtools.utils.GuiUtil;

import javax.swing.*;
import javax.swing.GroupLayout;
import java.util.List;
import java.util.Map;

/**
 * @author Fooberticus
 */
public class ResultsWindow extends JFrame {

    public ResultsWindow(Map<Long, List<SourceBan>> sourceBanMap, Map<Long, SteamPlayerBan> steamPlayerBanMap, Map<Long, SteamPlayerSummary> steamPlayerSummaryMap) {
        initComponents();

        if (!steamPlayerBanMap.isEmpty()) {
            resultsTabbedPane.add( "Steam Bans", new VACBanPanel( steamPlayerSummaryMap, steamPlayerBanMap ) );
        }

        if (!sourceBanMap.isEmpty()) {
            resultsTabbedPane.add("Community Bans", new CommunityBanPanel(steamPlayerSummaryMap, sourceBanMap));
        }

        resultsTabbedPane.add("All Players", new AllUsersPanel(steamPlayerSummaryMap));
    }

    public static void startResultsWindow(Map<Long, List<SourceBan>> sourceBanMap, Map<Long, SteamPlayerBan> steamPlayerBanMap, Map<Long, SteamPlayerSummary> steamPlayerSummaryMap) {
        GuiUtil.initWindow( new ResultsWindow(sourceBanMap, steamPlayerBanMap, steamPlayerSummaryMap), "Results" );
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
