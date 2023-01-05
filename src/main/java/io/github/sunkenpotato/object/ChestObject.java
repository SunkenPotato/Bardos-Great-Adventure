package io.github.sunkenpotato.object;

import io.github.sunkenpotato.main.GamePanel;

import static io.github.sunkenpotato.main.Common.readImage;

/**
 * Represents a chest in-game
 * @see io.github.sunkenpotato.object.Object
 */
public class ChestObject extends Object{
    public ChestObject(int worldX, int worldY, GamePanel gamePanel) {
        name = "Chest";
        this.worldX = worldX * gamePanel.tileSize;
        this.worldY = worldY * gamePanel.tileSize;
        collision = true;
        try {
            image = readImage("/object/chest.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
