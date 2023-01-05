package io.github.sunkenpotato.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static io.github.sunkenpotato.main.FrameInit.jfrinit;

public class Main {
    static void run(String cmd) {
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static {
        System.setProperty("apple.eawt.quitStrategy", "CLOSE_ALL_WINDOWS");
    }

    public static final Logger LOGGER = LogManager.getLogger(Main.class);
    public static final GamePanel gamePanel = new GamePanel();
    private void display() {
        window = new JFrame("Bardos Great Adventure (%d×%d)".formatted(768, 576));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Common.exit();
            }
        });
        window.setResizable(false);
        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        jfrinit();
        window.setVisible(true);

        LOGGER.info("Finish window.");

        gamePanel.setup();
        LOGGER.info("Setup gamePanel: OK");
        gamePanel.startGameThread();
        LOGGER.info("Started game Thread");
    }

    public static JFrame window;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Main()::display);
    }



}
