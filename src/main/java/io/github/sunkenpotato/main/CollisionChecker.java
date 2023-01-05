package io.github.sunkenpotato.main;

import io.github.sunkenpotato.entity.Entity;
import io.github.sunkenpotato.main.GamePanel;
import io.github.sunkenpotato.object.Object;

import java.util.Arrays;

public record CollisionChecker(GamePanel gamePanel) {

    public void checkTile(Entity entity) {

        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gamePanel.tileSize;
        int entityRightCol = entityRightWorldX / gamePanel.tileSize;
        int entityTopRow = entityTopWorldY / gamePanel.tileSize;
        int entityBottomRow = entityBottomWorldY / gamePanel.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case Up -> {
                entityTopRow = (entityTopWorldY - entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileHandler.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.tileHandler.mapTileNum[entityRightCol][entityTopRow];
                if (gamePanel.tileHandler.tiles[tileNum1].collision ||
                        gamePanel.tileHandler.tiles[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case Down -> {
                entityBottomRow = (entityBottomWorldY + entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileHandler.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gamePanel.tileHandler.mapTileNum[entityRightCol][entityBottomRow];
                if (gamePanel.tileHandler.tiles[tileNum1].collision ||
                        gamePanel.tileHandler.tiles[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case Left -> {
                entityLeftCol = (entityLeftWorldX - entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileHandler.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.tileHandler.mapTileNum[entityLeftCol][entityBottomRow];
                if (gamePanel.tileHandler.tiles[tileNum1].collision ||
                        gamePanel.tileHandler.tiles[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
            case Right -> {
                entityRightCol = (entityRightWorldX + entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileHandler.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gamePanel.tileHandler.mapTileNum[entityRightCol][entityBottomRow];
                if (gamePanel.tileHandler.tiles[tileNum1].collision ||
                        gamePanel.tileHandler.tiles[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
        }
    }

    public int checkObject(Entity e, boolean isPlayer) {
        int index = (int) Double.POSITIVE_INFINITY;
        for (int i = 0; i < gamePanel.objects.length; i++) {
            if (gamePanel.objects[i] != null) {
                // Get entity solid area pos
                e.solidArea.x = e.worldX + e.solidArea.x;
                e.solidArea.y = e.worldY + e.solidArea.y;

                // Get object solid area pos
                gamePanel.objects[i].solidArea.x = gamePanel.objects[i].worldX + gamePanel.objects[i].solidArea.x;
                gamePanel.objects[i].solidArea.y = gamePanel.objects[i].worldY + gamePanel.objects[i].solidArea.y;

                switch (e.direction) {
                    case Up -> {
                        e.solidArea.y -= e.speed;
                        if (e.solidArea.intersects(gamePanel.objects[i].solidArea)) {

                            if (gamePanel.objects[i].collision) {
                                e.collisionOn = true;
                            }
                            if (isPlayer) {
                                index = i;
                            }

                        }
                    }
                    case Down -> {
                        e.solidArea.y += e.speed;
                        if (e.solidArea.intersects(gamePanel.objects[i].solidArea)) {
                            if (e.solidArea.intersects(gamePanel.objects[i].solidArea)) {

                                if (gamePanel.objects[i].collision) {
                                    e.collisionOn = true;
                                }
                                if (isPlayer) {
                                    index = i;
                                }

                            }
                        }
                    }
                    case Left -> {
                        e.solidArea.x -= e.speed;
                        if (e.solidArea.intersects(gamePanel.objects[i].solidArea)) {

                            if (gamePanel.objects[i].collision) {
                                e.collisionOn = true;
                            }
                            if (isPlayer) {
                                index = i;
                            }

                        }
                    }
                    case Right -> {
                        e.solidArea.x += e.speed;
                        if (e.solidArea.intersects(gamePanel.objects[i].solidArea)) {

                            if (gamePanel.objects[i].collision) {
                                e.collisionOn = true;
                            }
                            if (isPlayer) {
                                index = i;
                            }

                        }
                    }
                }
                // Reset values
                e.solidArea.x = e.solidAreaDefaultX;
                e.solidArea.y = e.solidAreaDefaultY;

                //  Reset Object values
                gamePanel.objects[i].solidArea.x = gamePanel.objects[i].solidAreaDefaultX;
                gamePanel.objects[i].solidArea.y = gamePanel.objects[i].solidAreaDefaultY;
            }
        }
        return index;
    }

}
