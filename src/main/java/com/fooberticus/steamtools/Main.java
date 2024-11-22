package com.fooberticus.steamtools;

import com.fooberticus.steamtools.gui.frames.MainWindow;
import com.fooberticus.steamtools.utils.GuiUtil;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        GuiUtil.initGui();
        SwingUtilities.invokeLater(MainWindow::startMainWindow);
    }

}
