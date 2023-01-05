package io.github.sunkenpotato.object;

import io.github.sunkenpotato.main.GamePanel;

import static io.github.sunkenpotato.main.Common.readImage;

/**
 * Represents a key in-game
 * @see Object
 */
public class KeyObject extends Object{
    public KeyObject(int worldX, int worldY, GamePanel gamePanel) {
        name = "Key";
        this.worldX = worldX * gamePanel.tileSize;
        this.worldY = worldY * gamePanel.tileSize;
        try {
            image = readImage("/object/key.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
