package io.github.sunkenpotato.main;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

import static io.github.sunkenpotato.main.Common.readImage;
import static io.github.sunkenpotato.main.Main.window;

/**
 * Frame Modifier class
 */
public class FrameInit {
    static final Logger LOGGER = LogManager.getLogger(FrameInit.class);
    public static void jfrinit() {
        if (System.getProperty("os.name").equals("Mac OS X")) {
            final Image image = readImage("/player/bardo/down/bardo_down_1_g.png");
            System.setProperty("apple.laf.useScreenMenuBar", "true");

            final Taskbar taskbar = Taskbar.getTaskbar();

            try {
                //set icon for macOS (and other systems which do support this method)
                taskbar.setIconImage(image);

            } catch (final UnsupportedOperationException e) {
                LOGGER.warn("The os does not support: 'taskbar.setIconImage'");
            } catch (final SecurityException e) {
                LOGGER.warn("There was a security exception for: 'taskbar.setIconImage'");
            }
        }
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Help");
        JMenuItem reportABug = new JMenuItem("Report a bug");
        JMenuItem wiki = new JMenuItem("Open wiki");
        menu.add(reportABug);
        //menu.add(wiki);
        menuBar.add(menu);
        window.setJMenuBar(menuBar);
    }




}
