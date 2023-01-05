package io.github.sunkenpotato.main;

import io.github.sunkenpotato.object.BootsObject;
import io.github.sunkenpotato.object.ChestObject;
import io.github.sunkenpotato.object.DoorObject;
import io.github.sunkenpotato.object.KeyObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public record AssetHandler(GamePanel gamePanel) {

    public void setObject() {

        // KEYS
        gamePanel.objects[0] = new KeyObject(23, 7, gamePanel);
        gamePanel.objects[1] = new KeyObject(23, 40, gamePanel);
        gamePanel.objects[2] = new KeyObject(37, 7, gamePanel);

        // doors
        gamePanel.objects[3] = new DoorObject(15, 21, gamePanel);
        gamePanel.objects[4] = new DoorObject(10, 11, gamePanel);
        gamePanel.objects[5] = new DoorObject(8, 21, gamePanel);

        // ches
        gamePanel.objects[6] = new ChestObject(10, 7, gamePanel);

        // botts
        gamePanel.objects[7] = new BootsObject(37, 42, gamePanel);
        LogManager.getLogger(this).info("Initialize objects.");
    }
}