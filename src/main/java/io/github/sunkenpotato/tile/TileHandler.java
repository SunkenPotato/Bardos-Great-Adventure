package io.github.sunkenpotato.tile;

import io.github.sunkenpotato.main.GamePanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static io.github.sunkenpotato.main.Common.readImage;

public class TileHandler {
    final GamePanel gamePanel;
    public final Tile[] tiles;
    public final int[][] mapTileNum;
    static final Logger LOGGER = LogManager.getLogger(TileHandler.class);

    public TileHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        tiles = new Tile[10];
        mapTileNum = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];
        loadMap(2);
        loadTileImages();
    }

    /**
     * Load map data.
     * @param mapNo Map number -> map + mapNo -> i.e. map0
     */
    public void loadMap(int mapNo) {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/map/map"+mapNo);
            assert inputStream != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            int col = 0, row = 0;

            while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
                String line = bufferedReader.readLine();

                while (col < gamePanel.maxWorldCol) {
                    String[] nums = line.split(" ");

                    int num = Integer.parseInt(nums[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }

                if (col == gamePanel.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }

            bufferedReader.close();
        } catch (Exception e) {
            LOGGER.fatal("Exception related to game files(maps): {}", e.getMessage());
        }
    }

    /**
     * Load tile assets
     */
    public void loadTileImages() {
        tiles[0] = new Tile(readImage("/tile/grass4.png"), "grass");

        tiles[1] = new Tile(readImage("/tile/wall.png"), "wall", true);

        tiles[2] = new Tile(readImage("/tile/water.png"), "water", true);

        tiles[3] = new Tile(readImage("/tile/earth.png"), "earth");

        tiles[4] = new Tile(readImage("/tile/tree.png"), "tree", true);

        tiles[5] = new Tile(readImage("/tile/sand.png"), "sand");

        tiles[6] = new Tile(readImage("/tile/gravel.png"), "gravel");

    }


    /**
     * Render tiles
     * @param graphics2D
     */
    public void render(Graphics2D graphics2D) {

        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            if(worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX
                    && worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX
                    && worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY
                    && worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY){

                graphics2D.drawImage(tiles[tileNum].image, screenX, screenY,
                        gamePanel.tileSize, gamePanel.tileSize, null);
            }

            worldCol++;
            if (worldCol == gamePanel.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }



    }
}
