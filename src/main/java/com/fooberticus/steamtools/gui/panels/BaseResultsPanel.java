package com.fooberticus.steamtools.gui.panels;

import javax.swing.*;

public class BaseResultsPanel extends JPanel {

    JScrollPane scrollPane;

    public BaseResultsPanel() {
        initComponents();
    }

    private void initComponents() {
        scrollPane = new JScrollPane();

        setVisible(true);

        GroupLayout contentPaneLayout = new GroupLayout(this);
        setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 861, Short.MAX_VALUE))));

        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addContainerGap())
        );
    }

}
