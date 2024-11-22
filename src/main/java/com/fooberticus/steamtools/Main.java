package com.fooberticus.steamtools;

import com.fooberticus.steamtools.gui.MainWindow;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::startMainWindow);
    }

}
