package io.github.sunkenpotato.object;

import io.github.sunkenpotato.main.GamePanel;

import static io.github.sunkenpotato.main.Common.readImage;

/**
 * Represents a door in-game
 * @see io.github.sunkenpotato.object.Object
 */
public class DoorObject extends Object{
    public DoorObject(int worldX, int worldY, GamePanel gamePanel) {
        name = "Door";
        this.worldX = worldX * gamePanel.tileSize;
        this.worldY = worldY * gamePanel.tileSize;
        try {
            image = readImage("/object/door.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        collision = true;
    }
}
