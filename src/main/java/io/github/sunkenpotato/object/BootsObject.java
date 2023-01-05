package io.github.sunkenpotato.object;

import io.github.sunkenpotato.main.GamePanel;

import static io.github.sunkenpotato.main.Common.readImage;

/**
 * Represents Boots in-game
 * @see Object
 */
public class BootsObject extends Object{
    public BootsObject(int worldX, int worldY, GamePanel gamePanel) {
        name = "Boots";
        this.worldX = worldX * gamePanel.tileSize;
        this.worldY = worldY * gamePanel.tileSize;
        collision = true;
        try {
            image = readImage("/object/boots.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
