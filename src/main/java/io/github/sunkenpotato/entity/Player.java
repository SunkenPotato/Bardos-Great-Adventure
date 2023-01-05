package io.github.sunkenpotato.entity;

import io.github.sunkenpotato.main.GamePanel;
import io.github.sunkenpotato.main.KeyHandler;
import io.github.sunkenpotato.main.SoundHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static io.github.sunkenpotato.main.Common.readImage;

public class Player extends Entity{
    final int PLAYER_HITBOX_X = 0;
    final int PLAYER_HITBOX_Y = (int) (6 * GamePanel.scale);
    final int PLAYER_HITBOX_WIDTH = (int) (15 * GamePanel.scale);
    final int PLAYER_HITBOX_HEIGHT = (int) (30 * GamePanel.scale);

    final GamePanel gamePanel;
    final KeyHandler keyHandler;
    final SoundHandler soundH;

    public final int screenX;
    public final int screenY;
    int hasKeys = 0;

    static final Logger LOGGER = LogManager.getLogger(Player.class);

    public Player(GamePanel gamePanel, KeyHandler keyHandler, SoundHandler soundH) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        this.soundH = soundH;

        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize/2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize/2);

        init();
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 16;
        solidArea.height = 16;
        getPlayerImage();
        direction = Direction.Down;

        /*
                solidArea = new Rectangle(0, 16, gamePanel.tileSize - ((gamePanel.tileSize / 3) * 2),
                gamePanel.tileSize - ((gamePanel.tileSize / 3) * 2));
         */
        ////////////////////////
        // TODO               //
        // Fix player hit box //
        ////////////////////////
        //solidArea = new Rectangle(0, 0, (int) (26 * GamePanel.scale), (int) (36 * GamePanel.scale));

        solidArea = new Rectangle(11, 10, 22, 30);
        LOGGER.info("Initialized Player");
    }

    /**
     * Initialize player.
     * This is <b>not</b> a constructor
     */
    public void init() {
        worldX = gamePanel.tileSize * 23;
        worldY = gamePanel.tileSize * 21;
        speed = 4;
        direction = Direction.Down;
    }

    /**
     * Update the player
     */
    public void update() {

        if(keyHandler.wPressed) {
            direction = Direction.Up;

        }
        else if (keyHandler.sPressed) {
            direction = Direction.Down;

        }
        else if (keyHandler.aPressed) {
            direction = Direction.Left;

        }
        else if (keyHandler.dPressed) {
            direction = Direction.Right;
        }

        // CHECK TILE COLLISION
        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);

        // CHECK OBJECT COLLISION
        int objIndex = gamePanel.collisionChecker.checkObject(this, true);
        handleObject(objIndex);


        // IF COLLISION IS FALSE, ALLOW PLAYER TO MOVE
        if (!collisionOn && (keyHandler.aPressed || keyHandler.dPressed || keyHandler.sPressed || keyHandler.wPressed)) {
            switch (direction) {
                case Up -> worldY -= speed;
                case Down -> worldY += speed;
                case Left -> worldX -= speed;
                case Right -> worldX += speed;
            }
        }

        if (keyHandler.wPressed  || keyHandler.sPressed || keyHandler.dPressed || keyHandler.aPressed) {
            spriteCounter++;
            // Update sprite 5x a second (60 / 15 = 4)
            if (spriteCounter > 9) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                }
                else if (spriteNum == 2) {
                    spriteNum = 3;
                }
                else if (spriteNum == 3) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        else {
            spriteNum = 1;
        }


    }

    /**
     * Handle objects
     * @param index
     * @deprecated
     */
    @Deprecated(forRemoval = true)
    @SuppressWarnings("all")
    public void handleObject(int index) {
        if(index != (int) Double.POSITIVE_INFINITY) {
            String objectName = gamePanel.objects[index].name;
            switch (objectName) {
                case "Key" -> {
                    hasKeys ++;
                    System.out.println("Picked up Key");
                    gamePanel.objects[index] = null;
                }
                case "Door" -> {
                    if (hasKeys > 0) {
                        gamePanel.objects[index] = null;
                        hasKeys --;
                    }
                }
                case "Chest" -> {
                    if (hasKeys > 1) {
                        gamePanel.objects[index] = null;
                        hasKeys -= 2;
                    }
                }
                case "Boots" -> {
                    gamePanel.playSf(2);
                    speed += 2;
                    gamePanel.objects[index] = null;
                }
            }
        }
    }

    /**
     * Get player sprites.
     */
    public void getPlayerImage() {

        // BufferedImage sheet = ImageIO.read(getClass().getResourceAsStream("/player/bardo.png"));
        // Sorry for this:
        // down
        down = new ArrayList<>();

        for (var i: new int[]{1, 2, 3}) {
            BufferedImage image = readImage(String.format("/player/bardo/down/bardo_down_%d.png", i));
            down.add(image);
        }

        left = new ArrayList<>();

        for (var i: new int[]{1, 2, 3}) {
            BufferedImage image = readImage(String.format("/player/bardo/left/bardo_left_%d.png", i));
            left.add(image);
        }

        right = new ArrayList<>();

        for (var i: new int[]{1, 2, 3}) {
            BufferedImage image = readImage(String.format("/player/bardo/right/bardo_right_%d.png", i));
            right.add(image);
        }

        up = new ArrayList<>();

        for (var i: new int[]{1, 2, 3}) {
            BufferedImage image = readImage(String.format("/player/bardo/up/bardo_up_%d.png", i));
            up.add(image);
        }
        LOGGER.info("Loaded player sprites.");

    }

    @SuppressWarnings("all")

    /**
     * Render the player
     */
    public void render(Graphics2D graphics2D) {
        BufferedImage image = null;

        switch(direction) {
            case Up -> {
                if(spriteNum == 1) {
                    image = up.get(0);
                }
                else if(spriteNum == 2) {
                    image = up.get(1);
                }
                else if(spriteNum == 3) {
                    image = up.get(2);
                }
            }
            case Down -> {
                if(spriteNum == 1) {
                    image = down.get(0);
                }
                else if(spriteNum == 2) {
                    image = down.get(1);
                }
                else if(spriteNum == 3) {
                    image = down.get(2);
                }
            }
            case Left -> {
                if(spriteNum == 1) {
                    image = left.get(0);
                }
                else if(spriteNum == 2) {
                    image = left.get(1);
                }
                else if(spriteNum == 3) {
                    image = left.get(2);
                }
            }
            case Right -> {
                if(spriteNum == 1) {
                    image = right.get(0);
                }
                else if(spriteNum == 2) {
                    image = right.get(1);
                }
                else if(spriteNum == 3) {
                    image = right.get(2);
                }
            }
        }

        graphics2D.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
