package com.fooberticus.steamtools.gui.frames;

import com.fooberticus.steamtools.gui.panels.AllUsersPanel;
import com.fooberticus.steamtools.gui.panels.CheaterResultsPanel;
import com.fooberticus.steamtools.models.SourceBanResponse;
import com.fooberticus.steamtools.utils.GuiUtil;

import javax.swing.*;
import javax.swing.GroupLayout;
import java.util.Map;

/**
 * @author Fooberticus
 */
public class ResultsWindow extends JFrame {
    SourceBanResponse response;
    Map<Long, String> userMap;

    public ResultsWindow(SourceBanResponse response, Map<Long, String> userMap) {
        this.response = response;
        this.userMap = userMap;

        initComponents();

        if (response != null && response.getResponse() != null && !response.getResponse().isEmpty()) {
            resultsTabbedPane.add("Cheaters", new CheaterResultsPanel(response, userMap));
        }

        resultsTabbedPane.add("All Players", new AllUsersPanel(userMap));
    }

    public static void startResultsWindow(SourceBanResponse response, Map<Long, String> userMap) {
        GuiUtil.initWindow( new ResultsWindow(response, userMap), "Results" );
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
