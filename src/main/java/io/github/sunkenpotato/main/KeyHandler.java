package io.github.sunkenpotato.main;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static io.github.sunkenpotato.main.Main.LOGGER;

public class KeyHandler implements KeyListener {

    public boolean wPressed, sPressed, aPressed, dPressed, debugEnabled;
    GamePanel gp;

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        int code = e.getKeyCode();

    }


    @Override
    public synchronized void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();



        if (code == KeyEvent.VK_ESCAPE) {
            int quit = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?",
                    "Confirm Quit", JOptionPane.YES_NO_OPTION);

            if(quit != 1) {
                System.exit(0);

            }
        }
        else if (code == KeyEvent.VK_K) {
            if (gp.devMode){
                int x = Integer.parseInt(JOptionPane.showInputDialog("Enter X"));
                int y = Integer.parseInt(JOptionPane.showInputDialog("Enter Y"));
                gp.player.worldX = x * gp.tileSize;
                gp.player.worldY = y * gp.tileSize;
                LOGGER.debug("Set player coordinates to {}, {}", x, y);
            }

        }


        switch (code) {
            case KeyEvent.VK_W -> wPressed = true;
            case KeyEvent.VK_S -> sPressed = true;
            case KeyEvent.VK_A -> aPressed = true;
            case KeyEvent.VK_D -> dPressed = true;
            default -> LOGGER.trace("Invalid key pressed. key: {}", code);
        }

    }

    @Override
    public synchronized void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();


        switch (code) {
            case KeyEvent.VK_W -> wPressed = false;
            case KeyEvent.VK_S -> sPressed = false;
            case KeyEvent.VK_A -> aPressed = false;
            case KeyEvent.VK_D -> dPressed = false;
            default -> LOGGER.trace("Invalid key released. key: {}", code);
        }
    }
}

