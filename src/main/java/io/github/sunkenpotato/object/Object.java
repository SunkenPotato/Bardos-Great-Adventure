package io.github.sunkenpotato.object;

import io.github.sunkenpotato.main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Represents any type of object in-game.
 * <b>All</b> Objects <b>must</b> extend {@code Object}
 */
public class Object {
    BufferedImage image;

    public String name;

    public boolean collision = false;
    public int worldX, worldY;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

    public void render(Graphics2D graphics2D, GamePanel gamePanel) {
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if(worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX
                && worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX
                && worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY
                && worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY){

            graphics2D.drawImage(image, screenX, screenY,
                    gamePanel.tileSize, gamePanel.tileSize, null);

        }
    }
}
